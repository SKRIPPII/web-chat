package com.example.MyFirstRestApp.models;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private int friend_id;
    @Column(name = "login")
    private String login;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;
    public Friend() {
    }

    public Friend(String login, Person person) {
        this.login = login;
        this.person = person;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Frend{" +
                "friend_id=" + friend_id +
                ", login='" + login + '\'' +
                ", person=" + person +
                '}';
    }
}
