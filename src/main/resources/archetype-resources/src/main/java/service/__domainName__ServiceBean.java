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
 * @author Joel F. Ruelos Jr.
 * @since 1.0
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ${domainName}ServiceBean implements ${domainName}Service {

    @Autowired
    private ${domainName}Repository ${domainNameVariable}Repository;

    @Override
    public Collection<${domainName}> findAll() {
	return ${domainNameVariable}Repository.findAll();
    }

    @Override
    @Cacheable(value = "${domainNameVariable}s", key = "${symbol_pound}id")
    public ${domainName} findOne(Long id) {
	return ${domainNameVariable}Repository.findOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "${domainNameVariable}s", key = "${symbol_pound}result.id", condition = "${symbol_pound}result != null")
    public ${domainName} save(${domainName} persist) {

	${domainName} ${domainNameVariable} = null;
	if (persist != null && persist.getId() == null) {
	    ${domainNameVariable} = saveOrUpdate(persist);
	}

	return ${domainNameVariable};
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "${domainNameVariable}s", key = "${symbol_pound}update.id", condition = "${symbol_pound}result != null")
    public ${domainName} update(${domainName} update) {

	${domainName} ${domainNameVariable} = null;
	if (update != null && update.getId() != null
		&& ${domainNameVariable}Repository.findOne(update.getId()) != null) {
	    ${domainNameVariable} = saveOrUpdate(update);
	}

	return ${domainNameVariable};
    }

    private ${domainName} saveOrUpdate(${domainName} ${domainNameVariable}) {
	return ${domainNameVariable}Repository.save(${domainNameVariable});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "${domainNameVariable}s", key = "${symbol_pound}id")
    public void delete(Long id) {
	${domainNameVariable}Repository.delete(id);
    }

    @Override
    public long countAll() {
	return ${domainNameVariable}Repository.count();
    }
}