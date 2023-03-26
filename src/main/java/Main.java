import java.util.*;

public class Main {

    public static void main(String[] args) {
        final Map<Integer, Integer> sizeToFreq = new HashMap<>();

        int numberRoutes = 1000;

        for (int i = 0; i < numberRoutes; i++) {
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR=0;
                for (char element : route.toCharArray()) {
                    if (element == 'R') countR++;
                }
                synchronized (sizeToFreq) {
                    Integer n = sizeToFreq.get(countR);
                    if ( n == null ) sizeToFreq.put(countR, 1 );
                    else sizeToFreq.put(countR, ++n);
                    sizeToFreq.notify();
                }
            }).start();
        }

        int key = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Самое частое количество повторений " + key + " (встретилсоь " +
                         sizeToFreq.get(key) + " раз)");
        System.out.println("Другие размеры:");
        sizeToFreq.entrySet().forEach(System.out::println);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
