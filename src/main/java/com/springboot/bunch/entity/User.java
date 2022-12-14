package com.springboot.bunch.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Bunch> createdBunches;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "bunch_favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bunch_id"))
    Set<Bunch> favouriteBunches;
}
