package com.nisum.oppenheimer.util;

public class Constants {

    // RESTful API
    public static final String USER_PATH_REST_ENDPOINT = "/api/users";
    public static final String USER_V1_MEDIA_TYPE = "application/vnd.nisum.oppenheimer.user.v1+json";

    // General validation messages
    public static final String NAME_REQUIRED = "Name is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email should be valid";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_WEAK = "Password must contain an uppercase letter, a lowercase letter, a digit, and a special character";
    public static final String PHONE_NUMBER_REQUIRED = "Phone number is required";
    public static final String PHONE_NUMBER_INVALID = "Phone number must contain only digits";
    public static final String PHONE_NUMBER_INVALID_SIZE = "size must be between 0 and 15";
    public static final String CITY_CODE_REQUIRED = "City code is required";
    public static final String CITY_CODE_INVALID = "City code must contain only digits";
    public static final String COUNTRY_CODE_REQUIRED = "Country code is required";
    public static final String COUNTRY_CODE_INVALID = "Country code must contain only digits";

    // Regex patterns
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).*$";
    public static final String PHONE_NUMBER_REGEX = "\\d+";
    public static final String CITY_CODE_REGEX = "\\d+";
    public static final String COUNTRY_CODE_REGEX = "\\d+";

    // Size constraints
    public static final int NAME_MAX_SIZE = 55;
    public static final int EMAIL_MAX_SIZE = 55;
    public static final int PASSWORD_MIN_SIZE = 8;
    public static final int PHONE_NUMBER_MAX_SIZE = 15;
    public static final int CITY_CODE_MAX_SIZE = 15;
    public static final int COUNTRY_CODE_MAX_SIZE = 15;
}
