package by.hibernate.ignatieva.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @NotNull
    @ManyToOne
    private Position position;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Department department, Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonBackReference
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) &&
                lastName.equals(employee.lastName) &&
                department.getName().equals(employee.department.getName()) &&
                position.getName().equals(employee.position.getName());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + department.getName().hashCode();
        result = 31 * result + position.getName().hashCode();
        return result;
    }
}
