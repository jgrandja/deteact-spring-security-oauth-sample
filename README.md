# Deteact Spring Security OAuth Sample

## Pass Test Scenario

- Startup Spring Boot application `DeteactApplication`
- Go to http://localhost:8080
- Click through on "Go to the **secured pages**"
- Login using user1/password
- Click through on "**Access OAuth2 Resource**"
- Authorize the Client on the Approval page
- Client completes the `authorization_code` flow and then accesses the protected resource at `/oauth2/resource1`

## Fail Test Scenario

- Go to `application.yml` and change the following configuration:
    - **COMMENT this line** -> pre-established-redirect-uri: ${server.url}/callback
    - **UNCOMMENT this line** -> pre-established-redirect-uri: ${server.url}/callback/%252e%252e
- Startup Spring Boot application `DeteactApplication`
- Go to http://localhost:8080
- Click through on "Go to the **secured pages**"
- Login using user1/password
- Click through on "**Access OAuth2 Resource**"
- Authorize the Client on the Approval page
- `/callback` does not get called and you will see the `code` and `state` leaked in the browser, for example, `http://localhost:8080/callback/%252e%252e?code=5vX3XO&state=fIgTNJ`
- Clean up the Request URL so it looks like this: `http://localhost:8080/callback?code=5vX3XO&state=fIgTNJ`
- Launch another browser session and go to http://localhost:8080
- Click through on "Go to the **secured pages**"
- Login using user2/password
- Try accessing the url directly in the browser: `http://localhost:8080/callback?code=5vX3XO&state=fIgTNJ`   
- CSRF is detected and exception is thrown on Line 255 in `AuthorizationCodeAccessTokenProvider` 