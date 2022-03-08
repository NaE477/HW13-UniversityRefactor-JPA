package models.things;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.users.Professor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer units;
    private String courseName;
    @ManyToOne
    private Professor professor;
    @ManyToOne
    private Term term;

    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Grade> grades;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        if(professor != null)
        return "\u001b[31m" + "ID: " + getId() +
                ", Course Name: " + getCourseName() +
                ", Professor: " + professor.getFirstname() + " " + professor.getLastname() +
                ", Unit:" + getUnits() +
                ", Term: " + getTerm().getTerm() + "\u001b[0m";
        else
            return "\u001b[31m" + "ID: " + getId() +
                    ", Course Name: " + getCourseName() +
                    ", Professor: " + "No professor yet" +
                    ", Unit:" + getUnits() +
                    ", Term: " + getTerm().getTerm() + "\u001b[0m";
    }
}
