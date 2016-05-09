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
public class ${domainName}ServiceTest extends AbstractTest {

    @Autowired
    ${domainName}Service ${domainNameVariable}Service;

    @Test
    public void whenPersistingWithoutIdThenShouldPersist() {
	// assert saving
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
	${domainNameVariable}.set$propertyIdentifier(null);    
	${domainName} ${domainNameVariable}Persisted = ${domainNameVariable}Service.save(${domainNameVariable});
	assertNotNull("failure - ${domainNameVariable} was not persisted", ${domainNameVariable}Persisted);
    }

    @Test
    public void whenPesistingWithIdThenShouldNotPersist() {
	// assert saving with Id
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

	${domainName} ${domainNameVariable}Persisted = ${domainNameVariable}Service.save(${domainNameVariable});
	assertNull("failure - saving ${domainNameVariable} with ID was persisted", ${domainNameVariable}Persisted);
    }

    @Test
    public void whenSuccessfullyPersistedThenShouldRetrieve() {
	// assert saving
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

	${domainName} ${domainNameVariable}Persisted = ${domainNameVariable}Service.save(${domainNameVariable});
	assertNotNull("failure - ${domainNameVariable} was not persisted", ${domainNameVariable}Persisted);

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
	assertNotNull("failure - ${domainNameVariable} was not persisted",
		${domainNameVariable}Service.save(${domainNameVariable}2));

	// assert find all
	int total = ${domainNameVariable}Service.findAll().size();
	assertEquals("failure - total size does not match", 2, total);

	// assert find single
	${domainName} actual = ${domainNameVariable}Service.findOne(${domainNameVariable}Persisted.get$propertyIdentifier());

	 #foreach($prop in $propertyList.split(","))
		#set( $index = ${prop.indexOf("=")} )
	    	#set( $name = ${prop.substring(0, $index)} )
	    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
		#set( $index = $index + 1 )
	    	#set( $type = ${prop.substring($index)} )
	    	assertEquals("failure - $name does not match", ${domainNameVariable}.get$capitalizeProp(),
			actual.get$capitalizeProp());
	 #end
    }

    @Test
    public void whenUpdateWithValidIdThenShouldSucces() {
	// assert saving
	${domainName} sdd = new ${domainName}();
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	${domainName} sddPersisted = ${domainNameVariable}Service.save(sdd);
	assertNotNull("failure - ${domainNameVariable} was not persisted", sddPersisted);

	sddPersisted.set${domainName}Code("SDD UPDATED");
	sddPersisted.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT TEST");
	sddPersisted.setActive(false);

	${domainName} modified;
	assertNotNull("failure - ${domainNameVariable} was not updated",
		modified = ${domainNameVariable}Service.update(sddPersisted));
	assertEquals("failure - ${domainNameVariable} code not match",
		sddPersisted.get${domainName}Code(), modified.get${domainName}Code());
	assertEquals("failure - ${domainNameVariable} name not match",
		sddPersisted.get${domainName}Name(), modified.get${domainName}Name());
	assertEquals("failure - ${domainNameVariable} active not match", sddPersisted.isActive(),
		modified.isActive());
    }

    @Test
    public void whenUpdatingWithInvalidIdShouldFail() {
	// assert saving
	${domainName} sdd = new ${domainName}();
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	assertNull("failure - entity without ID should not be updated",
		${domainNameVariable}Service.update(sdd));

	sdd.set$propertyIdentifier(Long.MAX_VALUE);

	assertNull("failure - entity with no existing ID should not be updated",
		${domainNameVariable}Service.update(sdd));
    }

    @Test
    public void whenDeletingThenShouldNoLongerExists() {
	// assert saving
	${domainName} sdd = new ${domainName}();
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	${domainName} sddPersisted = ${domainNameVariable}Service.save(sdd);
	${domainNameVariable}Service.delete(sddPersisted.get$propertyIdentifier());
	assertNull("failure - resource has not been deleted",
		${domainNameVariable}Service.findOne(sddPersisted.get$propertyIdentifier()));
    }
}