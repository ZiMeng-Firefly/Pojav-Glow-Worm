//
// Created by Vera-Firefly on 17.01.2025.
//

#include <android/dlext.h>
#include <string.h>
#include <stdio.h>
#include <atomic>

static void* (*android_dlopen_ext_impl)(const char* filename, int flags, const android_dlextinfo* extinfo, const void* caller_addr);
static struct android_namespace_t* (*android_get_exported_namespace_impl)(const char* name);

static std::atomic<void*> global_ready_handle{nullptr};

static const char* supported_namespaces[] = {"sphal", "vendor", "default"};

__attribute__((visibility("default"), used))
void linker_hook_set_handles(void* handle, void* dlopen_ext, void* get_namespace) {
    global_ready_handle.store(handle);
    android_dlopen_ext_impl = (decltype(android_dlopen_ext_impl))dlopen_ext;
    android_get_exported_namespace_impl = (decltype(android_get_exported_namespace_impl))get_namespace;
}

__attribute__((visibility("default"), used))
void* android_dlopen_ext(const char* filename, int flags, const android_dlextinfo* extinfo) {
    if (!filename || !android_dlopen_ext_impl) {
        return nullptr;
    }

    if (strstr(filename, "vulkan.") == nullptr) {
        return android_dlopen_ext_impl(filename, flags, extinfo, &android_dlopen_ext);
    }

    return global_ready_handle.load();
}

__attribute__((visibility("default"), used))
void* load_sphal_library(const char* filename, int flags) {
    if (!filename || !android_dlopen_ext_impl || !android_get_exported_namespace_impl) {
        return nullptr;
    }

    if (strstr(filename, "vulkan.") != nullptr) {
        return global_ready_handle.load();
    }

    struct android_namespace_t* androidNamespace = nullptr;
    for (const char* namespace_name : supported_namespaces) {
        androidNamespace = android_get_exported_namespace_impl(namespace_name);
        if (androidNamespace != nullptr) {
            break;
        }
    }

    if (!androidNamespace) {
        return nullptr;
    }

    android_dlextinfo extinfo = {};
    extinfo.flags = ANDROID_DLEXT_USE_NAMESPACE;
    extinfo.library_namespace = androidNamespace;

    return android_dlopen_ext_impl(filename, flags, &extinfo, &android_dlopen_ext);
}

__attribute__((visibility("default"), used))
uint64_t atrace_get_enabled_tags() {
    return 0;
}