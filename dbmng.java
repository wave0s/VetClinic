import java.sql.*;

public class dbmng {
    private static final String URL = "jdbc:mysql://localhost:3306/vet_clinic";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public dbmng() {
        createTable();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS appointments (
                id INT AUTO_INCREMENT PRIMARY KEY,
                service_type VARCHAR(100) NOT NULL,
                pet_type VARCHAR(50) NOT NULL,
                service_rate INT NOT NULL,
                is_paid BOOLEAN DEFAULT FALSE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    
    public void addAppointment(vetService service) {
        String sql = "INSERT INTO appointments (service_type, pet_type, service_rate) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, service.getServiceType());
            pstmt.setString(2, service.getPetType());
            pstmt.setInt(3, service.getServiceRate());
            
            pstmt.executeUpdate();
            System.out.println("Appointment saved to database.");
            
        } catch (SQLException e) {
            System.out.println("Error t: " + e.getMessage());
        }
    }
    
    public void displayAllAppointments() {
        String sql = "SELECT * FROM appointments ORDER BY id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== All Appointments ===");
            boolean found = false;
            
            while (rs.next()) {
                found = true;
                String status = rs.getBoolean("is_paid") ? "[PAID]" : "[UNPAID]";
                System.out.printf("ID: %d | %s for %s - Php%d %s%n",
                    rs.getInt("id"),
                    rs.getString("service_type"),
                    rs.getString("pet_type"),
                    rs.getInt("service_rate"),
                    status);
            }
            
            if (!found) {
                System.out.println("No appointments.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error/s: " + e.getMessage());
        }
    }
    
    public void displayUnpaidAppointments() {
        String sql = "SELECT * FROM appointments WHERE is_paid = FALSE ORDER BY id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== Unpaid Appointments ===");
            boolean hasUnpaid = false;
            
            while (rs.next()) {
                hasUnpaid = true;
                System.out.printf("ID: %d | %s for %s - Php%d [UNPAID]%n",
                    rs.getInt("id"),
                    rs.getString("service_type"),
                    rs.getString("pet_type"),
                    rs.getInt("service_rate"));
            }
            
            if (!hasUnpaid) {
                System.out.println("Nothing to see here.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public vetService getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ? AND is_paid = FALSE";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String serviceType = rs.getString("service_type");
                String petType = rs.getString("pet_type");
                int serviceRate = rs.getInt("service_rate");
                
                if (serviceType.equals("Wellness Checkup")) {
                    return new servwellnessCheckup(petType);
                } else if (serviceType.equals("Neutering")) {
                    return new servNeutering(petType);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return null;
    }
    
    public void markAsPaid(int id) {
        String sql = "UPDATE appointments SET is_paid = TRUE WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Transaction Completee");
            }
            
        } catch (SQLException e) {
            System.out.println("Error updating payment");
        }
    }
}