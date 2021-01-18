package cz.educanet.zoo.data;

import cz.educanet.zoo.Animal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnimalEntity {
    private String name;
    private int age;
    private int weight;
    private char gender;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public AnimalEntity() {
    }

    public AnimalEntity(Integer id, AnimalEntity old) {
        this.name = old.getName();
        this.age = old.getAge();
        this.weight = old.getWeight();
        this.gender = old.getGender();
        this.setId(id);
    }

    public AnimalEntity(Animal a) {
        this.name = a.getName();
        this.age = a.getAge();
        this.weight = a.getWeight();
        this.gender = a.getGender();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
