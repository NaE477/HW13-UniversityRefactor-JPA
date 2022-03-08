package models.users;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Clerk extends User {
    private Double salary = 2000000.0;

    public Clerk(Integer clerkId, String firstname, String lastname, String username, String password) {
        super(clerkId, firstname, lastname, username, password);
    }

    @Override
    public String toString() {
        return "\u001b[31m" + "ID: " + getId() +
                ", Full Name: " + getFirstname() + " " + getLastname() +
                ", Username: " + getUsername() +
                ", Salary: " + getSalary() + "\u001b[0m";
    }
}
