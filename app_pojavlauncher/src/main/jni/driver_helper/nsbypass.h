
//
// Created by Vera-Firefly on 17.01.2025.
//

#ifndef LINKER_NSBYPASS_H
#define LINKER_NSBYPASS_H

#include <stdbool.h>

bool linker_ns_load(const char* lib_search_path);
void* linker_ns_dlopen(const char* name, int flag);
void* linker_ns_dlopen_unique(const char* tmpdir, const char* name, int flag);

#ifdef __cplusplus
extern "C" {
#endif

void* loadTurnipVulkan();

#ifdef __cplusplus
}
#endif

#endif //LINKER_NSBYPASS_H