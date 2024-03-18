package com.example.softdelete.repository;
import com.example.softdelete.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    @Override
    @Transactional
    @Modifying
    @Query("UPDATE Car c SET c.deleted = true WHERE c.id = ?1")
    void deleteById(Long id);
}
