package com.example.softdelete.controller;

import com.example.softdelete.model.Car;
import com.example.softdelete.repository.CarRepository;
import com.example.softdelete.service.CarService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CarController {

    private final CarService carService;

    private final CarRepository carRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CarController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @PostMapping("/listall")
    public ResponseEntity<List<Car>> listAll() {
        List<Car> car1 = carService.listAllCar();
        return ResponseEntity.ok(car1);
    }

    @PostMapping("/save")
    public ResponseEntity<Car> createEntity(@RequestBody Car car) {
        Car car1 = carService.saveEntity(car);
        return ResponseEntity.ok(car1);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        carService.deleteEntity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Car>> findAll(@RequestParam(value =
            "isDeleted", required = false, defaultValue = "false")boolean
                                                     isDeleted) {
        List<Car> cars = findAllFilter(isDeleted);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    public List<Car> findAllFilter(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedCarFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Car> cars = carRepository.findAll();
        session.disableFilter("deletedCarFilter");
        return cars;
    }
}
