package com.nisum.oppenheimer.api.restful.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.oppenheimer.util.Constants;
import com.nisum.oppenheimer.validation.ValidPassword;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * UserDTO represents the data transfer object for a User entity in the system.
 * This class is used to receive and send user-related data in JSON format
 * over HTTP in a RESTful API.
 *
 * <p>
 * The fields in this DTO are validated using Bean Validation (JSR 380) annotations,
 * and Jackson annotations are used to control the JSON serialization and deserialization.
 * </p>
 *
 * <p>
 * The validation constraints ensure that:
 * <ul>
 *     <li>The 'name' field is required, cannot be blank, and cannot exceed a maximum length.</li>
 *     <li>The 'email' field is required, must be a valid email format, and cannot exceed a maximum length.</li>
 *     <li>The 'password' field is required, must meet a minimum length, and must contain an uppercase letter,
 *     a lowercase letter, a digit, and a special character.</li>
 *     <li>The 'phones' field is optional, but if present, it must be a list of valid PhoneDTO objects.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class uses constants defined in {@code Constants} to enforce the validation rules,
 * ensuring that validation messages, size limits, and patterns are centralized and easy to manage.
 * </p>
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    /**
     * The name of the user.
     * <p>
     * It must not be blank and must not exceed {@code Constants.NAME_MAX_SIZE} characters.
     * </p>
     */
    @JsonProperty("name")
    @Size(max = Constants.NAME_MAX_SIZE)
    @NotBlank(message = Constants.NAME_REQUIRED)
    private String name;

    /**
     * The email address of the user.
     * <p>
     * It must be a valid email format, must not be blank, and must not exceed {@code Constants.EMAIL_MAX_SIZE} characters.
     * The regex pattern used for validation is defined in {@code Constants.EMAIL_REGEX}.
     * </p>
     */
    @JsonProperty("email")
    @Size(max = Constants.EMAIL_MAX_SIZE)
    @Email(message = Constants.EMAIL_INVALID)
    @NotBlank(message = Constants.EMAIL_REQUIRED)
    @Pattern(regexp = Constants.EMAIL_REGEX, message = Constants.EMAIL_INVALID)
    private String email;

    /**
     * The password for the user account.
     * <p>
     * It must not be blank, must be at least {@code Constants.PASSWORD_MIN_SIZE} characters long,
     * and must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.
     * The regex pattern used for password validation is defined in {@code Constants.PASSWORD_REGEX}.
     * </p>
     */
    @JsonProperty("password")
    @NotBlank(message = Constants.PASSWORD_REQUIRED)
    @ValidPassword(message = Constants.PASSWORD_WEAK)
    @Size(min = Constants.PASSWORD_MIN_SIZE, message = "Password must be at least " + Constants.PASSWORD_MIN_SIZE + " characters")
    private String password;

    /**
     * The list of phone numbers associated with the user.
     * <p>
     * This field is optional, but if provided, each phone entry must be a valid {@code PhoneDTO}.
     * </p>
     */
    @JsonProperty("phones")
    private List<@Valid PhoneDTO> phones;
}
