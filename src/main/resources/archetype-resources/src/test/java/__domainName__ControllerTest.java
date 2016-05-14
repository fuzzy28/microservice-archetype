#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import ${package}.controller.${domainName}Controller;
import ${package}.domain.${domainName};
import ${package}.exception.${domainName}NotFoundException;
import ${package}.service.${domainName}Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.hateoas.EntityLinks;
import com.fasterxml.jackson.core.JsonProcessingException;

#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")

@Transactional
public class ${domainName}ControllerTest extends AbstractControllerTest {

    private final String BASE_URI = "/${domainName.toLowerCase()}s";

    @Mock
    protected ${domainName}Service ${domainName.toLowerCase()}Service;

    @Mock
    protected EntityLinks links;
    
    @InjectMocks
    protected ${domainName}Controller ${domainName.toLowerCase()}Controller;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	when(links.linkToSingleResource(any(), any())).thenReturn(new Link(BASE_URI));
	setup(${domainName.toLowerCase()}Controller);
    }

    protected Collection<${domainName}> getAll${domainName}s() {
	Collection<${domainName}> ${domainName.toLowerCase()}s = new ArrayList<>();
	${domainName} ${domainName.toLowerCase()} = new ${domainName}();
	
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp("Test");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp(true);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainName.toLowerCase()}.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainName.toLowerCase()}.set$propertyIdentifier(1L);
	 ${domainName.toLowerCase()}s.add(${domainName.toLowerCase()});

	${domainName} ${domainName.toLowerCase()}2 = new ${domainName}();
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainName.toLowerCase()}2.set$capitalizeProp("Test");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainName.toLowerCase()}2.set$capitalizeProp(true);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainName.toLowerCase()}2.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainName.toLowerCase()}2.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainName.toLowerCase()}2.set$propertyIdentifier(2L);

	
	 ${domainName.toLowerCase()}s.add(${domainName.toLowerCase()}2);
	return ${domainName.toLowerCase()}s;
    }

    protected ${domainName} getSingle${domainName}(Long id) {
	Optional<${domainName}> ${domainName.toLowerCase()} = getAll${domainName}s()
		.stream()
		.filter(d -> d.get$propertyIdentifier() == id)
		.findFirst();
	return ${domainName.toLowerCase()}.isPresent() ? ${domainName.toLowerCase()}.get() : null;
    }

    @Test
    public void whenFetchingAllThenShouldListAvailable${domainName}s() throws Exception {
	when(${domainName.toLowerCase()}Service.findAll()).thenReturn(getAll${domainName}s());

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.get(BASE_URI)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();

	assertSuccessStatusAndHasContent(response.getResponse());

	verify(${domainName.toLowerCase()}Service, times(1)).findAll();
    }

    @Test
    public void whenFindingExistingRecordThenShouldFetchSuccessfully() throws Exception {
	Long id = 1L;
	when(${domainName.toLowerCase()}Service.findOne(any(Long.class)))
		.thenReturn(getSingle${domainName}(id));

	MvcResult response = mockMvc
		.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + id))
		.andReturn();

	assertSuccessStatusAndHasContent(response.getResponse());

	verify(${domainName.toLowerCase()}Service, times(1)).findOne(id);
    }

    @Test
    public void whenFindingNonExistingRecordThenShouldReturnNotFound() throws Exception {

	String expectedErrMessage = "${domainName.toLowerCase()} nod found exception";
	Long id = Long.MAX_VALUE;
	when(${domainName.toLowerCase()}Service.findOne(any(Long.class)))
		.thenThrow(new ${domainName}NotFoundException(expectedErrMessage));

	mockMvc
		.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + id))
		.andExpect(
			MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
		.andReturn();

	verify(${domainName.toLowerCase()}Service, times(1)).findOne(id);
    }

    @Test
    public void whenSavingWithoutIdThenEntityShouldBeSaved() throws Exception {
	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);
	${domainName.toLowerCase()}.set$propertyIdentifier(null);
	when(${domainName.toLowerCase()}Service.save(any(${domainName}.class))).thenReturn(${domainName.toLowerCase()});

	MvcResult response = postRequest(super.objectToJson(${domainName.toLowerCase()}));

	assertEquals(
		"failure - status not 201",
		HttpStatus.CREATED.value(),
		response.getResponse().getStatus());

	assertTrue(
		"failure - content empty",
		response.getResponse().getContentAsString().trim().length() > 0);

	${domainName} persistedHrd = super.jsonToObject(
		response.getResponse().getContentAsString(),
		${domainName}.class);

	assertNotNull("failure - persisted hrd returns null", persistedHrd);

	verify(${domainName.toLowerCase()}Service, times(1)).save(any(${domainName}.class));

	assertContentEquals(${domainName.toLowerCase()}, persistedHrd);

    }

    @Test
    public void whenSavingNotSuccessfullThenShouldReturnInternalServerError()
	    throws Exception {
	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);

	when(${domainName.toLowerCase()}Service.save(any(${domainName}.class))).thenReturn(null);

	MvcResult response = postRequest(super.objectToJson(${domainName.toLowerCase()}));

	assertEquals(
		"failure - status not 400",
		HttpStatus.BAD_REQUEST.value(),
		response.getResponse().getStatus());

	assertTrue(
		"failure - content not empty",
		response.getResponse().getContentAsString().trim().length() == 0);

    }

    private MvcResult postRequest(String content) throws Exception {
	return mockMvc
		.perform(
			MockMvcRequestBuilders
				.post(BASE_URI)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
    }

    @Test
    public void whenUpdatingNonExistingResourceThenShouldReturnNotFound()
	    throws Exception {

	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);

	when(${domainName.toLowerCase()}Service.update(any(${domainName}.class))).thenReturn(null);

	MvcResult response = putRequest(super.objectToJson(${domainName.toLowerCase()}), 1L);

	assertNotFoundStatusAndHasNoContent(response.getResponse());
    }

    @Test
    public void whenUpdatingWithInconsistentIdThenShouldReturnBadRequest()
	    throws Exception {
	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);

	when(${domainName.toLowerCase()}Service.update(any(${domainName}.class))).thenReturn(${domainName.toLowerCase()});

	MvcResult response = putRequest(super.objectToJson(${domainName.toLowerCase()}), Long.MAX_VALUE);

	assertEquals(
		"failure - has fetched non existing ${domainName.toLowerCase()}",
		HttpStatus.BAD_REQUEST.value(),
		response.getResponse().getStatus());
	assertTrue(
		"failure - 404 page content is not empty",
		response.getResponse().getContentAsString().trim().length() == 0);
	logger.info("content is > " + response.getResponse().getContentAsString());
    }

    @Test
    public void whenUpdatingExistingResourceThenShouldReturnUpdatedEntity()
	    throws JsonProcessingException, Exception {
	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);

	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp("Test Updated");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp(false);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainName.toLowerCase()}.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainName.toLowerCase()}.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainName.toLowerCase()}.set$propertyIdentifier(1L);

	when(${domainName.toLowerCase()}Service.update(any(${domainName}.class))).thenReturn(${domainName.toLowerCase()});

	MvcResult response = putRequest(
		super.objectToJson(${domainName.toLowerCase()}),
		${domainName.toLowerCase()}.get$propertyIdentifier());

	verify(${domainName.toLowerCase()}Service, times(1)).update(any(${domainName}.class));

	${domainName} updatedEntity = super.jsonToObject(
		response.getResponse().getContentAsString(),
		${domainName}.class);

	assertContentEquals(${domainName.toLowerCase()}, updatedEntity);
    }

    @Test
    public void whenDeletingNonExistingResourceThenShouldReturnNotFound()
	    throws Exception {

	when(${domainName.toLowerCase()}Service.findOne(any(Long.class))).thenReturn(null);

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.delete(BASE_URI + "/" + any(Long.class).longValue()))
		.andReturn();

	assertNotFoundStatusAndHasNoContent(response.getResponse());

    }

    @Test
    public void whenDeletingExistingResourceThenShouldReturnOk() throws Exception {
	${domainName} ${domainName.toLowerCase()} = getSingle${domainName}(1L);

	when(${domainName.toLowerCase()}Service.findOne(any(Long.class))).thenReturn(${domainName.toLowerCase()});

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.delete(BASE_URI + "/" + ${domainName.toLowerCase()}.get$propertyIdentifier()))
		.andReturn();

	assertEquals(
		"failure - status not NO_CONTENT",
		HttpStatus.NO_CONTENT.value(),
		response.getResponse().getStatus());

	assertTrue(
		"failure - response has content",
		response.getResponse().getContentAsString().trim().length() == 0);

	verify(${domainName.toLowerCase()}Service, times(1)).findOne(any(Long.class));

	verify(${domainName.toLowerCase()}Service, times(1)).delete(any(Long.class));

    }

    private MvcResult putRequest(String content, Long id) throws Exception {
	return mockMvc
		.perform(
			MockMvcRequestBuilders
				.put(BASE_URI + "/" + id)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
		.andReturn();
    }

    private void assertSuccessStatusAndHasContent(MockHttpServletResponse response)
	    throws Exception {
	assertEquals(
		"failure - status not 200",
		HttpStatus.OK.value(),
		response.getStatus());
	assertTrue(
		"failure - content empty",
		response.getContentAsString().trim().length() > 0);
	logger.info("content is > " + response.getContentAsString());
    }

    private void assertNotFoundStatusAndHasNoContent(MockHttpServletResponse response)
	    throws Exception {
	assertEquals(
		"failure - has fetched non existing ${domainName.toLowerCase()}",
		HttpStatus.NOT_FOUND.value(),
		response.getStatus());
	assertTrue(
		"failure - 404 page content is not empty",
		response.getContentAsString().trim().length() == 0);
	logger.info("content is > " + response.getContentAsString());
    }

    private void assertContentEquals(${domainName} expected, ${domainName} actual) {
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	assertEquals("failure - $name does not match", expected.get$capitalizeProp(),
			actual.get$capitalizeProp());
	 #end
    }
}
