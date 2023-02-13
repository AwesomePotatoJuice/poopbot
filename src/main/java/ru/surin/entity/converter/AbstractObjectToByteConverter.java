package ru.surin.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.lang.Nullable;

public abstract class AbstractObjectToByteConverter<T> implements Converter<T, byte[]> {
    private final Jackson2JsonRedisSerializer<T> serializer;

    AbstractObjectToByteConverter(Class<T> clazz) {
        serializer = new Jackson2JsonRedisSerializer<>(clazz);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public byte[] convert(@Nullable T value) {
        return serializer.serialize(value);
    }
}
