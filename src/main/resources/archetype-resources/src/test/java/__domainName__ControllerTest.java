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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;

#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")

@Transactional
public class ${domainName}ControllerTest extends AbstractControllerTest {

    private final String BASE_URI = "/${domainNameVariable}s";

    @Mock
    protected ${domainName}Service ${domainNameVariable}Service;

    @InjectMocks
    protected ${domainName}Controller ${domainNameVariable}Controller;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);

	setup(${domainNameVariable}Controller);
    }

    protected Collection<${domainName}> getAll${domainName}s() {
	Collection<${domainName}> ${domainNameVariable}s = new ArrayList<>();
	${domainName} ${domainNameVariable} = new ${domainName}();
	
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainNameVariable}.set$capitalizeProp("Test");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainNameVariable}.set$capitalizeProp(true);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainNameVariable}.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainNameVariable}.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainNameVariable}.set$propertyIdentifier(1L);
	 ${domainNameVariable}s.add(${domainNameVariable});

	${domainName} ${domainNameVariable}2 = new ${domainName}();
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainNameVariable}2.set$capitalizeProp("Test");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainNameVariable}2.set$capitalizeProp(true);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainNameVariable}2.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainNameVariable}2.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainNameVariable}2.set$propertyIdentifier(2L);

	
	 ${domainNameVariable}s.add(${domainNameVariable}2);
	return ${domainNameVariable}s;
    }

    protected ${domainName} getSingle${domainName}(Long id) {
	Optional<${domainName}> ${domainNameVariable} = getAll${domainName}s()
		.stream()
		.filter(d -> d.get$propertyIdentifier() == id)
		.findFirst();
	return ${domainNameVariable}.isPresent() ? ${domainNameVariable}.get() : null;
    }

    @Test
    public void whenFetchingAllThenShouldListAvailable${domainName}s() throws Exception {
	when(${domainNameVariable}Service.findAll()).thenReturn(getAll${domainName}s());

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.get(BASE_URI)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();

	assertSuccessStatusAndHasContent(response.getResponse());

	verify(${domainNameVariable}Service, times(1)).findAll();
    }

    @Test
    public void whenFindingExistingRecordThenShouldFetchSuccessfully() throws Exception {
	Long id = 1L;
	when(${domainNameVariable}Service.findOne(any(Long.class)))
		.thenReturn(getSingle${domainName}(id));

	MvcResult response = mockMvc
		.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + id))
		.andReturn();

	assertSuccessStatusAndHasContent(response.getResponse());

	verify(${domainNameVariable}Service, times(1)).findOne(id);
    }

    @Test
    public void whenFindingNonExistingRecordThenShouldReturnNotFound() throws Exception {

	String expectedErrMessage = "${domainNameVariable} nod found exception";
	Long id = Long.MAX_VALUE;
	when(${domainNameVariable}Service.findOne(any(Long.class)))
		.thenThrow(new ${domainName}NotFoundException(expectedErrMessage));

	mockMvc
		.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + id))
		.andExpect(
			MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
		.andReturn();

	verify(${domainNameVariable}Service, times(1)).findOne(id);
    }

    @Test
    public void whenSavingWithoutIdThenEntityShouldBeSaved() throws Exception {
	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);
	${domainNameVariable}.set$propertyIdentifier(null);
	when(${domainNameVariable}Service.save(any(${domainName}.class))).thenReturn(${domainNameVariable});

	MvcResult response = postRequest(super.objectToJson(${domainNameVariable}));

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

	verify(${domainNameVariable}Service, times(1)).save(any(${domainName}.class));

	assertContentEquals(${domainNameVariable}, persistedHrd);

    }

    @Test
    public void whenSavingNotSuccessfullThenShouldReturnInternalServerError()
	    throws Exception {
	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);

	when(${domainNameVariable}Service.save(any(${domainName}.class))).thenReturn(null);

	MvcResult response = postRequest(super.objectToJson(${domainNameVariable}));

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

	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);

	when(${domainNameVariable}Service.update(any(${domainName}.class))).thenReturn(null);

	MvcResult response = putRequest(super.objectToJson(${domainNameVariable}), 1L);

	assertNotFoundStatusAndHasNoContent(response.getResponse());
    }

    @Test
    public void whenUpdatingWithInconsistentIdThenShouldReturnBadRequest()
	    throws Exception {
	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);

	when(${domainNameVariable}Service.update(any(${domainName}.class))).thenReturn(${domainNameVariable});

	MvcResult response = putRequest(super.objectToJson(${domainNameVariable}), Long.MAX_VALUE);

	assertEquals(
		"failure - has fetched non existing ${domainNameVariable}",
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
	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);

	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if ($type.toLowerCase().equals("string"))
	    	    ${domainNameVariable}.set$capitalizeProp("Test Updated");
	    	#elseif ($type.toLowerCase().equals("boolean"))
	    	    ${domainNameVariable}.set$capitalizeProp(false);
		#elseif ($type.toLowerCase().equals("long"))
	    	    ${domainNameVariable}.set$capitalizeProp(1L);
		#elseif ($type.toLowerCase().equals("int"))
		    ${domainNameVariable}.set$capitalizeProp(1);
	    	#end
	 #end
	 ${domainNameVariable}.set$propertyIdentifier(1L);

	when(${domainNameVariable}Service.update(any(${domainName}.class))).thenReturn(${domainNameVariable});

	MvcResult response = putRequest(
		super.objectToJson(${domainNameVariable}),
		${domainNameVariable}.get$propertyIdentifier());

	verify(${domainNameVariable}Service, times(1)).update(any(${domainName}.class));

	${domainName} updatedEntity = super.jsonToObject(
		response.getResponse().getContentAsString(),
		${domainName}.class);

	assertContentEquals(${domainNameVariable}, updatedEntity);
    }

    @Test
    public void whenDeletingNonExistingResourceThenShouldReturnNotFound()
	    throws Exception {

	when(${domainNameVariable}Service.findOne(any(Long.class))).thenReturn(null);

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.delete(BASE_URI + "/" + any(Long.class).longValue()))
		.andReturn();

	assertNotFoundStatusAndHasNoContent(response.getResponse());

    }

    @Test
    public void whenDeletingExistingResourceThenShouldReturnOk() throws Exception {
	${domainName} ${domainNameVariable} = getSingle${domainName}(1L);

	when(${domainNameVariable}Service.findOne(any(Long.class))).thenReturn(${domainNameVariable});

	MvcResult response = mockMvc
		.perform(
			MockMvcRequestBuilders
				.delete(BASE_URI + "/" + ${domainNameVariable}.get$propertyIdentifier()))
		.andReturn();

	assertEquals(
		"failure - status not NO_CONTENT",
		HttpStatus.NO_CONTENT.value(),
		response.getResponse().getStatus());

	assertTrue(
		"failure - response has content",
		response.getResponse().getContentAsString().trim().length() == 0);

	verify(${domainNameVariable}Service, times(1)).findOne(any(Long.class));

	verify(${domainNameVariable}Service, times(1)).delete(any(Long.class));

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
		"failure - has fetched non existing ${domainNameVariable}",
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
