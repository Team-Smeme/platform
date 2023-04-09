package com.snim.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String Object2Json(Object target) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(target);
    }

    public static <T> T Json2Object(@NotNull String source, @NotNull Class<T> java) throws JsonProcessingException {
        return (T) OBJECT_MAPPER.readValue(source, java);
    }
}