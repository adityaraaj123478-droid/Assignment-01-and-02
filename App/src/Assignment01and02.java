public class Assignment01and02 {
    static class ParkingLot {
        private String[] spots = new String[500]; // Array-based hash table

        public int parkVehicle(String licensePlate) {
            int hash = Math.abs(licensePlate.hashCode() % 500);
            int originalHash = hash;
            int probes = 0;

            // Linear Probing: find next empty spot
            while (spots[hash] != null) {
                hash = (hash + 1) % 500;
                probes++;
                if (hash == originalHash) return -1; // Lot full
            }

            spots[hash] = licensePlate;
            System.out.println("Parked " + licensePlate + " at #" + hash + " (" + probes + " probes)");
            return hash;
        }
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();
        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("XYZ-9999");
    }
}