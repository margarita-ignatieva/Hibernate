package by.hibernate.ignatieva.demo.service;

import by.hibernate.ignatieva.demo.model.Department;
import by.hibernate.ignatieva.demo.model.Employee;
import by.hibernate.ignatieva.demo.model.Position;
import by.hibernate.ignatieva.demo.repository.DepartmentRepository;
import by.hibernate.ignatieva.demo.repository.EmployeeRepository;
import by.hibernate.ignatieva.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetUp = false;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetUp) {
            return;
        }
        Department depart1 = new Department();
        depart1.setName("IT");
        Department depart2 = new Department();
        depart2.setName("Marketing");
        departmentRepository.save(depart1);
        departmentRepository.save(depart2);
        Position pos1 = new Position();
        pos1.setName("HR");
        Position pos2 = new Position();
        pos2.setName("SMM");
        positionRepository.save(pos1);
        positionRepository.save(pos2);
        Employee employee1 = new Employee("John","Brown", depart1, pos1);
        Employee employee2 = new Employee("Max", "Black", depart2, pos2);
        Employee employee3 = new Employee("Max", "Smith", depart1, pos2);
        Employee employee4 = new Employee("Sara", "Jane", depart1, pos2);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        alreadySetUp = true;
    }
}
