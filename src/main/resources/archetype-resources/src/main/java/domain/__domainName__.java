#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * The ${domainName} class is the most important entity of this service.
 * 
 * @author ${author}
 * @since 1.0
 */

@Entity
public class ${domainName} {

    #foreach($prop in $propertyList.split(","))
    
	#set( $index = ${prop.indexOf("=")} )
    	#set( $name = ${prop.substring(0, $index)} )
    	#set( $lowerCaseProp = ${name.toLowerCase()} )
	#set( $index = $index + 1 )
    	#set( $type = ${prop.substring($index)} )
    	#if ($name.equals($propertyId))
    	    @Id
    	    @GeneratedValue(strategy = GenerationType.IDENTITY)
    	#end
    @Column(name = "$lowerCaseProp")
    private $!type $!name;
    #end
    #foreach($prop in $propertyList.split(","))
	#set( $index = ${prop.indexOf("=")} )
    	#set( $name = ${prop.substring(0, $index)} )
    	#set( $capitalizeProp = "${name.substring(0,1).toUpperCase()}${name.substring(1)}")
	#set( $index = $index + 1 )
    	#set( $type = ${prop.substring($index)} )
    public $type get$capitalizeProp() {
        return $name;
    }
    
    public $type set$capitalizeProp($type $name) {
        return this.$name = $name;
    }
    
    #end

}