import java.util.*;

public class Problem08 {

    // Parking spot states
    enum Status {
        EMPTY, OCCUPIED, DELETED
    }

    static class ParkingSpot {
        String licensePlate;
        long entryTime;
        Status status;

        ParkingSpot() {
            this.status = Status.EMPTY;
        }
    }

    private static final int SIZE = 500;
    private static ParkingSpot[] table = new ParkingSpot[SIZE];

    private static int totalVehicles = 0;
    private static int totalProbes = 0;

    static {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new ParkingSpot();
        }
    }

    public static void main(String[] args) {

        parkVehicle("ABC1234");
        parkVehicle("ABC1235");
        parkVehicle("XYZ9999");

        exitVehicle("ABC1234");

        getStatistics();
    }

    // Hash function
    private static int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % SIZE;
    }

    // Park vehicle using linear probing
    public static void parkVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (table[index].status == Status.OCCUPIED) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index].licensePlate = licensePlate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = Status.OCCUPIED;

        totalVehicles++;
        totalProbes += probes;

        System.out.println("Vehicle " + licensePlate +
                " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public static void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (table[index].status != Status.EMPTY) {

            if (table[index].status == Status.OCCUPIED &&
                    table[index].licensePlate.equals(licensePlate)) {

                long durationMillis =
                        System.currentTimeMillis() - table[index].entryTime;

                double hours = durationMillis / (1000.0 * 60 * 60);
                double fee = hours * 5; // $5/hour

                table[index].status = Status.DELETED;

                System.out.println("Vehicle " + licensePlate +
                        " exited from spot #" + index +
                        ", Duration: " + String.format("%.2f", hours) +
                        " hrs, Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % SIZE;
            probes++;
        }

        System.out.println("Vehicle not found!");
    }

    // Find nearest available spot
    public static int findNearestAvailable() {
        for (int i = 0; i < SIZE; i++) {
            if (table[i].status != Status.OCCUPIED) {
                return i;
            }
        }
        return -1;
    }

    // Statistics
    public static void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : table) {
            if (spot.status == Status.OCCUPIED) {
                occupied++;
            }
        }

        double occupancyRate = (occupied * 100.0) / SIZE;
        double avgProbes = totalVehicles == 0 ? 0 :
                (double) totalProbes / totalVehicles;

        System.out.println("\n--- Parking Stats ---");
        System.out.println("Occupancy: " + String.format("%.2f", occupancyRate) + "%");
        System.out.println("Average Probes: " + String.format("%.2f", avgProbes));
        System.out.println("Nearest Available Spot: #" + findNearestAvailable());
    }
}
