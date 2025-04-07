package edu.tcu.cs.hogwartsartifactsonline2.security;


import edu.tcu.cs.hogwartsartifactsonline2.system.Result;
import edu.tcu.cs.hogwartsartifactsonline2.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("{api.endpoint.base-url}/users")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication, SessionStatus sessionStatus) {
        LOGGER.debug("Authenticated user: '{}'", authentication.getName());
        return new Result(true, StatusCode.SUCCESS, "User info and JSON web token", this.authService.createLoginInfo(authentication));

    }
}
