package by.hibernate.ignatieva.demo.repository;

import by.hibernate.ignatieva.demo.model.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position, Long> {
}
