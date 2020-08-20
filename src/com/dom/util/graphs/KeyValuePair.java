package com.dom.util.graphs;

public class KeyValuePair<K, V> {

    K key;
    V value;

    public KeyValuePair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
