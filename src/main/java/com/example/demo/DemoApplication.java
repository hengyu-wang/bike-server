package com.example.demo;


import com.example.demo.config.SpringBeanUtil;
import com.example.demo.repository.bikeRepository;
import com.example.demo.repository.recordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringBeanUtil.class)
public class DemoApplication{
    public static SimpleServer server;

    @Autowired
    public static recordRepository recordRepo;
    @Autowired
    public static bikeRepository bikeRepo;

    public static void main(String[] args)
    {   //必须开启一个新线程接收socket连接

        SpringApplication.run(DemoApplication.class, args);
        server = new SimpleServer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.startSocket();
            }
        }).start();
        System.out.println("在这里开一个新线程来接受套接字");
    }


}
