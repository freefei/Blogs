package com.renfei.blog.common.redis.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.renfei.blog.common.json.JsonMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Author: jlchen
* Date: 2012-11-14
*/
public class StringHashMapper<T> {
    private final JsonMapper mapper;
    private final JavaType userType;
    private final JavaType mapType;

    public StringHashMapper(Class<T> type) {
        this.mapper = JsonMapper.nonDefaultMapper();
        this.mapType = mapper.createCollectionType(HashMap.class, String.class, String.class);
        this.userType = mapper.getMapper().getTypeFactory().constructType(type);
    }

    public T fromHash(Map<String, String> hash) {
        if (hash.isEmpty()) return null;
        return mapper.getMapper().convertValue(hash, userType);
    }


    public Map<String, String> toHash(T object) {
        Map<String, String> hash = mapper.getMapper().convertValue(object, mapType);

        //remove null values
        List<String> nullKeys = Lists.newArrayListWithCapacity(hash.size());
        for (String key : hash.keySet()) {
            if (hash.get(key) == null) {
                nullKeys.add(key);
            }
        }
        for (String nullKey : nullKeys) {
            hash.remove(nullKey);
        }
        return hash;
    }
}
