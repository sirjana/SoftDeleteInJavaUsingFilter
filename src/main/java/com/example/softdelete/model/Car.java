package com.example.softdelete.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Table(name = "car")
@FilterDef(
        name = "deletedCarFilter",
        parameters = @ParamDef(name = "isDeleted", type = java.lang.Boolean.class)
)
@Filter(
        name = "deletedCarFilter",
        condition = "deleted = :isDeleted"
)
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    private String model;

    private String color;

    private Boolean deleted;
}
