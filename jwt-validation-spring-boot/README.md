# JWT Validation and Claims Extraction (Spring Boot)

A complete Java Spring Boot Maven project for validating JWT signatures, checking token expiration, and extracting username/roles. Swagger UI is included.

## Requirements
- JDK 17 or later
- Maven 3.6+
- Internet connection on the first Maven build to download dependencies

## Run the project
1. Extract the ZIP file.
2. Open the extracted `jwt-validation-spring-boot` folder in IntelliJ IDEA / Eclipse / VS Code, or open a terminal there.
3. Run:

   ```bash
   mvn spring-boot:run
   ```

4. Open Swagger UI:

   `http://localhost:8080/swagger-ui/index.html`

## Test using Swagger UI

### 1. Generate a valid demo token
Open `POST /api/jwt/generate`, click **Try it out**, and send:

```json
{
  "username": "nehru",
  "roles": ["USER", "ADMIN"]
}
```

Copy the `token` value from the response.

### 2. Validate the token
Open `POST /api/jwt/validate` and send:

```json
{
  "token": "PASTE_TOKEN_HERE"
}
```

Expected successful response:

```json
{
  "valid": true,
  "message": "Token Valid",
  "username": "nehru",
  "roles": ["USER", "ADMIN"]
}
```

### 3. Test invalid tokens
- Alter one or more characters in the token and submit it.
- Submit a random string.
- Submit an expired JWT (a token whose `exp` time is in the past).

Expected response (HTTP 401):

```json
{
  "valid": false,
  "message": "Invalid Token",
  "username": null,
  "roles": []
}
```

## API summary

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/jwt/generate` | Generates a one-hour demo JWT for testing |
| POST | `/api/jwt/validate` | Verifies signature/expiration and extracts username/roles |

## Main implementation
- `JwtService.validateToken()` parses signed claims using the configured secret key. JJWT rejects invalid signatures and expired tokens.
- `JwtController.validate()` returns username (`sub`) and the `roles` claim.
- The extra `/generate` endpoint is only a convenient Swagger testing helper.

## Important security note
The key in `application.properties` is for local demonstration only. For real deployments, use a strong random secret stored outside source control (for example, an environment variable or secret manager), enforce HTTPS, and never expose a public token-generation endpoint.
