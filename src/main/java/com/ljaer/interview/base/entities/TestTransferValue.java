package com.ljaer.interview.base.entities;

public class TestTransferValue {

    public void changeValue1(int age){
        age = 30;
    }

    public void changeValue2(Person person){
        person.setPersonName("xxx");
    }

    public void changeValue3(String str){
        str = "xxx";
    }

    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        //基本类型
        int age = 20;
        test.changeValue1(age);
        System.out.println("age----"+age);

        //引用类型
        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println("person----"+person);

        //引用类型
        String str = "abc";// "abc" 这种的在字符串常量池
        test.changeValue3(str);
        System.out.println("String----"+str);
    }

}
