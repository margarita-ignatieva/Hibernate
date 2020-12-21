package by.hibernate.ignatieva.demo.repository;

import by.hibernate.ignatieva.demo.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
