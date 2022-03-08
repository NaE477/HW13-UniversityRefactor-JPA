package models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.things.Grade;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends User{
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private Set<Grade> grades;

    public Student(Integer id,String firstname,String lastname,String username,String password){
        super(id,firstname,lastname,username,password);
    }

    @Override
    public String toString() {
        return "\u001b[31m" + "ID: " + getId() +
                ", Full Name: " + getFirstname() + " " + getLastname() +
                ", Username: " + getUsername() + "\u001b[0m";
    }
}
