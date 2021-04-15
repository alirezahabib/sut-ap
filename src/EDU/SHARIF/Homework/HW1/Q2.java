package EDU.SHARIF.Homework.HW1;

import java.util.*;

public class Q2 {

    static Set<String> commonsSet = new HashSet<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        input.nextLine();

        // The first input is added without common-check
        String[] words = input.nextLine().toLowerCase().split(" ");
        Collections.addAll(commonsSet, words);

        for (int i = 0; i < n - 1; i++) {
            words = input.nextLine().toLowerCase().split(" ");
            Set<String> newSet = new HashSet<>();
            Collections.addAll(newSet, words);

            commonsSet.retainAll(newSet);
        }

        for (int i = 0; i < n; i++) {
            words = input.nextLine().toLowerCase().split(" ");

            for (String word : words) {
                commonsSet.remove(word);
            }
        }

        if (commonsSet.size() == 0) {
            System.out.println("Nothing in common");
        } else {
            List<String> commonsList = new ArrayList<>(commonsSet);
            commonsList.sort(Collections.reverseOrder());

            for (String word : commonsList) {
                System.out.print(word + " ");
            }
        }
    }
}
