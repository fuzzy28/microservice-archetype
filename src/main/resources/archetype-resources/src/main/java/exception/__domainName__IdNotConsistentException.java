#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

/**
 * The ${domainName}IdNotConsistentException class is 
 * a custom class use if ID in URL path and request payload does
 * not match.
 * 
 * @author ${author}
 * @since 1.0
 * 
 */

public class ${domainName}IdNotConsistentException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ${domainName}IdNotConsistentException() {
    }

    public ${domainName}IdNotConsistentException(String message) {
	super(message);
    }

}
