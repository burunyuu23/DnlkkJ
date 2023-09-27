package com.dnlkk.boot;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.dnlkk.util.PathUtils;

public class AppConfig {
    private static Map<String, Object> config;
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    public static String getProperty(String key) {
        if (config == null) {
            loadConfig();
        }
        return findProperty(config, key);
    }

    private static String findProperty(Map<String, Object> config, String key) {
        if (config == null)
            return null;
        if (config.containsKey(key))
            return config.get(key).toString();
        String[] keys = PathUtils.splitPath("\\.", key);
        String nextKey = PathUtils.removeFirstPath(".", keys);
        if (nextKey == null)
            return null;
        return findProperty((Map<String,Object>) config.get(keys[0]), nextKey);
    }

    public static boolean loadConfig() {
        Yaml yaml = new Yaml();
        if (config != null)
            return true;
        try (InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream("application.yml")) {
            config = yaml.load(inputStream);
            return config != null;
        } catch (Exception e) {
            logger.error("application.yml config in resources not found");
        }
        return false;
    }
}