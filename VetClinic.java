import java.util.Scanner;

public class VetClinic {
    private static dbmng database = new dbmng();
    private static Scanner io = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Lance's Veterinary Clinic ===");
        
        while (true) {
            displayMenu();
            int servType = io.nextInt();
            
            switch (servType) {
                case 1:
                    bookWellnessCheckup();
                    break;
                case 2:
                    bookNeutering();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    billPatient();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n1. Wellness Checkup");
        System.out.println("2. Neutering");
        System.out.println("3. View Transactions");
        System.out.println("4. Bill Patient");
        System.out.println("5. Exit");
        System.out.print("Choose option: ");
    }
    
    private static void bookWellnessCheckup() {
        io.nextLine();
        System.out.print("Pet type: ");
        String petType = io.nextLine();
        
        servwellnessCheckup checkup = new servwellnessCheckup(petType);
        database.addAppointment(checkup);
        
        System.out.println("Booked! Cost: Php" + checkup.getServiceRate());
    }
    
    private static void bookNeutering() {
        io.nextLine();
        System.out.print("Pet type: ");
        String petType = io.nextLine();
        
        servNeutering neutering = new servNeutering(petType);
        database.addAppointment(neutering);
        
        System.out.println("Booked! Cost: Php" + neutering.getServiceRate());
    }
    
    private static void viewAppointments() {
        database.displayAllAppointments();
    }
    
    private static void billPatient() {
        database.displayUnpaidAppointments();
        
        
        System.out.print("Enter ID::: ");
        int appointmentId = io.nextInt();
        
        vetService appointment = database.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Invalid .");
            return;
        }
        
        int totalBill = appointment.getServiceRate();
        System.out.println("Total bill: Php" + totalBill);
        System.out.print("Enter payment amount: Php");
        int paidAmount = io.nextInt();
        
        if (paidAmount >= totalBill) {
            database.markAsPaid(appointmentId);
            int change = paidAmount - totalBill;
            System.out.println("Payment ssuccessful!");
            if (change > 0) {
                System.out.println("Change: Php" + change);
            }
        } else {
            System.out.println("Insufficient");
        }
    }
}