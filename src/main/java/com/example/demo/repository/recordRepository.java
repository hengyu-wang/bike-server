package com.example.demo.repository;


import com.example.demo.entity.record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Service(value = "recordRepository")
public interface recordRepository extends JpaRepository<record,Class> {
    @Query("select r from record r where r.userid = :userid")
    record findByUserId(@Param("userid") String userid);

    @Query("select r from record r where r.bikeid = :bikeid")
    record findByBikeId(@Param("bikeid") String bikeid);

//    @Query(value = "INSERT into record(userId,bikeId) values (:userid,:bikeid)")
//    record save(@Param("userid") String userid,@Param("bikeid") String bikeid);

//    @Modifying
//    @Query(value = "INSERT into record(userId,bikeId) values (:#{#r.userid},:#{#r.bikeid})",nativeQuery = true)
//    void saveOne(record r);
}
