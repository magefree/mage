package mage.client.util;

import com.google.common.base.Function;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.ForwardingLoadingCache;
import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.cache.CacheBuilder.newBuilder;

public class SoftValuesLoadingCache<K, V> extends ForwardingLoadingCache<K, Optional<V>> {

    private final LoadingCache<K, Optional<V>> cache;
    private static final Logger logger = Logger.getLogger(SoftValuesLoadingCache.class);

    public SoftValuesLoadingCache(CacheLoader<K, Optional<V>> loader) {
        cache = newBuilder()
                .maximumSize(3000)
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .softValues()
                .build(loader);
    }

    @Override
    protected LoadingCache<K, Optional<V>> delegate() {
        return cache;
    }

    public V getOrThrow(K key) {
        V v = getOrNull(key);
        if (v == null) {
            throw new NullPointerException();
        }
        return v;
    }

    public V getOrNull(K key) {
        try {
            return get(key).orElse(null);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof OutOfMemoryError) {
                logger.warn("Out of memory error: try to increase free memory in launcher options (-xmx param)");
                return null;
            } else {
                throw new RuntimeException(e);
            }
        } catch (Throwable e) {
            return null;
        }
    }

    public V peekIfPresent(K key) {
        Optional<V> value = getIfPresent(key);
        if (value != null) {
            return value.orElse(null);
        }
        return null;
    }

    public static <K, V> SoftValuesLoadingCache<K, V> from(CacheLoader<K, Optional<V>> loader) {
        return new SoftValuesLoadingCache<>(loader);
    }

    public static <K, V> SoftValuesLoadingCache<K, V> from(Function<K, V> loader) {
        return from(CacheLoader.from(k -> Optional.ofNullable(loader.apply(k))));
    }

}
