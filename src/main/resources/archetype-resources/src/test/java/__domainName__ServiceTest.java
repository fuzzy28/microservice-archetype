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

@Transactional
public class ${domainName}ServiceTest extends AbstractTest {

    @Autowired
    ${domainName}Service ${domainNameVariable}Service;

    @Test
    public void whenPersistingWithoutIdThenShouldPersist() {
	// assert saving
	${domainName} sdd = new ${domainName}();
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	${domainName} sddPersisted = ${domainNameVariable}Service.save(sdd);
	assertNotNull("failure - ${domainNameVariable} was not persisted", sddPersisted);
    }

    @Test
    public void whenPesistingWithIdThenShouldNotPersist() {
	// assert saving with Id
	${domainName} sdd = new ${domainName}();
	sdd.setId(1L);
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	${domainName} sddPersisted = ${domainNameVariable}Service.save(sdd);
	assertNull("failure - saving ${domainNameVariable} with ID was persisted", sddPersisted);
    }

    @Test
    public void whenSuccessfullyPersistedThenShouldRetrieve() {
	// assert saving
	${domainName} sdd = new ${domainName}();
	sdd.set${domainName}Code("SDD");
	sdd.set${domainName}Name("SOFTWARE DEVELOPMENT DEPARTMENT");
	sdd.setActive(true);

	${domainName} sddPersisted = ${domainNameVariable}Service.save(sdd);
	assertNotNull("failure - ${domainNameVariable} was not persisted", sddPersisted);

	${domainName} fep = new ${domainName}();
	fep.set${domainName}Code("FEP");
	fep.set${domainName}Name("FRONT END PROCESSING DEPARTMENT");
	fep.setActive(true);
	assertNotNull("failure - ${domainNameVariable} was not persisted",
		${domainNameVariable}Service.save(fep));

	// assert find all
	int total = ${domainNameVariable}Service.findAll().size();
	assertEquals("failure - total size does not match", 2, total);

	// assert find single
	${domainName} actual = ${domainNameVariable}Service.findOne(sddPersisted.getId());
	assertEquals("failure - ${domainNameVariable} code not match", sdd.get${domainName}Code(),
		actual.get${domainName}Code());
	assertEquals("failure - ${domainNameVariable} name not match", sdd.get${domainName}Name(),
		actual.get${domainName}Name());
	assertEquals("failure - ${domainNameVariable} active not match", sdd.isActive(),
		actual.isActive());
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

	sdd.setId(Long.MAX_VALUE);

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
	${domainNameVariable}Service.delete(sddPersisted.getId());
	assertNull("failure - resource has not been deleted",
		${domainNameVariable}Service.findOne(sddPersisted.getId()));
    }
}