public class servwellnessCheckup extends vetService {
    
    public servwellnessCheckup(String petType) {
        super("Wellness Checkup", calculateRate(petType), petType);
    }
    
    private static int calculateRate(String petType) {
        switch (petType.toLowerCase()) {
            case "dog":
                return 75;
            case "cat":
                return 60;
            case "bird":
                return 45;
            case "rabbit":
                return 50;
            default:
                return 65;
        }
    }
}