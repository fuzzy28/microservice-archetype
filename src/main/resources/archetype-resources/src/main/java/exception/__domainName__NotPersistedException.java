#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

/**
 * The ${domainName}NotPersistedException class is 
 * a custom class use if ${domainName}  
 * was not persisted or saved in the database.
 * 
 * @author ${author}
 * @since 1.0
 * 
 */

public class ${domainName}NotPersistedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ${domainName}NotPersistedException() {
    }

    public ${domainName}NotPersistedException(String message) {
	super(message);
    }

}
