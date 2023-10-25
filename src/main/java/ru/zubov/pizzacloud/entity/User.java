package ru.zubov.pizzacloud.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "st")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private final Set<RoleUser> authorities = new HashSet<>();

    public User(String username, String password, List<RoleUser> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = "";
        this.street = "";
        this.city = "";
        this.state = "";
        this.zip = "";
        this.phoneNumber = "";
        this.authorities.addAll(authorities);
    }

    public User(String username, String encode, String fullname, String street, String city, String state, String zip, String phone) {
        this.username = username;
        this.password = encode;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone;
    }

    @Override
    public Collection<RoleUser> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}