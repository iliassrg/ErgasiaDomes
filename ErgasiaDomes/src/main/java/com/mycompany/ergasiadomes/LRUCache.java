package com.mycompany.ergasiadomes;

import java.util.HashMap;

/**
 * Υλοποίηση μιας LRU κρυφής μνήμης με χρήση γενικών τύπων (K, V).
 *
 * @param <K> Ο τύπος του κλειδιού.
 * @param <V> Ο τύπος της τιμής.
 */
public class LRUCache<K, V> implements Cache<K, V> {
    // Εσωτερική κλάση για τους κόμβους της διπλά συνδεδεμένης λίστας.
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
    private final CacheNode pseudoHead, pseudoTail; // Ψευδο-κεφαλή και ουρά για εύκολη διαχείριση.

    // Κατασκευαστής που δέχεται τη μέγιστη χωρητικότητα.
    public LRUCache(int capacity) {
        this.maxCapacity = capacity;
        this.storage = new HashMap<>(capacity);

        // Αρχικοποίηση ψευδο-κεφαλής και ουράς.
        pseudoHead = new CacheNode(null, null);
        pseudoTail = new CacheNode(null, null);

        pseudoHead.next = pseudoTail;
        pseudoTail.prev = pseudoHead;
    }

    // Επιστρέφει την τιμή για το κλειδί, αν υπάρχει, αλλιώς null.
    @Override
    public V get(K key) {
        CacheNode node = storage.get(key);
        if (node == null) {
            return null; // Το κλειδί δεν υπάρχει.
        }
        moveToFront(node); // Μετακίνηση του κόμβου στην κορυφή.
        return node.value;
    }

    // Εισάγει ή ενημερώνει ένα ζεύγος κλειδιού-τιμής.
    @Override
    public void put(K key, V value) {
        CacheNode node = storage.get(key);

        if (node != null) {
            // Αν το κλειδί υπάρχει ήδη, ενημερώνουμε την τιμή.
            node.value = value;
            moveToFront(node);
        } else {
            // Αν το κλειδί δεν υπάρχει, δημιουργούμε νέο κόμβο.
            node = new CacheNode(key, value);
            storage.put(key, node);
            addToFront(node);

            if (storage.size() > maxCapacity) {
                removeLeastRecentlyUsed();
            }
        }
    }

    // Προσθήκη ενός κόμβου στην κορυφή της λίστας.
    private void addToFront(CacheNode node) {
        node.next = pseudoHead.next;
        node.prev = pseudoHead;
        pseudoHead.next.prev = node;
        pseudoHead.next = node;
    }

    // Μετακίνηση ενός κόμβου στην κορυφή της λίστας.
    private void moveToFront(CacheNode node) {
        removeNode(node);
        addToFront(node);
    }

    // Αφαίρεση ενός κόμβου από τη λίστα.
    private void removeNode(CacheNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Αφαίρεση του λιγότερο πρόσφατα χρησιμοποιημένου κόμβου.
    private void removeLeastRecentlyUsed() {
        CacheNode toRemove = pseudoTail.prev;
        removeNode(toRemove);
        storage.remove(toRemove.key);
    }
}