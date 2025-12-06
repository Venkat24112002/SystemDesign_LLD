package HashMap;

class Entry<K,V> {

    K key;
    V value;
    Entry next;

    Entry(K k, V v){
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}


public class MyHashMap<K,V> {

    private static final int INITIAL_SIZE = (1<<4); //16
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    Entry[] hashTable;

    public MyHashMap(){
        hashTable = new Entry[INITIAL_SIZE];
    }

    public MyHashMap(int capacity){
        int tableSize = tableSizeFor(capacity);
        hashTable = new Entry[tableSize];
    }

    int tableSizeFor(int cap){
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n<0) ? 1 : (n > MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n+1;
    }


    public void put(K key, V value) {

        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];

        if(node == null) {
            Entry newNode = new Entry(key,value);
//            Entry<K,V> newNode = new Entry<>(key, value);
            hashTable[hashCode] = newNode;
        } else {
            Entry previousNode = node;
            while (node != null) {
                if (node.key == key) {
                    node.value = value;
                    return;
                }
                previousNode = node;
                node = node.next;
            }
            Entry newNode = new Entry(key,value);
            previousNode.next = newNode;
        }
    }


    public V get(K key) {

        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];

        while(node != null) {
            if(node.key.equals(key)) {
                return (V)node.value;
            }
            node = node.next;
        }
        return null;
    }

    public static void main(String []args){
        MyHashMap<Integer, String> map = new MyHashMap<>(7);
        map.put(1, "hi");
        map.put(2, "my");
        map.put(3, "name");
        map.put(4, "is");
        map.put(5, "Venkat");
        map.put(6, "how");
        map.put(7, "are");
        map.put(8, "you");
        map.put(9, "friends");
        map.put(10, "?");

        String value = map.get(8);
        System.out.println(value);
    }
}



//class Entry<K,V> {
//    K key;
//    V value;
//    Entry next;
//
//    Entry(K k, V v){
//        key = k;
//        value = v;
//    }
//
//    public K getKey() {
//        return key;
//    }
//
//    public V getValue() {
//        return value;
//    }
//
//    public void setKey(K key) {
//        this.key = key;
//    }
//
//    public void setValue(V value) {
//        this.value = value;
//    }
//}

//public class MyHashMap<K,V> {
//
//    private static final int INITIAL_SIZE = (1<<4); //16
//    private static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    private Entry[] hashTable;
//    private final ReadWriteLock lock = new ReentrantReadWriteLock();
//
//    public MyHashMap(){
//        hashTable = new Entry[INITIAL_SIZE];
//    }
//
//    public MyHashMap(int capacity){
//        int tableSize = tableSizeFor(capacity);
//        hashTable = new Entry[tableSize];
//    }
//
//    int tableSizeFor(int cap){
//        int n = cap - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n<0) ? 1 : (n > MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n+1;
//    }
//
//    public void put(K key, V value) {
//        lock.writeLock().lock();
//        try {
//            int hashCode = Math.abs(key.hashCode() % hashTable.length);
//            Entry node = hashTable[hashCode];
//
//            if(node == null) {
//                Entry newNode = new Entry(key, value);
//                hashTable[hashCode] = newNode;
//            } else {
//                Entry previousNode = node;
//                while (node != null) {
//                    if (node.key.equals(key)) {  // Use equals() instead of ==
//                        node.value = value;
//                        return;
//                    }
//                    previousNode = node;
//                    node = node.next;
//                }
//                Entry newNode = new Entry(key, value);
//                previousNode.next = newNode;
//            }
//        } finally {
//            lock.writeLock().unlock();
//        }
//    }
//
//    public V get(K key) {
//        lock.readLock().lock();
//        try {
//            int hashCode = Math.abs(key.hashCode() % hashTable.length);
//            Entry node = hashTable[hashCode];
//
//            while(node != null) {
//                if(node.key.equals(key)) {
//                    return (V)node.value;
//                }
//                node = node.next;
//            }
//            return null;
//        } finally {
//            lock.readLock().unlock();
//        }
//    }
//
//    public V remove(K key) {
//        lock.writeLock().lock();
//        try {
//            int hashCode = Math.abs(key.hashCode() % hashTable.length);
//            Entry node = hashTable[hashCode];
//            Entry prev = null;
//
//            while(node != null) {
//                if(node.key.equals(key)) {
//                    if(prev == null) {
//                        hashTable[hashCode] = node.next;
//                    } else {
//                        prev.next = node.next;
//                    }
//                    return (V)node.value;
//                }
//                prev = node;
//                node = node.next;
//            }
//            return null;
//        } finally {
//            lock.writeLock().unlock();
//        }
//    }
//
//    public int size() {
//        lock.readLock().lock();
//        try {
//            int count = 0;
//            for(Entry entry : hashTable) {
//                Entry node = entry;
//                while(node != null) {
//                    count++;
//                    node = node.next;
//                }
//            }
//            return count;
//        } finally {
//            lock.readLock().unlock();
//        }
//    }
//
//    public static void main(String []args) throws InterruptedException {
//        MyHashMap<Integer, String> map = new MyHashMap<>(7);
//
//        // Test thread safety with multiple threads
//        Thread[] threads = new Thread[5];
//
//        for(int i = 0; i < threads.length; i++) {
//            final int threadId = i;
//            threads[i] = new Thread(() -> {
//                for(int j = 0; j < 100; j++) {
//                    int key = threadId * 100 + j;
//                    map.put(key, "Thread-" + threadId + "-Value-" + j);
//                }
//            });
//            threads[i].start();
//        }
//
//        // Wait for all threads to complete
//        for(Thread thread : threads) {
//            thread.join();
//        }
//
//        // Verify data
//        System.out.println("Total entries: " + map.size());
//        System.out.println("Sample value: " + map.get(250));
//
//        // Original test
//        map.put(1, "hi");
//        map.put(2, "my");
//        map.put(3, "name");
//        map.put(4, "is");
//        map.put(5, "Venkat");
//        map.put(6, "how");
//        map.put(7, "are");
//        map.put(8, "you");
//        map.put(9, "friends");
//        map.put(10, "?");
//
//        String value = map.get(8);
//        System.out.println(value);
//    }
//}
