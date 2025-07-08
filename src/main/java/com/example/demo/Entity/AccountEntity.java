package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, unique = true)
    private Long seq;

    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "newdate", nullable = false)
    private LocalDateTime newdate;

    @Column(name = "newdateuser", nullable = false)
    private String newdateuser;

    @Column(name = "update", nullable = false)
    private LocalDateTime update;

    @Column(name = "updateuser", nullable = false)
    private String updateuser;

    @Column(name = "adflag", nullable = false)
    private String adflag;

    // ゲッターとセッター

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getNewdate() {
        return newdate;
    }

    public void setNewdate(LocalDateTime newdate) {
        this.newdate = newdate;
    }

    public String getNewdateuser() {
        return newdateuser;
    }

    public void setNewdateuser(String newdateuser) {
        this.newdateuser = newdateuser;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getAdflag() {
        return adflag;
    }

    public void setAdflag(String adflag) {
        this.adflag = adflag;
    }
}
