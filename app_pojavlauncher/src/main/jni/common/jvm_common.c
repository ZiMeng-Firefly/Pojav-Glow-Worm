//
// Created by Vera-Firefly on 27.01.2025.
//
#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void installEMUIIteratorMititgation(JNIEnv *env);
void installLwjglDlopenHook(JNIEnv *env);
void hookExec(JNIEnv *env);

void* maybe_load_vulkan();

JNIEXPORT jstring JNICALL
Java_org_lwjgl_glfw_CallbackBridge_nativeClipboard(JNIEnv* env, __attribute__((unused)) jclass clazz, jint action, jbyteArray copySrc);
