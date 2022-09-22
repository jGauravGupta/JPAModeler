/**
 * 11.1.4 AttributeOverride Annotation
 * Example 1
 */
package io.github.jeddict.jpa.attribute.override.example1;

import jakarta.persistence.Basic;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * @author jGauravGupta
 */
@MappedSuperclass
public class Employee {

    @Id
    protected Long id;

    @Basic
    private String address;

    @Version
    protected long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}