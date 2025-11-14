package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints2 {

    private static final Logger logger = LogManager.getLogger(UserEndPoints2.class);

    private static ResourceBundle getURL() {
        return ResourceBundle.getBundle("routes");
    }

    public static Response createUser(User payload) {
        String url = getURL().getString("post_url");

        logger.info("==> POST {}", url);

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(url);
    }

    public static Response getUser(String username) {
        String url = getURL().getString("get_url");

        logger.info("==> GET {}", url);

        return given()
                .pathParam("username", username)
                .when()
                .get(url);
    }

    public static Response updateUser(String username, User payload) {
        String url = getURL().getString("update_url");

        logger.info("==> PUT {}", url);

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(url);
    }

    public static Response deleteUser(String username) {
        String url = getURL().getString("delete_url");

        logger.info("==> DELETE {}", url);

        return given()
                .pathParam("username", username)
                .when()
                .delete(url);
    }
}

