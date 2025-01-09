/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.ergasiadomes;

/**
 *
 * @author emmch
 */
/**
* A cache interface
*
* @param <K> the key type
* @param <V> the value type
*/
public interface Cache<K, V> {
    /**
    * Get the value for a key. Returns null if the key is not
    * in the cache.
    *
    * @param key the key
     * @return 
    */
    V get(K key);
    /**
    * Put a new key value pair in the cache
    *
    * @param key the key
    * @param value the value
    */
    void put(K key, V value);
}