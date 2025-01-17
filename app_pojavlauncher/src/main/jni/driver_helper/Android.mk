include $(CLEAR_VARS)

LOCAL_CPPFLAGS := -std=c++17
LOCAL_LDLIBS := -llog -ldl -lc++
LOCAL_MODULE := driver_helper
LOCAL_SRC_FILES := \
    driver_helper.c \
    nsbypass.c
LOCAL_CFLAGS := -fPIC -g -rdynamic

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE := linkerhook
LOCAL_SRC_FILES := hook.c
LOCAL_LDFLAGS := -z global

include $(BUILD_SHARED_LIBRARY)