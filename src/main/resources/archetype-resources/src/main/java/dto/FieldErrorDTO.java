#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dto;

/**
 * The FieldErrorDTO class is a a mutable class 
 * which provides information to validated entity.
 * 
 * @author ${author}
 * @since 1.0
 */

public class FieldErrorDTO {

    private final String field;
    private final String message;

    public FieldErrorDTO(String field, String message) {
	this.field = field;
	this.message = message;
    }

    public String getField() {
	return field;
    }

    public String getMessage() {
	return message;
    }
}
