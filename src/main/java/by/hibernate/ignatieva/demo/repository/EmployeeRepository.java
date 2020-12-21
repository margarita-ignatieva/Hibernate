package by.hibernate.ignatieva.demo.repository;

import by.hibernate.ignatieva.demo.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
