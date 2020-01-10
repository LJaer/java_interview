package com.ljaer.interview.base.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Person {
    private Integer id;
    private String personName;

    public Person(String personName){
        this.personName = personName;
    }
}
