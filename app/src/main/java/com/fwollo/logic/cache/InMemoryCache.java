package com.fwollo.logic.cache;

import android.support.v4.util.LruCache;

import com.octo.android.robospice.persistence.memory.CacheItem;
import com.octo.android.robospice.persistence.memory.LruCacheObjectPersister;

/**
 * Simple memory based universal storage
 */
public class InMemoryCache extends LruCacheObjectPersister<Object> {

    /**
     * Creates new Lru backed cache with maxSize maximum elements
     * @param maxSize maximum stored elements
     */
    public InMemoryCache(int maxSize) {
        super(Object.class, new LruCache<Object, CacheItem<Object>>(maxSize));
    }

    @Override
    public boolean canHandleClass(Class<?> clazz) {
        return true;
    }
}
