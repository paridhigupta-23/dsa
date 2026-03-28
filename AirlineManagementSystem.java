package dsa;
import java.util.Scanner;

// ================= FLIGHT CLASS =================
class Flight {
    String id, departure, arrival;
    double price, rating;
    Flight next;

    Flight(String id, String departure, String arrival,
           double price, double rating) {

        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.rating = rating;
        this.next = null;
    }
}

// ================= PASSENGER CLASS =================
class Passenger {
    String id, name;
    int age;
    Passenger next;

    Passenger(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.next = null;
    }
}

// ================= MAIN SYSTEM CLASS =================
public class AirlineManagementSystem {

    Flight flightHead = null;
    Passenger passengerHead = null;

    int[][] seats = new int[5][4];   // 2D Array
    Scanner sc = new Scanner(System.in);

    // ================= ADD METHODS =================

    public void addFlight(String id, String departure, String arrival,
                          double price, double rating) {

        Flight newFlight = new Flight(id, departure, arrival, price, rating);

        if (flightHead == null)
            flightHead = newFlight;
        else {
            Flight temp = flightHead;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newFlight;
        }
    }

    public void addPassenger(String id, String name, int age) {

        Passenger newPassenger = new Passenger(id, name, age);

        if (passengerHead == null)
            passengerHead = newPassenger;
        else {
            Passenger temp = passengerHead;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newPassenger;
        }
    }

    // ================= DISPLAY METHODS =================

    public void displayFlights() {
        Flight temp = flightHead;
        while (temp != null) {
            System.out.println(temp.id +
                    " | Departure: " + temp.departure +
                    " | Arrival: " + temp.arrival +
                    " | Price: ₹" + temp.price +
                    " | Rating: " + temp.rating);
            temp = temp.next;
        }
    }

    public void displayPassengers() {
        Passenger temp = passengerHead;
        while (temp != null) {
            System.out.println(temp.id +
                    " | Name: " + temp.name +
                    " | Age: " + temp.age);
            temp = temp.next;
        }
    }

    // ================= REMOVE METHODS =================

    public void removeFlight(String id) {

        if (flightHead == null) return;

        if (flightHead.id.equals(id)) {
            flightHead = flightHead.next;
            return;
        }

        Flight temp = flightHead;
        while (temp.next != null && !temp.next.id.equals(id))
            temp = temp.next;

        if (temp.next != null)
            temp.next = temp.next.next;
    }

    public void removePassenger(String id) {

        if (passengerHead == null) return;

        if (passengerHead.id.equals(id)) {
            passengerHead = passengerHead.next;
            return;
        }

        Passenger temp = passengerHead;
        while (temp.next != null && !temp.next.id.equals(id))
            temp = temp.next;

        if (temp.next != null)
            temp.next = temp.next.next;
    }

    // ================= UPDATE =================

    public void updateFlightPrice(String id, double newPrice) {

        Flight temp = flightHead;

        while (temp != null) {
            if (temp.id.equals(id)) {
                temp.price = newPrice;
                return;
            }
            temp = temp.next;
        }
    }

    // ================= LINEAR SEARCH =================

    public void searchPassengerById(String id) {

        Passenger temp = passengerHead;

        while (temp != null) {
            if (temp.id.equals(id)) {
                System.out.println("Passenger Found: " + temp.name);
                return;
            }
            temp = temp.next;
        }

        System.out.println("Passenger Not Found");
    }

    public void searchFlightByArrival(String arrival) {

        Flight temp = flightHead;

        while (temp != null) {
            if (temp.arrival.equalsIgnoreCase(arrival)) {
                System.out.println("Flight Found: " + temp.id);
                return;
            }
            temp = temp.next;
        }

        System.out.println("Flight Not Found");
    }

    // ================= MERGE SORT (FLIGHT PRICE) =================

    public void sortFlightPriceMerge() {
        flightHead = mergeSort(flightHead);
    }

    private Flight mergeSort(Flight head) {

        if (head == null || head.next == null)
            return head;

        Flight middle = getMiddle(head);
        Flight nextOfMiddle = middle.next;
        middle.next = null;

        Flight left = mergeSort(head);
        Flight right = mergeSort(nextOfMiddle);

        return merge(left, right);
    }

    private Flight merge(Flight a, Flight b) {

        if (a == null) return b;
        if (b == null) return a;

        Flight result;

        if (a.price <= b.price) {
            result = a;
            result.next = merge(a.next, b);
        } else {
            result = b;
            result.next = merge(a, b.next);
        }

        return result;
    }

    private Flight getMiddle(Flight head) {

        Flight slow = head;
        Flight fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // ================= QUICK SORT (PASSENGER AGE) =================

    public void sortPassengerAgeQuick() {
        passengerHead = quickSort(passengerHead);
    }

    private Passenger quickSort(Passenger head) {

        if (head == null || head.next == null)
            return head;

        Passenger pivot = head;
        Passenger smaller = null, greater = null;

        Passenger current = head.next;

        while (current != null) {
            Passenger next = current.next;

            if (current.age < pivot.age) {
                current.next = smaller;
                smaller = current;
            } else {
                current.next = greater;
                greater = current;
            }

            current = next;
        }

        smaller = quickSort(smaller);
        greater = quickSort(greater);

        return concatenate(smaller, pivot, greater);
    }

    private Passenger concatenate(Passenger small,
                                  Passenger pivot,
                                  Passenger great) {

        pivot.next = great;

        if (small == null)
            return pivot;

        Passenger temp = small;

        while (temp.next != null)
            temp = temp.next;

        temp.next = pivot;

        return small;
    }

    // ================= BINARY SEARCH (PRICE) =================

    public void binarySearchFlightPrice(double key) {

        sortFlightPriceMerge();

        Flight temp = flightHead;

        while (temp != null) {
            if (temp.price == key) {
                System.out.println("Price Found in Flight: " + temp.id);
                return;
            }
            temp = temp.next;
        }

        System.out.println("Price Not Found");
    }

    // ================= SEARCH AVAILABLE SEATS =================

    public void searchAvailableSeat() {

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {

                if (seats[i][j] == 0)
                    System.out.println("Available Seat at Row "
                            + i + " Column " + j);
            }
        }
    }
}