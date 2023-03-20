package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Data       //比赛是需要修改，如果是复合主键时就需要使用这个类；但是和外键没有关系，也就是说JPA不需要表示外键的关系
public class PrimaryKey implements Serializable {

    private String userid;
    private String bikeid;

    public PrimaryKey(){

    }

    public PrimaryKey(String a, String b) {
        this.userid = a;
        this.bikeid = b;
    }
}