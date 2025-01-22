//
// Created by Vera-Firefly on 17.01.2025.
//

#include <android/dlext.h>
#include <string.h>
#include <stdio.h>
#include <atomic>

static void* (*android_dlopen_ext_impl)(const char* filename, int flags, const android_dlextinfo* extinfo, const void* caller_addr);
static struct android_namespace_t* (*android_get_exported_namespace_impl)(const char* name);

static void* ready_handle;
static std::atomic<void*> global_ready_handle{nullptr};

static const char *supported_namespaces[] = {"sphal", "vendor", "default"};

union PointerCaster {
    void* dataPtr;
    void* (*funcPtr_4args)(const char*, int, const android_dlextinfo*, const void*);
    void* (*funcPtr_3args)(const char*, int, const android_dlextinfo*);
    struct android_namespace_t* (*namespaceFuncPtr)(const char*);
};

__attribute__((visibility("default"), used))
void linker_hook_set_handles(void* handle, void* android_dlopen_ext, void* android_get_exported_namespace)
{
    ready_handle = handle;
    global_ready_handle.store(handle);

    PointerCaster caster;
    caster.dataPtr = android_dlopen_ext;
    android_dlopen_ext_impl = caster.funcPtr_4args;

    caster.dataPtr = android_get_exported_namespace;
    android_get_exported_namespace_impl = caster.namespaceFuncPtr;
}

static void* checkIfGlobalReadyHandle() {
    void* handle = global_ready_handle.load();
    if (handle == nullptr)
    {
        fprintf(stderr, "Global ready handle is null, falling back to ready_handle.\n");
        return ready_handle;
    }
    return handle;
}

__attribute__((visibility("default"), used))
void *android_dlopen_ext(const char *filename, int flags, const android_dlextinfo *extinfo) {
    if (strstr(filename, "vulkan."))
        return checkIfGlobalReadyHandle();

    PointerCaster caster;
    caster.funcPtr_3args = android_dlopen_ext;
    return android_dlopen_ext_impl(filename, flags, extinfo, caster.dataPtr);
}

__attribute__((visibility("default"), used))
void *android_load_sphal_library(const char *filename, int flags) {
    if (strstr(filename, "vulkan."))
        return checkIfGlobalReadyHandle();

    struct android_namespace_t* androidNamespace = nullptr;
    for (const char* namespace_name : supported_namespaces)
    {
        androidNamespace = android_get_exported_namespace_impl(namespace_name);
        if (androidNamespace != NULL) break;
    }

    android_dlextinfo extinfo = {
        .flags = ANDROID_DLEXT_USE_NAMESPACE,
        .library_namespace = androidNamespace
    };

    PointerCaster caster;
    caster.funcPtr_3args = android_dlopen_ext;
    return android_dlopen_ext_impl(filename, flags, &extinfo, caster.dataPtr);
}

__attribute__((visibility("default"), used))
uint64_t atrace_get_enabled_tags() {
    return 0;
}