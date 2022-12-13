/**
 * 11.1.43 OrderColumn Annotation
 * Table 40
 */
package io.github.jeddict.jpa.ordercolumn;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jGauravGupta
 */
@Entity
public class Student {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        getCourses().add(course);
    }

    public void removeCourse(Course course) {
        getCourses().remove(course);
    }

}