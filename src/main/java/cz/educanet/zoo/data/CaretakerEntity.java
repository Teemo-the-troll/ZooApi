package cz.educanet.zoo.data;

import cz.educanet.zoo.Caretaker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CaretakerEntity {
    String firstName;
    String lastName;
    char gender;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public CaretakerEntity(Caretaker caretaker) {
        this.firstName = caretaker.getFirstName();
        this.lastName = caretaker.getLastName();
        this.gender = caretaker.getGender();
    }

    public CaretakerEntity(Integer id, CaretakerEntity old) {
        this.firstName = old.getFirstName();
        this.lastName = old.getLastName();
        this.gender = old.getGender();
        this.setId(id);
    }

    public CaretakerEntity() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
