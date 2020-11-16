# Invite Customers

## Implementation

The application was built using Java/Spring Boot as language/framework, by creating a simple REST API.

### Important

>All distance calculations are being done based on the earth median radius which is considered to be of `6.371km` (see [Wikipedia](https://en.wikipedia.org/wiki/Earth_radius))

### GitHub repository

<https://github.com/rjrb/InviteCustomers>

### SonarCloud Analysis

<https://sonarcloud.io/dashboard?id=rjrb_InviteCustomers>

### Installation

1. You will need Java 11 installed on your system

    > Download Java JDK 11 from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

1. Clone the repository

    ```bash
    git clone https://github.com/rjrb/InviteCustomers.git
    ```

1. CD into the directory `InviteCustomers`
1. Run the following command to execute the application:

    ```bash
    gradlew bootRun
    ```

1. Open your web browser (or Postman or similar and run a `GET` request), and navigate to: <http://localhost:8080/invite>

    - It will pick by default the `customers.txt` file located in the root of the project

1. Alternatively, you can `POST` to <http://localhost:8080/invite> with a JSON list of customers to filter the results. For example, you can send something like:

    ```json
    [
        {"user_id": 1, "name": "Alice Cahill", "latitude": "51.9289300", "longitude": "-10.27699"},
        {"user_id": 2, "name": "Ian McArdle", "latitude": "51.8856167", "longitude": "-10.4240951"},
        {"user_id": 3, "name": "Jack Enright", "latitude": "52.3191841", "longitude": "-8.5072391"},
        {"user_id": 6, "name": "Theresa Enright", "latitude": "53.1229599", "longitude": "-6.2705202"},
        {"user_id": 4, "name": "Ian Kehoe", "latitude": "53.2451022", "longitude": "-6.238335"},
        {"user_id": 5, "name": "Nora Dempsey", "latitude": "53.1302756", "longitude": "-6.2397222"}
    ]
    ```

1. The output for the provided customers file can be found at `output.json` at the root of the project
1. To run the tests, run the following command:

    ```bash
    gradlew test
    ```

    - Test results can be found at: `build/reports/tests/test`

1. The sources can be found at: `src/main/java/com/ramirezblauvelt/invitecustomers`
1. The tests can be found at: `src/test/java/com/ramirezblauvelt/invitecustomers`
1. The configuration of the application can be found at: `src/main/resources/application.yaml`
1. If you want to build the application and execute all tests, create a coverage report and update SonarCloud report, you can run:

    ```bash
    gradlew clean build jacocoTestReport sonarqube
    ```

    - Generated binaries can be found at: `build/libs`
    - Test results can be found at: `build/reports/tests/test`
    - Coverage results can be found at: `build/jacoco/test/html`

### Important

>All distance calculations are being done based on the earth median radius which is considered to be of `6.371km` (see [Wikipedia](https://en.wikipedia.org/wiki/Earth_radius))
