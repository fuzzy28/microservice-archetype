#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.util.Collection;

import javax.validation.Valid;

import ${package}.domain.${domainName};
import ${package}.exception.${domainName}IdNotConsistentException;
import ${package}.exception.${domainName}NotFoundException;
import ${package}.exception.${domainName}NotPersistedException;
import ${package}.service.${domainName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ${domainName}Controller class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>
 * which, by default, contains a ResponseEntity converted into JSON with an
 * associated HTTP status code.
 * 
 * @author ${author}
 * @since 1.0
 */
#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")
@RequestMapping("/${domainNameVariable}s")
@RestController
public class ${domainName}Controller {

    @Autowired
    private ${domainName}Service ${domainNameVariable}Service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<${domainName}>> getAll${domainName}s() {
	return new ResponseEntity<Collection<${domainName}>>(
		${domainNameVariable}Service.findAll(),
		HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<${domainName}> get${domainName}ById(
	    @PathVariable("id") Long ${domainNameVariable}Id) {

	${domainName} ${domainNameVariable} = ${domainNameVariable}Service.findOne(${domainNameVariable}Id);
	ResponseEntity<${domainName}> response = null;
	if (${domainNameVariable} == null) {
	    throw new ${domainName}NotFoundException();
	} else {
	    response = new ResponseEntity<${domainName}>(${domainNameVariable}, HttpStatus.OK);
	}
	return response;
    }

    @RequestMapping(
	    method = RequestMethod.POST,
	    consumes = MediaType.APPLICATION_JSON_VALUE,
	    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${domainName}> save${domainName}(
	    @Valid @RequestBody ${domainName} persist) {

	if (persist == null || persist.get$propertyIdentifier() != null) {
	    throw new ${domainName}NotPersistedException();
	}

	${domainName} ${domainNameVariable} = ${domainNameVariable}Service.save(persist);
	HttpHeaders headers = new HttpHeaders();
	headers.add("location", String.format("/${domainNameVariable}s/%d", ${domainNameVariable}.get$propertyIdentifier()));
	return new ResponseEntity<${domainName}>(${domainNameVariable}, headers, HttpStatus.CREATED);
    }

    @RequestMapping(
	    path = "/{id}",
	    method = RequestMethod.PUT,
	    consumes = MediaType.APPLICATION_JSON_VALUE,
	    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${domainName}> update${domainName}(
	    @Valid @RequestBody ${domainName} persist,
	    @PathVariable("id") Long ${domainNameVariable}Id) {

	if (persist.get$propertyIdentifier() != null && persist.get$propertyIdentifier() != ${domainNameVariable}Id) {
	    throw new ${domainName}IdNotConsistentException();
	}

	ResponseEntity<${domainName}> response = null;
	${domainName} ${domainNameVariable} = ${domainNameVariable}Service.update(persist);
	if (${domainNameVariable} == null) {
	    throw new ${domainName}NotFoundException();
	} else {
	    response = new ResponseEntity<${domainName}>(${domainNameVariable}, HttpStatus.OK);
	}

	return response;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<${domainName}> delete${domainName}ById(
	    @PathVariable("id") Long ${domainNameVariable}Id) {

	ResponseEntity<${domainName}> response = null;
	if (${domainNameVariable}Service.findOne(${domainNameVariable}Id) == null) {
	    throw new ${domainName}NotFoundException();
	} else {
	    ${domainNameVariable}Service.delete(${domainNameVariable}Id);
	    response = new ResponseEntity<${domainName}>(HttpStatus.NO_CONTENT);
	}

	return response;
    }
}