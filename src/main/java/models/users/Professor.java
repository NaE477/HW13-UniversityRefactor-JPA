package models.users;

import lombok.*;
import models.things.Course;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Professor extends User {
    @OneToMany(mappedBy = "professor",fetch = FetchType.EAGER)
    private Set<Course> courses;
    @Enumerated(EnumType.STRING)
    private ProfPosition profPosition;

    public Professor(Integer professorId, String firstname, String lastname, String username, String password, ProfPosition profPosition) {
        super(professorId, firstname, lastname, username, password);
        this.profPosition = profPosition;
    }

    @Override
    public String toString() {
        return "\u001b[31m" + "ID: " + super.getId() +
                " ,Full Name: " + super.getFirstname() + " " + super.getLastname() +
                " ,Username: " + super.getUsername() +
                " ,Position: " + profPosition.toString() + "\u001b[0m";
    }
}
