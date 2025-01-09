package com.mycompany.ergasiadomes;

import java.util.HashMap;

public class LRUCache<K, V> implements Cache<K, V> {
    
    public enum CacheReplacementPolicy {
    LRU("Least Recently Used"),
    MRU("Most Recently Used");
    
    private final String description;
    
    CacheReplacementPolicy(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
        }
    } 
    
    private class CacheNode {
        K key;
        V value;
        CacheNode prev;
        CacheNode next;

        public CacheNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int maxCapacity; // Το μέγιστο μέγεθος της κρυφής μνήμης.
    private final HashMap<K, CacheNode> storage; // Χάρτης για γρήγορη αναζήτηση.
    private CacheNode pseudoHead; // Ψευδο-κεφαλή και ουρά για εύκολη διαχείριση.
    private CacheNode pseudoTail; // Ψευδο-κεφαλή και ουρά για εύκολη διαχείριση.
    private final CacheReplacementPolicy policy;
    private int hitCount;
    private int missCount;

    // Κατασκευαστής που δέχεται τη μέγιστη χωρητικότητα.
    public LRUCache(int capacity, CacheReplacementPolicy policy) {
        this.maxCapacity = capacity;
        this.storage = new HashMap<>(capacity);       
        this.policy = policy;
        this.pseudoHead = null;
        this.pseudoTail = null;
        this.hitCount = 0;
        this.missCount = 0;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    } 
    
    
    @Override
    public V get(K key) {
        CacheNode node = storage.get(key);
        if (node == null) {
            missCount++;
            return null; // Το κλειδί δεν υπάρχει.
        }
        hitCount++;
        moveToTail(node); // Μετακίνηση του κόμβου στην κορυφή.
        return node.value;
    }

    // Εισάγει ή ενημερώνει ένα ζεύγος κλειδιού-τιμής.
    @Override
    public void put(K key, V value) {
        CacheNode node = storage.get(key);

        if (node != null) {
            node.value = value;
            moveToTail(node);
        } else {
            // Αν το κλειδί δεν υπάρχει, δημιουργούμε νέο κόμβο.
            node = new CacheNode(key, value);
            storage.put(key, node);
            addToTail(node);

            if (storage.size() > maxCapacity) {
                removeLeastRecentlyUsed();
            } else {
                removeMostRecentlyUsed();
            }
        }
    }

    // Προσθήκη ενός κόμβου στην κορυφή της λίστας.
    private void addToTail(CacheNode node) {
        if (pseudoHead == null) {
            pseudoHead = node;
            pseudoTail = node;
        } else {
            pseudoTail.next = node;
            node.prev = pseudoTail;
            pseudoTail = node;
        }
    }

    // Μετακίνηση ενός κόμβου στην κορυφή της λίστας.
    private void moveToTail(CacheNode node) {
        if (node == pseudoTail) return;
        removeNode(node);
        addToTail(node);
    }

    // Αφαίρεση ενός κόμβου από τη λίστα.
    private void removeNode(CacheNode node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            pseudoHead = node.next;
        }
        
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            pseudoTail = node.prev;
        }
        
        node.prev = null;
        node.next = null;
    }

    // Αφαίρεση του λιγότερο πρόσφατα χρησιμοποιημένου κόμβου.
    private void removeLeastRecentlyUsed() {
        if (pseudoHead == null) {
            return;
        }
        CacheNode toRemove = pseudoHead;
        storage.remove(toRemove.key);
        pseudoHead = pseudoHead.next;
        if (pseudoHead != null) {
            pseudoHead.prev = null;
        }
    }
    
    private void removeMostRecentlyUsed() {
        if (pseudoTail == null) {
            return;
        }
        CacheNode toRemove = pseudoTail;
        storage.remove(toRemove.key);
        pseudoTail = pseudoTail.prev;
        if (pseudoTail != null) {
            pseudoTail.next = null;
        }
    }

    public String getPolicyDescription() {
        return policy.getDescription();
    }

    @Override
    public String toString() {
        return "Cache using " + policy.toString(); 
    }     
}
