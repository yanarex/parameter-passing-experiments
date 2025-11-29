public class ReferencevsValue {
    
        static int x = 1;

        static void test(int a, int b) {
            a = a + 1;
            b = b + 1;
        }

        public static void main(String[] args) {
            x = 1;
            test(x, x);
            System.out.println("x = " + x);
        }
}
