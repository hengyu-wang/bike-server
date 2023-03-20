package com.example.demo.repository;

import com.example.demo.entity.bike;
import com.example.demo.entity.record;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Service(value = "bikeRepository")
//整合的hibernate，相当于对这个表直接提供了增删改查的方法
public interface bikeRepository extends JpaRepository<bike,String> {

    @Modifying
    @Transactional
    @Query(value = "update bike set bike.statu=:statu where bike.bikeid = :bikeid",nativeQuery = true)
    void update(@Param("bikeid") String bikeid,@Param("statu") String statu);

}

