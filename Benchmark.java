public class Benchmark {

    private static final int ARRAY_SIZE   = 10000000;  // 10M ints
    private static final int NUM_CALLS    = 1000;

    // Used to prevent the JIT from optimizing away the array access.
    private static volatile long sink = 0;

    public static void main(String[] args) {
        System.out.println("Array size: " + ARRAY_SIZE);
        System.out.println("Measured calls: " + NUM_CALLS);

        // Create a large array
        int[] bigArray = new int[ARRAY_SIZE];
        for (int i = 0; i < bigArray.length; i++) {
            bigArray[i] = i;
        }

        //  PASS-BY-REFERENCE 
        long startRef = System.nanoTime();
        for (int i = 0; i < NUM_CALLS; i++) {
            passByReference(bigArray);
        }
        long endRef = System.nanoTime();
        long refTimeNs = endRef - startRef;

        //  MEASURE PASS-BY-VALUE (simulated by copying before the call) 
        long startVal = System.nanoTime();
        for (int i = 0; i < NUM_CALLS; i++) {
            int[] copy = bigArray.clone(); // cost of "by value" for a large array
            passByReference(copy);
        }
        long endVal = System.nanoTime();
        long valTimeNs = endVal - startVal;

        // RESULTS 
        double refTimeMs = refTimeNs / 1000000.0;
        double valTimeMs = valTimeNs / 1000000.0;
        double ratio = valTimeNs / (double) refTimeNs;

        System.out.printf("Pass-by-reference total time: %.3f ms%n", refTimeMs);
        System.out.printf("Pass-by-value (copy) total time: %.3f ms%n", valTimeMs);
        System.out.printf("Ratio (value / reference): %.3f%n", ratio);
        System.out.printf("Pass-by-value took about %.0f times longer than pass-by-reference.%n", ratio);

    }

    private static void passByReference(int[] arr) {
        // Read one element to prevent the JVM from optimizing the call away.
        sink += arr[0];
    }
}
