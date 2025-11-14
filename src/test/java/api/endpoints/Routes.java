package api.endpoints;

/**
 * Routes class containing all API endpoints for PetStore Swagger API
 * Swagger URL: https://petstore.swagger.io/
 */
public class Routes {

    // Base URL
    public static final String BASE_URL = "https://petstore.swagger.io/v2";

    // User module endpoints
    public static final String post_url = BASE_URL + "/user";
    public static final String get_url = BASE_URL + "/user/{username}";
    public static final String update_url = BASE_URL + "/user/{username}";
    public static final String delete_url = BASE_URL + "/user/{username}";

    // Alternative naming (if you prefer uppercase constants)
    public static final String CREATE_USER = BASE_URL + "/user";
    public static final String GET_USER = BASE_URL + "/user/{username}";
    public static final String UPDATE_USER = BASE_URL + "/user/{username}";
    public static final String DELETE_USER = BASE_URL + "/user/{username}";

    // Store module endpoints (for future use)
    public static final String STORE_ORDER = BASE_URL + "/store/order";
    public static final String STORE_INVENTORY = BASE_URL + "/store/inventory";

    // Pet module endpoints (for future use)
    public static final String PET_URL = BASE_URL + "/pet";
    public static final String PET_FIND_BY_STATUS = BASE_URL + "/pet/findByStatus";
    public static final String PET_FIND_BY_TAGS = BASE_URL + "/pet/findByTags";
}

