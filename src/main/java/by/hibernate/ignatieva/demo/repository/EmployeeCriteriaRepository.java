package by.hibernate.ignatieva.demo.repository;

import by.hibernate.ignatieva.demo.model.Employee;
import by.hibernate.ignatieva.demo.model.EmployeePage;
import by.hibernate.ignatieva.demo.model.EmployeeSearchCriteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employee> findAllWithFilters(EmployeePage employeePage,
                                             EmployeeSearchCriteria employeeSearchCriteria) {
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot =criteriaQuery.from(Employee.class);
        Predicate predicate = constructPredicate(employeeSearchCriteria, employeeRoot);
        criteriaQuery.where((predicate));

        setOrder(employeePage, employeeRoot, criteriaQuery);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());

        Pageable pageable = getPageable(employeePage);
        
        long employeesCount = countEmployees(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private void setOrder(EmployeePage employeePage, Root<Employee> employeeRoot,
                          CriteriaQuery<Employee> criteriaQuery) {
        if (employeePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
        }
    }

    private long countEmployees(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(EmployeePage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }

    private Predicate constructPredicate(EmployeeSearchCriteria employeeSearchCriteria,
                                         Root<Employee> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (employeeSearchCriteria.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(employeeRoot.get("firstName"),
                    "%" + employeeSearchCriteria.getFirstName() + "%"));
        }
        if (employeeSearchCriteria.getLastName() != null) {
            predicates.add(criteriaBuilder.like(employeeRoot.get("lastName"),
                    "%" + employeeSearchCriteria.getLastName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
