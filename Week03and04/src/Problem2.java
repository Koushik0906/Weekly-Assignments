import java.util.*;

public class Problem2 {

    static class Client {
        String name;
        int riskScore;
        double accountBalance;

        public Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return name + ":" + riskScore;
        }
    }

    public static void main(String[] args) {

        List<Client> clients = new ArrayList<>();

        clients.add(new Client("clientC", 80, 5000));
        clients.add(new Client("clientA", 20, 10000));
        clients.add(new Client("clientB", 50, 7000));

        System.out.println("Bubble Sort (Ascending):");
        bubbleSort(clients);

        System.out.println("\nInsertion Sort (Descending + Balance):");
        insertionSort(clients);

        System.out.println("\nTop Risk Clients:");
        printTopK(clients, 3);
    }

    // 🔹 Bubble Sort (Ascending Risk)
    public static void bubbleSort(List<Client> list) {

        int n = list.size();
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (list.get(j).riskScore > list.get(j + 1).riskScore) {

                    Collections.swap(list, j, j + 1);
                    swaps++;
                }
            }
        }

        System.out.println(list);
        System.out.println("Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (Descending Risk + Balance)
    public static void insertionSort(List<Client> list) {

        for (int i = 1; i < list.size(); i++) {

            Client key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    (list.get(j).riskScore < key.riskScore ||
                            (list.get(j).riskScore == key.riskScore &&
                                    list.get(j).accountBalance < key.accountBalance))) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println(list);
    }

    // 🔹 Top K highest risk clients
    public static void printTopK(List<Client> list, int k) {

        for (int i = 0; i < Math.min(k, list.size()); i++) {
            Client c = list.get(i);
            System.out.println(c.name + "(" + c.riskScore + ")");
        }
    }
}