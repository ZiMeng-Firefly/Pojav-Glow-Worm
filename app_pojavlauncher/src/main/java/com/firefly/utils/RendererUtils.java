package com.firefly.utils;

import static net.kdt.pojavlaunch.Tools.LOCAL_RENDERER;

import com.movtery.plugins.renderer.RendererPlugin;

import java.util.*;

public class RendererUtils {
    private static final Map<String, String> pluginRendererValue = new HashMap<>();
    private static final Map<String, String> launcherRendererIds = new HashMap<>();

    public static void setPluginRendererArray() {
        if (RendererPlugin.isAvailable()) {
            RendererPlugin.getRendererList().forEach(renderer -> {
                if (renderer.getIdName() != null && renderer.getDes() != null) {
                    pluginRendererValue.put(renderer.getIdName(), renderer.getDes());
                }
            });
        }
    }

    public static void generateAndStoreUUIDs() {
        if (RendererPlugin.isAvailable()) {
            RendererPlugin.getRendererList().forEach(renderer -> {
                if (renderer.getIdName() != null && renderer.getId() != null) {
                    launcherRendererIds.put(getIdName, renderer.getId());
                }
            });
        }
    }

    public static List<String> rendererIds() {
        List<String> ids = new ArrayList<>();
        launcherRendererIds.forEach((key, value) -> ids.add(key));
        return ids;
    }

    public static boolean isGalliumRenderer(String envValue) {
        return envValue.contains("virgl") || envValue.contains("zink") || envValue.contains("freedreno") || envValue.contains("panfrost") || envValue.contains("softpipe") envValue.contains("llvmpipe");
    }

}