#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The ValidationErrorDTO class contains 
 * a list of FieldErrorDTO.
 * 
 * @author ${author}
 * @since 1.0
 * 
 */

public class ValidationErrorDTO {

    private final List<FieldErrorDTO> fieldErrors;

    public ValidationErrorDTO() {
	fieldErrors = new ArrayList<>();
    }

    public void addFieldError(FieldErrorDTO fieldErrorDto) {
	fieldErrors.add(fieldErrorDto);
    }

    public List<FieldErrorDTO> getFieldErrors() {
	return fieldErrors;
    }
}
