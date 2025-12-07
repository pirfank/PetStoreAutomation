PetStoreAutomation

This project automates API testing for the PetStore Swagger API using Java, TestNG, and RestAssured. The framework defines and tests API endpoints related to user management (create, retrieve, update, delete users), with routes and test logic encapsulated in dedicated classes.

Key Features:

Uses RestAssured for HTTP requests and responses.
Implements logging for API request actions.
Contains dedicated classes for endpoint routes and test execution.
Modular structure for easy expansion to other PetStore modules such as Store and Pet management.
Project Structure:

src/test/java/api/endpoints/: Contains Java classes for endpoint definitions (e.g., Routes.java).
Routes.java: Centralizes URL paths for the User, Store, and Pet modules.
UserEndPoints.java & UserEndPoints2.java: Provide methods to call user-related API operations with support for dynamic endpoint configuration and logging.
How it works:

You can automate user creation, fetching, updating, and deletion via dedicated test methods.
The framework is designed to be extended to test other PetStore API endpoints beyond user management.
Tech stack:
Java, Maven, TestNG, RestAssured, Log4j
