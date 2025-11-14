package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

@Listeners(ExtentReportManager.class)

public class DDTests {

    // Initialize Logger
    private static final Logger logger = LogManager.getLogger(DDTests.class);

    /**
     * Retry mechanism for GET requests (handles PetStore API delays)
     */
    private int retryGet(String username) {
        logger.debug("Retrying GET request for user: {}", username);
        Response res = null;
        
        for (int i = 0; i < 5; i++) {
            res = UserEndPoints.getUser(username);
            logger.debug("GET attempt {} - Status: {}", (i + 1), res.getStatusCode());
            
            if (res.getStatusCode() == 200) {
                logger.info("✓ User found on attempt {}: {}", (i + 1), username);
                return 200;
            }
            
            try { 
                Thread.sleep(800); 
            } catch (Exception ignored) {
                logger.warn("Sleep interrupted during retry");
            }
        }
        
        logger.error("✗ Failed to get user after 5 attempts: {}", username);
        return res.getStatusCode();
    }

    /**
     * Retry mechanism for DELETE requests (handles PetStore API delays)
     */
    private int retryDelete(String username) {
        logger.debug("Retrying DELETE request for user: {}", username);
        Response res = null;
        
        for (int i = 0; i < 5; i++) {
            res = UserEndPoints.deleteUser(username);
            logger.debug("DELETE attempt {} - Status: {}", (i + 1), res.getStatusCode());
            
            if (res.getStatusCode() == 200) {
                logger.info("✓ User deleted on attempt {}: {}", (i + 1), username);
                return 200;
            }
            
            try { 
                Thread.sleep(800); 
            } catch (Exception ignored) {
                logger.warn("Sleep interrupted during retry");
            }
        }
        
        logger.error("✗ Failed to delete user after 5 attempts: {}", username);
        return res.getStatusCode();
    }

    /**
     * Data-driven test: Create, Verify, and Delete User
     */
    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testCreateAndDeleteUser(String userid, String username,
                                        String firstName, String lastName,
                                        String email, String password,
                                        String phone, String userStatus) {

        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║  TEST START: User = {} (ID: {})", username, userid);
        logger.info("╚════════════════════════════════════════════════════════════════╝");

        // ===== PRE-CLEAN =====
        logger.info("→ Step 1: Pre-cleanup - Deleting user if exists: {}", username);
        UserEndPoints.deleteUser(username);
        logger.debug("Pre-cleanup completed for: {}", username);

        // ===== CREATE USER OBJECT =====
        logger.info("→ Step 2: Creating User object");
        User user = new User();
        user.setId(Integer.parseInt(userid));
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserStatus(Integer.parseInt(userStatus));
        
        logger.debug("User payload: [ID={}, Username={}, Name={} {}, Email={}]", 
                    userid, username, firstName, lastName, email);

        // ===== CREATE USER =====
        logger.info("→ Step 3: Creating user via API: {}", username);
        Response createRes = UserEndPoints.createUser(user);
        
        logger.info("Create Response Status: {}", createRes.getStatusCode());
        logger.debug("Create Response Body: {}", createRes.getBody().asString());
        
        Assert.assertEquals(createRes.getStatusCode(), 200,
                "❌ Create failed for " + username);
        logger.info("✓ User created successfully: {}", username);

        // ===== VERIFY CREATION =====
        logger.info("→ Step 4: Verifying user creation (with retry logic)");
        int getStatus = retryGet(username);
        
        Assert.assertEquals(getStatus, 200,
                "❌ User not found after creation: " + username);
        logger.info("✓ User verification successful: {}", username);

        // ===== DELETE USER =====
        logger.info("→ Step 5: Deleting user (with retry logic)");
        int deleteCode = retryDelete(username);
        
        Assert.assertEquals(deleteCode, 200,
                "❌ Delete failed for: " + username);
        logger.info("✓ User deleted successfully: {}", username);

        logger.info("╔════════════════════════════════════════════════════════════════╗");
        logger.info("║  TEST PASSED: User = {} ✓", username);
        logger.info("╚════════════════════════════════════════════════════════════════╝\n");
    }
}
