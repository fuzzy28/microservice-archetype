#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

/**
 * The ${domainName}NotFoundException class is 
 * a custom class use if ${domainName} resource 
 * cannot be found in the database.
 * 
 * @author ${author}
 * @since 1.0
 * 
 */

public class ${domainName}NotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ${domainName}NotFoundException() {
    }

    public ${domainName}NotFoundException(String message) {
	super(message);
    }

}
