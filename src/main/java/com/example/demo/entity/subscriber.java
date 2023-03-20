package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;




@Entity //直接将表名和类名绑定
@Data   //自动生成get，set方法
public class subscriber {
    @Id     //指明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;
    private String name;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
