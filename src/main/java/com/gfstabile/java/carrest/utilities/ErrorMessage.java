package com.gfstabile.java.carrest.utilities;

/**
 * Class that contains all errors message
 * used as response.
 *
 * @author G. F. Stabile
 */
public class ErrorMessage {
    public static final String BODY_MANDATORY = "The body is mandatory or the body structure is not tolerated";
    public static final String CAR_ALREADY_EXISTS = "A car with this internal code already exists";
    public static final String CAR_COMPANY_INTERNAL_CODE_BLANK = "The company internal code is mandatory";
    public static final String CAR_INTERNAL_CODE_BLANK = "The internal code is mandatory";
    public static final String CAR_NAME_BLANK = "The name is mandatory";
    public static final String CAR_NOT_FOUND = "The car's internal code do not exists";
    public static final String COMPANY_INTERNAL_CODE_DO_NOT_EXISTS = "The company internal code do not exists";
    public static final String DELETE_NOT_SUPPORTED = "Request method 'DELETE' not supported";

    private ErrorMessage() {

    }
}
