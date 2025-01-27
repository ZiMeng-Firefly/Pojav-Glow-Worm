//
// Created by Vera-Firefly on 27.01.2025.
//
#ifndef INPUT_BRIDGE_V3
#define INPUT_BRIDGE_V3

#include <jni.h>

void* maybe_load_vulkan();

JNIEXPORT jstring JNICALL
Java_org_lwjgl_glfw_CallbackBridge_nativeClipboard(JNIEnv* env, __attribute__((unused)) jclass clazz, jint action, jbyteArray copySrc);

#endif // INPUT_BRIDGE_V3