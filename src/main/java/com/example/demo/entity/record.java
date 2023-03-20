package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;


@Entity
@IdClass(PrimaryKey.class)
@DynamicUpdate
@Data
public class record {
    public record(){

    }

    public record(String userid, String bikeid) {
        this.userid = userid;
        this.bikeid = bikeid;
    }
    //    alt + insert快速生成构造方法
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBikeid() {
        return bikeid;
    }

    public void setBikeid(String bikeid) {
        this.bikeid = bikeid;
    }

    @Id String userid;
    @Id String bikeid;

//    private Integer sScore;       //也可以添加一个新的主键


}

