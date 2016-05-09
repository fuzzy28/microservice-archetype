#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import ${package}.domain.${domainName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ${domainName} Repository which acts as Data Access Object 
 * 
 * @author ${author}
 * @since 1.0
 */

@Repository
public interface ${domainName}Repository extends JpaRepository<${domainName}, Long> {

}
