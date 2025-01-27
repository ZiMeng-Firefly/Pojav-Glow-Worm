//
// Created by Vera-Firefly on 27.01.2025.
//
#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "jvm_hooks/jvm_hooks.h"

void installEMUIIteratorMititgation(JNIEnv *env);
void installLwjglDlopenHook(JNIEnv *env);
void hookExec(JNIEnv *env);
