package com.example.demo.controller;
import com.example.demo.DemoApplication;
import com.example.demo.entity.bike;
import com.example.demo.entity.record;
import com.example.demo.entity.subscriber;
import com.example.demo.repository.bikeRepository;
import com.example.demo.repository.recordRepository;
import com.example.demo.repository.subscriberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//由于给Android提供服务

@RestController
@RequestMapping("/sharingBike")
public class backpower {
    @Autowired
    private bikeRepository bikeRepo;
    @Autowired
    private recordRepository recordRepo;
    @Autowired
    private subscriberRepository subscriberRepo;


    //通过userid返回当前绑定自行车的状态，用于刷新
    @GetMapping("/backPower")
    public String aaa(@Param("userid") String userid) throws JsonProcessingException {
        record r = recordRepo.findByUserId(userid);
        Optional<bike> b;
        if(r==null){
            b = Optional.of(new bike("0", "0", "0", "0", "0", "0.00r/s", "0", "100", "0"));
        }
        else {
            b = bikeRepo.findById(r.getBikeid());
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        List<Optional<bike>> list=new ArrayList<>();
        list.add(b);
        String jsonList = mapper.writeValueAsString(list);

        return jsonList;
    }

    @GetMapping("/close")
    //安卓传过来的是用户的id,用于关闭自行车
    public String bbb(@Param("userid") String userid) throws IOException {
        record r = recordRepo.findByUserId(userid);
        System.out.println(userid);
        String bikeId = recordRepo.findByUserId(userid).getBikeid();


        String ipclose = DemoApplication.server.bikeMap.get(bikeId);
        System.out.println("调用了关闭，ip是："+ipclose);
        Socket socket = (Socket) DemoApplication.server.hostMap.get(ipclose);		//获取目的套接字
        PrintWriter back = new PrintWriter(socket.getOutputStream(), true);
        back.println("tingzhi");
//向安卓写回标志
        //说明此时是开着,更新状态
        bikeRepo.update(bikeId, "0");
        return "hasClose";
    }

    @GetMapping("/start")
    public String ccc(@Param("userid") String userid) throws IOException {
        System.out.println(userid);
        record r = recordRepo.findByUserId(userid);
        String bikeId = r.getBikeid();
//        //用于向板子写回数据
        String ipstart = DemoApplication.server.bikeMap.get(bikeId);
        System.out.println("调用了打开，ip是："+ipstart);
        Socket socket = DemoApplication.server.hostMap.get(ipstart);		//获取目的套接字
        PrintWriter back = new PrintWriter(socket.getOutputStream(), true);
        back.println("kaishi");
//向安卓写回标志
        //说明此时是关着

        bikeRepo.update(bikeId,"1");
        //向安卓写回数据
        return "hasStart";
    }

    @GetMapping("/huanche")
    public String ddd(@Param("userid") String userid) throws IOException {
        //安卓传过来用户id
        System.out.println(userid);
        record r = recordRepo.findByUserId(userid);
        String bikeId = r.getBikeid();
        //        //用于向板子写回数据
        String ipstart = DemoApplication.server.bikeMap.get(bikeId);
        System.out.println("调用了还车，ip是："+ipstart);
        Socket socket = DemoApplication.server.hostMap.get(ipstart);		//获取目的套接字
        PrintWriter back = new PrintWriter(socket.getOutputStream(), true);
        back.println("huanche");

        //还车需要将自行车的状态变为0，然后删掉记录
        bikeRepo.update(bikeId,"0");
        recordRepo.delete(r);
        //向安卓写回标志
        return "hasHuanche";
    }

    //登录的方法，基本不需要改
    @GetMapping("/login")
    public String eee(@Param("username") String username,@Param("userid") String userid)
    {
        Optional<subscriber> sub = subscriberRepo.findById(userid);
        if(sub.get().getName().equals(username)){
            return "success";
        }
        else{
            return "fail";
        }
    }


}

