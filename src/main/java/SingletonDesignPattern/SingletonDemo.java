package SingletonDesignPattern;

public class SingletonDemo {

    public static void main(String[] args) {

        // ===== 1. Eager Initialization =====
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("Eager same instance: " + (eager1 == eager2));

        // ===== 2. Lazy Initialization (NOT thread-safe) =====
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        System.out.println("Lazy same instance: " + (lazy1 == lazy2));

        // ===== 3. Double-Checked Locking (Thread-safe + Lazy) =====
        DoubleCheckedSingleton dc1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton dc2 = DoubleCheckedSingleton.getInstance();
        System.out.println("DoubleChecked same instance: " + (dc1 == dc2));

        // ===== 4. Enum Singleton (Best & Safest) =====
        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;
        System.out.println("Enum same instance: " + (enum1 == enum2));
    }
}

/*
 * ======================================================
 * 1️⃣ EAGER INITIALIZATION SINGLETON
 * - Instance is created at class loading time
 * - Thread-safe due to JVM class loading
 * - Instance is created even if not used
 * ======================================================
 */
class EagerSingleton {

    // Instance created immediately when class is loaded
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private constructor prevents external instantiation
    private EagerSingleton() { }

    // Global access point to the singleton instance
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}

/*
 * ======================================================
 * 2️⃣ LAZY INITIALIZATION SINGLETON (NOT THREAD-SAFE)
 * - Instance is created only when requested
 * - NOT safe in multithreaded environments
 * ======================================================
 */
class LazySingleton {

    // Instance is not created initially
    private static LazySingleton instance;

    // Private constructor
    private LazySingleton() { }

    // Instance created only when getInstance() is called
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

/*
 * ======================================================
 * 3️⃣ DOUBLE-CHECKED LOCKING SINGLETON
 * - Lazy initialization
 * - Thread-safe
 * - Uses 'volatile' to avoid instruction reordering
 * ======================================================
 */
class DoubleCheckedSingleton {

    // Volatile ensures visibility and prevents half-initialized object
    private static volatile DoubleCheckedSingleton instance;

    // Private constructor
    private DoubleCheckedSingleton() { }

    // Instance created lazily with minimal synchronization overhead
    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {                       // First check (no lock)
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {               // Second check (with lock)
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}

/*
 * ======================================================
 * 4️⃣ ENUM SINGLETON (RECOMMENDED)
 * - Simplest and safest
 * - Thread-safe by default
 * - Safe from reflection and serialization attacks
 * ======================================================
 */
enum EnumSingleton {

    // JVM guarantees only one instance of enum
    INSTANCE;

    // Example method
    public void doSomething() {
        System.out.println("Enum Singleton doing work...");
    }
}
