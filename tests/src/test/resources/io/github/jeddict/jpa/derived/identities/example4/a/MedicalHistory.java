/**
 * 2.4.1.3 Examples of Derived Identities
 * Example 4 : Case (a)
 *
 */
package io.github.jeddict.jpa.derived.identities.example4.a;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * @author jGauravGupta
 */
@Entity
public class MedicalHistory {

    @Id
    @OneToOne
    @JoinColumn(name = "FK")
    private Person patient;

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Person patient) {
        this.patient = patient;
    }

}