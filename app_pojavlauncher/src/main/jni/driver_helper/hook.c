//
// Created by Vera-Firefly on 17.01.2025.
//

#include <android/dlext.h>
#include <string.h>
#include <stdio.h>

static void* (*android_dlopen_ext_impl)(const char* filename, int flags, const android_dlextinfo* extinfo, const void* caller_addr) = NULL;
static struct android_namespace_t* (*android_get_exported_namespace_impl)(const char* name) = NULL;
static void* ready_handle = NULL;

static const char* const sphal_namespaces[] = {
    "sphal", "vendor", "default"
};

#define SPHAL_NAMESPACE_COUNT (sizeof(sphal_namespaces) / sizeof(sphal_namespaces[0]))

__attribute__((visibility("default"), used))
void linker_hook_set_handles(void* handle, void* android_dlopen_ext, void* android_get_exported_namespace) {
    if (handle && android_dlopen_ext && android_get_exported_namespace) {
        ready_handle = handle;
        android_dlopen_ext_impl = (typeof(android_dlopen_ext_impl))android_dlopen_ext;
        android_get_exported_namespace_impl = (typeof(android_get_exported_namespace_impl))android_get_exported_namespace;
    }
}

__attribute__((visibility("default"), used))
void* android_dlopen_ext(const char* filename, int flags, const android_dlextinfo* extinfo) {
    if (strstr(filename, "vulkan."))
        return ready_handle;

    return android_dlopen_ext_impl(filename, flags, extinfo, &android_dlopen_ext);    
}

__attribute__((visibility("default"), used))
void* android_load_sphal_library(const char* filename, int flags) {
    if (strstr(filename, "vulkan."))
        return ready_handle;

    struct android_namespace_t* androidNamespace = NULL;
    for (size_t i = 0; i < SPHAL_NAMESPACE_COUNT && !androidNamespace; i++) {
        androidNamespace = android_get_exported_namespace_impl(sphal_namespaces[i]);
    }

    if (!androidNamespace) {
        fprintf(stderr, "Error: No valid SPHAL namespace found\n");
        return NULL;
    }

    android_dlextinfo info = {
        .flags = ANDROID_DLEXT_USE_NAMESPACE,
        .library_namespace = androidNamespace
    };

    return android_dlopen_ext_impl(filename, flags, &info, &android_dlopen_ext);
}

__attribute__((visibility("default"), used))
uint64_t atrace_get_enabled_tags() {
    return 0;
}