#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.transaction.Transactional;

import ${package}.domain.${domainName};
import ${package}.service.${domainName}Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

#set( $propertyIdentifier = "${propertyId.substring(0,1).toUpperCase()}${propertyId.substring(1)}")

@Transactional
public class ${domainName}ServiceTest extends AbstractIntegrationTest {

    @Autowired
    ${domainName}Service ${domainName.toLowerCase()}Service;

    @Test
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
    public void whenSuccessfullyPersistedThenShouldRetrieve() {
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
	${domainName.toLowerCase()}2.set$propertyIdentifier(null);
	assertNotNull("failure - ${domainName.toLowerCase()} was not persisted",
		${domainName.toLowerCase()}Service.save(${domainName.toLowerCase()}2));

	// assert find all
	int total = ${domainName.toLowerCase()}Service.findAll().size();
	assertEquals("failure - total size does not match", 2, total);

	// assert find single
	${domainName} actual = ${domainName.toLowerCase()}Service.findOne(${domainName.toLowerCase()}Persisted.get$propertyIdentifier());

	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	assertEquals("failure - $name does not match", ${domainName.toLowerCase()}.get$capitalizeProp(),
			actual.get$capitalizeProp());
	 #end
    }

    @Test
    public void whenUpdateWithValidIdThenShouldSucces() {
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
	
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	
	    	#if (!$name.toLowerCase().equals($propertyId.toLowerCase()))
		    	#if ($type.toLowerCase().equals("string"))
		    	    ${domainName.toLowerCase()}Persisted.set$capitalizeProp("Test Updated");
		    	#elseif ($type.toLowerCase().equals("boolean"))
		    	    ${domainName.toLowerCase()}Persisted.set$capitalizeProp(false);
			#elseif ($type.toLowerCase().equals("long"))
		    	    ${domainName.toLowerCase()}Persisted.set$capitalizeProp(1L);
			#elseif ($type.toLowerCase().equals("int"))
			    ${domainName.toLowerCase()}Persisted.set$capitalizeProp(1);
		    	#end
	    	#end
	    	

	 #end
	 
	${domainName} modified;
	assertNotNull("failure - ${domainName.toLowerCase()} was not updated",
		modified = ${domainName.toLowerCase()}Service.update(${domainName.toLowerCase()}Persisted));
	
	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	assertEquals("failure - $name does not match", ${domainName.toLowerCase()}Persisted.get$capitalizeProp(),
	    		modified.get$capitalizeProp());
	 #end
    }

    @Test
    public void whenUpdatingWithInvalidIdShouldFail() {
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

	assertNull("failure - entity without ID should not be updated",
		${domainName.toLowerCase()}Service.update(${domainName.toLowerCase()}));

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
	${domainName.toLowerCase()}Service.delete(${domainName.toLowerCase()}Persisted.get$propertyIdentifier());
	assertNull("failure - resource has not been deleted",
		${domainName.toLowerCase()}Service.findOne(${domainName.toLowerCase()}Persisted.get$propertyIdentifier()));
    }
}