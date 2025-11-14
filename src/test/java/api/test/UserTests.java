package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import freemarker.log.Logger;

import com.github.javafaker.Faker;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayload;
    
    public org.apache.logging.log4j.Logger logger;

    @BeforeClass
    public void setup() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.number().numberBetween(1, 999999));
        userPayload.setUsername("user_" + faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUserStatus(1);
        
        logger=LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testCreateUser() {
    	logger.info("******* Creating user********");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void testGetUser() {
        Response response = UserEndPoints.getUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdateUser() {
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        // GET after update
        Response after = UserEndPoints.getUser(this.userPayload.getUsername());
        after.then().log().all();
        Assert.assertEquals(after.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
