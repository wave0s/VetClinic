public class servNeutering extends vetService {
    
    public servNeutering(String petType) {
        super("Neutering", calculateRate(petType), petType);
    }
    
    private static int calculateRate(String petType) {
        switch (petType.toLowerCase()) {
            case "dog":
                return 300;
            case "cat":
                return 200;
            case "rabbit":
                return 150;
            default:
                return 250;
        }
    }
}