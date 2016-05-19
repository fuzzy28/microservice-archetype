#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.util.Collection;
import java.util.ArrayList;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ${package}.exception.${domainName}IdNotConsistentException;
import ${package}.exception.${domainName}NotFoundException;
import ${package}.exception.${domainName}NotPersistedException;
import ${package}.service.${domainName}Service;
import ${package}.domain.${domainName};
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
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;

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
@RequestMapping("/${domainName.toLowerCase()}s")
@RestController
@ExposesResourceFor(${domainName}.class)
@Api(value = "/${domainName.toLowerCase()}s")
public class ${domainName}Controller {

    @Autowired
    private ${domainName}Service ${domainName.toLowerCase()}Service;
    
    @Autowired
    private EntityLinks links;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "retrieves all ${domainName.toLowerCase()}s.")
    public ResponseEntity<Collection<Resource<${domainName}>>> getAll${domainName}s() {
	Collection<Resource<${domainName}>> resources = new ArrayList<>();
	${domainName.toLowerCase()}Service.findAll().forEach(e -> resources.add(addLinkToSingleResource(e, e.get$propertyIdentifier())));
	return new ResponseEntity<Collection<Resource<${domainName}>>>(resources, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "retrieves a single ${domainName.toLowerCase()} by id.")
    public ResponseEntity<Resource<${domainName}>> get${domainName}ById(
	    @PathVariable("id") Long ${domainName.toLowerCase()}Id) {

	${domainName} ${domainName.toLowerCase()} = ${domainName.toLowerCase()}Service.findOne(${domainName.toLowerCase()}Id);
	if (${domainName.toLowerCase()} == null) {
	    throw new ${domainName}NotFoundException();
	}
	return new ResponseEntity<Resource<${domainName}>>(addLinkToSingleResource(${domainName.toLowerCase()}, ${domainName.toLowerCase()}Id), HttpStatus.OK);
    }

    @RequestMapping(
	    method = RequestMethod.POST,
	    consumes = MediaType.APPLICATION_JSON_VALUE,
	    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "persists a new ${domainName.toLowerCase()} resource.")
    public ResponseEntity<Resource<${domainName}>> save${domainName}(
	    @Valid @RequestBody ${domainName} persist) {

	if (persist == null || persist.get$propertyIdentifier() != null) {
	    throw new ${domainName}NotPersistedException();
	}

	${domainName} ${domainName.toLowerCase()} = ${domainName.toLowerCase()}Service.save(persist);
	HttpHeaders headers = new HttpHeaders();
	headers.add("location", String.format("/${domainName.toLowerCase()}s/%d", ${domainName.toLowerCase()}.get$propertyIdentifier()));
	return new ResponseEntity<Resource<${domainName}>>(addLinkToSingleResource(${domainName.toLowerCase()}, ${domainName.toLowerCase()}.get$propertyIdentifier()), headers, HttpStatus.CREATED);
    }

    @RequestMapping(
	    path = "/{id}",
	    method = RequestMethod.PUT,
	    consumes = MediaType.APPLICATION_JSON_VALUE,
	    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updates an  already persisted ${domainName.toLowerCase()} resource.")
    public ResponseEntity<Resource<${domainName}>> update${domainName}(
	    @Valid @RequestBody ${domainName} persist,
	    @PathVariable("id") Long ${domainName.toLowerCase()}Id) {

	if (persist.get$propertyIdentifier() != null && persist.get$propertyIdentifier() != ${domainName.toLowerCase()}Id) {
	    throw new ${domainName}IdNotConsistentException();
	}

	${domainName} ${domainName.toLowerCase()} = ${domainName.toLowerCase()}Service.update(persist);
	if (${domainName.toLowerCase()} == null) {
	    throw new ${domainName}NotFoundException();
	}

	return new ResponseEntity<Resource<${domainName}>>(addLinkToSingleResource(${domainName.toLowerCase()}, ${domainName.toLowerCase()}Id), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removes an existing ${domainName.toLowerCase()} resource.")
    public ResponseEntity<${domainName}> delete${domainName}ById(
	    @PathVariable("id") Long ${domainName.toLowerCase()}Id) {

	ResponseEntity<${domainName}> response = null;
	if (${domainName.toLowerCase()}Service.findOne(${domainName.toLowerCase()}Id) == null) {
	    throw new ${domainName}NotFoundException();
	} else {
	    ${domainName.toLowerCase()}Service.delete(${domainName.toLowerCase()}Id);
	    response = new ResponseEntity<${domainName}>(HttpStatus.NO_CONTENT);
	}

	return response;
    }
    
    private <T> Resource<T> addLinkToSingleResource(T object, Object id) {
	Resource<T> resource = new Resource<T>(object);
	resource.add(links.linkToSingleResource(object.getClass(), id));		
	return resource;
    }
}