package com.zhxq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User")
public class User {
    @OrderBy("grade desc")
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Integer userclass;


    @NotNull
    @Column(unique = true)
    private String userid;

    private Integer grade = 0;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private Integer rank = 1;

    @NotNull
    private String authority = "ROLE_USER";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.grade = 0;
    }

    public User(String username, String password, Integer userclass, String userid) {
        this.username = username;
        this.password = password;
        this.userclass =userclass;
        this.userid = userid;
        this.grade = 0;
    }

    public User(String username, String password, Integer userclass, String userid, Integer grade) {
        this.username = username;
        this.password = password;
        this.userclass = userclass;
        this.userid = userid;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public Integer getuserclass() {
        return userclass ;
    }

    public void setuserclass(Integer userclass) {
        this. userclass = userclass ;
    }

    public Integer getgrade() {
        return grade;
    }

    public void setgrade(Integer grade) {
        this.grade = grade;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}