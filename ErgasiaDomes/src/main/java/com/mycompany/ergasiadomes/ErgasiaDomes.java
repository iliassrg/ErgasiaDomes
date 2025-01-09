package com.mycompany.ergasiadomes;

/**
 * Κύρια κλάση για τη δοκιμή της υλοποίησης της LRU κρυφής μνήμης.
 */
public class ErgasiaDomes {

    public static void main(String[] args) {
        // Δημιουργία μιας LRU κρυφής μνήμης με μέγιστο μέγεθος 3.
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);

        // Προσθήκη στοιχείων στην κρυφή μνήμη.
        lruCache.put(1, "Alpha");
        lruCache.put(2, "Beta");
        lruCache.put(3, "Gamma");

        // Ανάκτηση στοιχείων από την κρυφή μνήμη.
        System.out.println("Ανάκτηση του κλειδιού 2: " + lruCache.get(2)); // Εκτυπώνει "Beta"

        // Προσθήκη νέου στοιχείου (4, "Delta").
        // Το παλαιότερο στοιχείο (1, "Alpha") θα αφαιρεθεί λόγω πολιτικής LRU.
        lruCache.put(4, "Delta");

        // Επαλήθευση της λειτουργικότητας:
        System.out.println("Ανάκτηση του κλειδιού 1: " + lruCache.get(1)); // Εκτυπώνει null (αφαιρέθηκε)
        System.out.println("Ανάκτηση του κλειδιού 3: " + lruCache.get(3)); // Εκτυπώνει "Gamma"
        System.out.println("Ανάκτηση του κλειδιού 4: " + lruCache.get(4)); // Εκτυπώνει "Delta"
    }
}