package org.json.simple.map;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleHashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 5;
    private static final float DEFAULT_LOAD_FACTOR = 0.55f;

    private int size;
    private int capacity;
    private float loadFactor;
    private TreeMap<K, V>[] table;

    public SimpleHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public SimpleHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public SimpleHashMap(int capacity, float loadFactor) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Invalid capacity: " + capacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Invalid load factor: " + loadFactor);

        this.capacity = capacity;
        this.loadFactor = loadFactor;
        table = new TreeMap[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = getHash(key);
        TreeMap<K, V> bucket = table[hash];
        return bucket != null && bucket.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (TreeMap<K, V> bucket : table) {
            if (bucket != null && bucket.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int hash = getHash(key);
        TreeMap<K, V> bucket = table[hash];
        return (bucket != null) ? bucket.get(key) : null;
    }

    @Override
    public V put(K key, V value) {
        int hash = getHash(key);
        TreeMap<K, V> bucket = table[hash];
        if (bucket == null) {
            bucket = new TreeMap<>();
            table[hash] = bucket;
        }
        V oldValue = bucket.put(key, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        int hash = getHash(key);
        TreeMap<K, V> bucket = table[hash];
        if (bucket != null) {
            V removedValue = bucket.remove(key);
            if (removedValue != null) {
                size--;
                return removedValue;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (TreeMap<K, V> bucket : table) {
            if (bucket != null) {
                keys.addAll(bucket.keySet());
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (TreeMap<K, V> bucket : table) {
            if (bucket != null) {
                values.addAll(bucket.values());
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (TreeMap<K, V> bucket : table) {
            if (bucket != null) {
                entries.addAll(bucket.entrySet());
            }
        }
        return entries;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return Map.super.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Map.super.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Map.super.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return Map.super.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return Map.super.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return Map.super.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return Map.super.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return Map.super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Map.super.merge(key, value, remappingFunction);
    }

    private int getHash(Object key) {
        return (key != null) ? Math.abs(key.hashCode() % capacity) : 0;
    }
}
