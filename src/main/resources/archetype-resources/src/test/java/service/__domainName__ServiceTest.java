#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import ${package}.AbstractIntegrationTest;
import ${package}.domain.${domainName};
import ${package}.service.${domainName}Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import static org.junit.Assert.*;

#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")

public class ${domainName}ServiceTest extends AbstractIntegrationTest {

    @Autowired
    ${domainName}Service ${domainName.toLowerCase()}Service;

    @Test
    @DatabaseSetup("initializeEmpty.xml")
    @ExpectedDatabase(
	    assertionMode = DatabaseAssertionMode.NON_STRICT,
	    value = "persistingWithoutId_expected.xml")
    public void whenPersistingWithoutIdThenShouldPersist() {
	// assert saving
	${domainName} ${domainName.toLowerCase()} = new ${domainName}();
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if (!$name.toLowerCase().equals($propertyId.toLowerCase()))
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
	 #end
	${domainName} ${domainName.toLowerCase()}Persisted = ${domainName.toLowerCase()}Service.save(${domainName.toLowerCase()});
	assertNotNull("failure - ${domainName.toLowerCase()} was not persisted", ${domainName.toLowerCase()}Persisted);
    }

    @Test
    public void whenPesistingWithIdThenShouldNotPersist() {
	// assert saving with Id
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

	${domainName} ${domainName.toLowerCase()}Persisted = ${domainName.toLowerCase()}Service.save(${domainName.toLowerCase()});
	assertNull("failure - saving ${domainName.toLowerCase()} with ID was persisted", ${domainName.toLowerCase()}Persisted);
    }

    @Test
    @DatabaseSetup(value = "initializeSingleRecord.xml")
    @ExpectedDatabase(
	    assertionMode = DatabaseAssertionMode.NON_STRICT,
	    value = "updateWithValidId_expected.xml")
    public void whenUpdateWithValidIdThenShouldSucceed() {
	// assert saving
	${domainName} ${domainName.toLowerCase()} = new ${domainName}();
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
	assertNotNull("failure - ${domainName.toLowerCase()} was not updated",
		${domainName.toLowerCase()}Service.update(${domainName.toLowerCase()}));
    }

    @Test
    @DatabaseSetup(value = "initializeSingleRecord.xml")
    public void whenUpdatingWithInvalidIdThenShouldFail() {
	// assert saving
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

	    	${domainName.toLowerCase()}.set$propertyIdentifier(Long.MAX_VALUE);

	assertNull("failure - entity with no existing ID should not be updated",
		${domainName.toLowerCase()}Service.update(${domainName.toLowerCase()}));
    }

    @Test
    public void whenDeletingThenShouldNoLongerExists() {
	// assert saving
	${domainName} ${domainName.toLowerCase()} = new ${domainName}();
	
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	#if (!$name.toLowerCase().equals($propertyId.toLowerCase()))
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
	 #end

	${domainName} ${domainName.toLowerCase()}Persisted = ${domainName.toLowerCase()}Service.save(${domainName.toLowerCase()});
	assertNotNull("failure - ${domainName.toLowerCase()} was not persisted", ${domainName.toLowerCase()}Persisted);
	${domainName.toLowerCase()}Service.delete(${domainName.toLowerCase()}Persisted.get$propertyIdentifier());
	assertNull("failure - resource has not been deleted",
		${domainName.toLowerCase()}Service.findOne(${domainName.toLowerCase()}Persisted.get$propertyIdentifier()));
    }
}