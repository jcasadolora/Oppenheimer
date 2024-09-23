package com.nisum.oppenheimer.api.restful.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.oppenheimer.util.Constants;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * PhoneDTO Data Transfer Object for representing a phone number.
 * PhoneDTO represent the data transfer object for a User's Phone entity in the system.
 * This class is used to receive and send user-related data in JSON format over HTTP in a RESTful API.
 *
 * <p>
 * The fields in this DTO are validated using Bean Validation (JSR 380) annotations,
 * and Jackson annotations are used to control the JSON serialization and deserialization.
 * </p>
 *
 * <p>
 * The validation constraints ensure that:
 * <ul>
 *     <li>{@link NotNull} and {@link NotBlank} for mandatory fields.</li>
 *     <li>{@link Pattern} for ensuring valid formats based on defined regex patterns in {@link Constants}.</li>
 *     <li>{@link Size} for limiting the length of the fields.</li>
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
public class PhoneDTO {

    @JsonProperty("number")
    @Size(max = Constants.PHONE_NUMBER_MAX_SIZE)
    @NotNull(message = Constants.PHONE_NUMBER_REQUIRED)
    @NotBlank(message = Constants.PHONE_NUMBER_REQUIRED)
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX, message = Constants.PHONE_NUMBER_INVALID)
    private String number;

    @JsonProperty("citycode")
    @Size(max = Constants.CITY_CODE_MAX_SIZE)
    @NotNull(message = Constants.CITY_CODE_REQUIRED)
    @NotBlank(message = Constants.CITY_CODE_REQUIRED)
    @Pattern(regexp = Constants.CITY_CODE_REGEX, message = Constants.CITY_CODE_INVALID)
    private String cityCode;

    @JsonProperty("countrycode")
    @Size(max = Constants.COUNTRY_CODE_MAX_SIZE)
    @NotNull(message = Constants.COUNTRY_CODE_REQUIRED)
    @NotBlank(message = Constants.COUNTRY_CODE_REQUIRED)
    @Pattern(regexp = Constants.COUNTRY_CODE_REGEX, message = Constants.COUNTRY_CODE_INVALID)
    private String countryCode;
}
