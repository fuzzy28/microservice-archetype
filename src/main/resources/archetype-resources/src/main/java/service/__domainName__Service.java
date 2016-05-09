#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.Collection;

import ${package}.domain.${domainName};

/**
 * ${domainName} Service Interface
 * 
 * @author Joel F. Ruelos Jr.
 * @since 1.0
 */

public interface ${domainName}Service {

    Collection<${domainName}> findAll();

    long countAll();

    ${domainName} findOne(Long id);

    ${domainName} save(${domainName} persist);

    ${domainName} update(${domainName} update);

    void delete(Long id);
}