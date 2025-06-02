public class vetService {
    protected int serviceRate;
    protected String serviceType;
    protected String petType;
    
    public vetService(String serviceType, int serviceRate, String petType) {
        this.serviceType = serviceType;
        this.serviceRate = serviceRate;
        this.petType = petType;
    }
    
    public int getServiceRate() {
        return serviceRate;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public String getPetType() {
        return petType;
    }
}