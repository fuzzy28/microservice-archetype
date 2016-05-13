#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.Collection;

import ${package}.domain.${domainName};
import ${package}.repository.${domainName}Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ${domainName} Service
 * 
 * @author ${author}
 * @since 1.0
 */

#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ${domainName}ServiceBean implements ${domainName}Service {

    @Autowired
    private ${domainName}Repository ${domainName.toLowerCase()}Repository;

    @Override
    public Collection<${domainName}> findAll() {
	return ${domainName.toLowerCase()}Repository.findAll();
    }

    @Override
    @Cacheable(value = "${domainName.toLowerCase()}s", key = "${symbol_pound}${propertyId}")
    public ${domainName} findOne(Long ${propertyId}) {
	return ${domainName.toLowerCase()}Repository.findOne(${propertyId});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "${domainName.toLowerCase()}s", key = "${symbol_pound}result.${propertyId}", condition = "${symbol_pound}result != null")
    public ${domainName} save(${domainName} persist) {

	${domainName} ${domainName.toLowerCase()} = null;
	if (persist != null && persist.get$propertyIdentifier() == null) {
	    ${domainName.toLowerCase()} = saveOrUpdate(persist);
	}

	return ${domainName.toLowerCase()};
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "${domainName.toLowerCase()}s", key = "${symbol_pound}update.${propertyId}", condition = "${symbol_pound}result != null")
    public ${domainName} update(${domainName} update) {

	${domainName} ${domainName.toLowerCase()} = null;
	if (update != null && update.get$propertyIdentifier() != null
		&& ${domainName.toLowerCase()}Repository.findOne(update.get$propertyIdentifier()) != null) {
	    ${domainName.toLowerCase()} = saveOrUpdate(update);
	}

	return ${domainName.toLowerCase()};
    }

    private ${domainName} saveOrUpdate(${domainName} ${domainName.toLowerCase()}) {
	return ${domainName.toLowerCase()}Repository.save(${domainName.toLowerCase()});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "${domainName.toLowerCase()}s", key = "${symbol_pound}${propertyId}")
    public void delete(Long ${propertyId}) {
	${domainName.toLowerCase()}Repository.delete(${propertyId});
    }

    @Override
    public long countAll() {
	return ${domainName.toLowerCase()}Repository.count();
    }
}