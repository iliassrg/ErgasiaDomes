package com.mycompany.ergasiadomes;

/**
 * Κύρια κλάση για τη δοκιμή της υλοποίησης της LRU κρυφής μνήμης.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErgasiaDomes {

    public static void main(String[] args) {
        int storage = 100;
        int operations = 100000;
        Random rand = new Random();
        
        runSimulation(storage, operations, LRUCache.CacheReplacementPolicy.LRU);
        runSimulation(storage, operations, LRUCache.CacheReplacementPolicy.MRU);
    }
    
    public static void runSimulation(int storage, int operations, LRUCache.CacheReplacementPolicy policy) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(storage, policy);
        Random rand = new Random();
        List<Integer> keysList = new ArrayList<>();
        
        for (int i = 0; i < 120; i++) {  // 80% επαναλαμβανόμενα κλειδιά
            keysList.add(rand.nextInt(300));  // Περισσότερα επαναλαμβανόμενα κλειδιά για LRU
        }
        
        for (int i = 0; i < 30; i++) {  // 20% λιγότερο επαναλαμβανόμενα κλειδιά
            keysList.add(rand.nextInt(500));  // Λιγότερο συχνά κλειδιά
        }
        
        List<Integer> mruKeysList = new ArrayList<>();
        for (int i = 0; i < 400; i++) {  // 80% επαναλαμβανόμενα κλειδιά για MRU
            mruKeysList.add(rand.nextInt(500));
        }

        for (int i = 0; i < 100; i++) {  // 20% λιγότερο επαναλαμβανόμενα κλειδιά
            mruKeysList.add(rand.nextInt(800));
        }

        for (int i = 0; i < operations; i++) {
            int key;
            if (policy == LRUCache.CacheReplacementPolicy.LRU) {
                key = keysList.get(rand.nextInt(keysList.size()));  // Επαναλαμβανόμενα και λιγότερο επαναλαμβανόμενα κλειδιά για LRU
            } else {
                key = mruKeysList.get(rand.nextInt(mruKeysList.size()));  // Επαναλαμβανόμενα και λιγότερο επαναλαμβανόμενα κλειδιά για MRU
            }

            if (cache.get(key) == null) {
                cache.put(key, rand.nextInt(1000));
            }
        }

        int hits = cache.getHitCount();
        int misses = cache.getMissCount();
        double hitRate = (hits * 100.0) / operations;
        double missRate = (misses * 100.0) / operations;

        System.out.printf("Policy: %s%n", cache.getPolicyDescription());
        System.out.printf("Total operations: %d%n", operations);
        System.out.printf("Cache Hits: %d%n", hits);
        System.out.printf("Cache Misses: %d%n", misses);
        System.out.printf("Hit Rate: %.2f%%%n", hitRate);
        System.out.printf("Miss Rate: %.2f%%%n%n", missRate);    
    }
        
}
