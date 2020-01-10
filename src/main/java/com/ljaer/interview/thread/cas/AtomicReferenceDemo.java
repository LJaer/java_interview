package com.ljaer.interview.thread.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Data
@AllArgsConstructor
@ToString
class User {
    String userName;
    int age;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());

        //AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(z3,1);
        //System.out.println(atomicStampedReference.compareAndSet(z3,li4,1,2) + "\t" + atomicStampedReference.getReference().toString());;
    }
}
