package com.hrpirme.groupchatapp.controller;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller class for handling custom error pages.
 */
@Controller
public class CustomErrorController {

    private final ErrorAttributes errorAttributes;

    /**
     * Constructs a new CustomErrorController with the provided ErrorAttributes.
     *
     * @param errorAttributes The error attributes to be used for error handling.
     */
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Handles requests for the custom 404 error page.
     *
     * @param webRequest The web request containing error information.
     * @return A redirection to the login page if the error status code is 404 (Not Found),
     *        or redirects to the default error page.
     */
    @RequestMapping("/custom404")
    public String handleCustom404(WebRequest webRequest) {
        // Get the HTTP status code of the error
        Integer statusCode = (Integer) webRequest.getAttribute("javax.servlet.error.status_code",
                WebRequest.SCOPE_REQUEST);

        // Redirect to the login page if the status code is 404 (Not Found)
        if (statusCode != null && statusCode == 404) {
            return "redirect:/login";
        }

        // Redirect to the default error page
        return "error";
    }
}