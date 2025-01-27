//
// Created by Vera-Firefly on 27.01.2025.
//

#ifndef JVM_COMMON_H
#define JVM_COMMON_H

#include <jni.h>

extern void* maybe_load_vulkan();

JNIEXPORT jstring JNICALL
Java_org_lwjgl_glfw_CallbackBridge_nativeClipboard(JNIEnv* env, __attribute__((unused)) jclass clazz, jint action, jbyteArray copySrc);

#endif // JVM_COMMON_H