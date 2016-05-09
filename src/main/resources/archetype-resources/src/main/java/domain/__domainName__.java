#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The ${domainName} class is the most important entity of this service.
 * 
 * @author ${author}
 * @since 1.0
 */

@Entity
public class ${domainName} {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "${domainNameVariable}code")
    @NotNull(message = "err.${domainNameVariable}code.notnull")
    @Size(min = 1, max = 10, message = "err.${domainNameVariable}code.size")
    private String ${domainNameVariable}Code;

    @Column(name = "${domainNameVariable}name")
    @NotNull(message = "err.${domainNameVariable}name.notnull")
    @Size(min = 1, max = 100, message = "err.${domainNameVariable}name.size")
    private String ${domainNameVariable}Name;

    @Column
    private boolean active = true;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String get${domainName}Code() {
	return ${domainNameVariable}Code;
    }

    public void set${domainName}Code(String ${domainNameVariable}Code) {
	this.${domainNameVariable}Code = ${domainNameVariable}Code;
    }

    public String get${domainName}Name() {
	return ${domainNameVariable}Name;
    }

    public void set${domainName}Name(String ${domainNameVariable}Name) {
	this.${domainNameVariable}Name = ${domainNameVariable}Name;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

}