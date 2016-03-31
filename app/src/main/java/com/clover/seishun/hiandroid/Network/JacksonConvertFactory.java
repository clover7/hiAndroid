package com.clover.seishun.hiandroid.Network;

/**
 * Created by heaun.b on 2016. 3. 31..
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * A {@linkplain Converter.Factory converter} which uses Jackson.
 * <p>
 * Because Jackson is so flexible in the types it supports, this converter assumes that it can
 * handle all types. If you are mixing JSON serialization with something else (such as protocol
 * buffers), you must {@linkplain Retrofit.Builder#addConverterFactory(Converter.Factory) add this
 * instance} last to allow the other converters a chance to see their types.
 */
public final class JacksonConvertFactory extends Converter.Factory {
    /** Create an instance using a default {@link ObjectMapper} instance for conversion. */
    public static JacksonConvertFactory create() {
        return create(new ObjectMapper());
    }

    /** Create an instance using {@code mapper} for conversion. */
    public static JacksonConvertFactory create(ObjectMapper mapper) {
        return new JacksonConvertFactory(mapper);
    }

    private final ObjectMapper mapper;

    private JacksonConvertFactory(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        this.mapper = mapper;
    }

}