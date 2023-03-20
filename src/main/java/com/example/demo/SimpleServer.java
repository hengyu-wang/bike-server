package com.example.demo;

import com.example.demo.config.SpringBeanUtil;
import com.example.demo.entity.bike;
import com.example.demo.entity.record;
import com.example.demo.repository.bikeRepository;
import com.example.demo.repository.recordRepository;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SimpleServer extends Thread{

    public recordRepository recordRepo;
    public bikeRepository bikeRepo;
    private ServerSocket serverSocket = null;
    private Socket s1 = null;
    public static Map<String, Socket> hostMap = new HashMap<String, Socket>();
    public static Map<String, String> bikeMap = new HashMap<String, String>();
    public static int index = 1;

    public SimpleServer(){

    }
    public void startSocket() {

        try {
            System.out.println("使用端口8086！！！");
            serverSocket = new ServerSocket(8086);
            this.start();       //调用下面的run方法
        } catch (IOException ex) {
            System.out.println("Exception on new ServerSocket: " + ex);
        }
    }


    public void run() {
        while (true) {
            try {
                this.recordRepo = (recordRepository) SpringBeanUtil.getBean("recordRepository");
                this.bikeRepo = (bikeRepository)SpringBeanUtil.getBean("bikeRepository");

                PrintWriter pw = null;
                System.out.println("Waiting for connect to client");
                s1 = serverSocket.accept();
                //如果这个ip已经在map里面了就不需要开线程
                if(!hostMap.containsKey(s1.getInetAddress().toString())) {
                    new Thread(new Runnable(){
                        public recordRepository recordRepo;
                        public bikeRepository bikeRepo;
                        @Override
                        public void run() {
                            this.bikeRepo = SpringBeanUtil.getBean(bikeRepository.class);
                            this.recordRepo = SpringBeanUtil.getBean(recordRepository.class);
                            System.out.println("开启一个新线程并run！");

                            System.out.println("Connection received from " + s1.getInetAddress().toString().replace("/","")+"    现在是第  ："+index++);	//获得主机名
                            hostMap.put(s1.getInetAddress().toString().replace("/",""), s1);
                            System.out.println("当前放入一个ip到hashmap  ："+s1.getInetAddress().toString().replace("/",""));

                            try {
                                InputStream in = s1.getInputStream();//从客户端生成网络输入流，用于接收来自网络的数据
                                byte[] bt = new byte[1024];//定义一个字节数组，用来存储网络数据
                                int len;//将网络数据写入字节数组
                                while((len = in.read(bt)) != 0) {
                                    String data = new String(bt, 0 , len);//将网络数据转换为字符串数据
                                    System.out.println("来自某个客户端："+ data);

                                    //----------------------------------------------------start
                                    //对数据进行解析
                                    if(data.substring(data.length()-1).equals("]"))
                                    {
                                        System.out.println("断网重连，断网过程中阻塞的数据如下:");
                                        String[] st = data.split("]");
                                        for(int i=0;i<=1;i++)   //断网重连后发送多少个信息
                                        {
                                            String[] strs = st[i].split("-");
                                            System.out.println("第N"+Integer.toString(i+1)+"条信息:  "+"用户id:"+strs[0]+"  电车id:"+strs[1]+"  开关状态(1=开；0=关):"+strs[2]+"  经度:"+strs[3]+"  纬度:" +
                                                    strs[4]+ "  速度:"+strs[5]+"  电池id:"+strs[6]+"  电量:"+strs[7]+"  温度:"+strs[8]+"  lasttime:"+strs[9]);
                                        }
                                        System.out.println();

                                    }
                                    else{
                                        String[] str = data.split("-");
                                        System.out.println("用户id:"+str[0]+"  电车id:"+str[1]+"  开关状态(1=开；0=关):"+str[2]+"  经度:"+str[3]+"  纬度:" +
                                                str[4]+ "  速度:"+str[5]+"  电池id:"+str[6]+"  电量:"+str[7]+"  温度:"+str[8]+"  lasttime:"+str[9]);

                                    }
                                    //无论是开还是关都是发一整串信息
                                    String[] strs = data.split("-");
                                    String userId = strs[0];
                                    String bikeId = strs[1];
                                    if(userId.equals("0000")) {		//说明此时还车
                                        record r = recordRepo.findByBikeId(bikeId);
                                        bikeRepo.update(bikeId,"0");	//还车要把状态设为0
                                        if(r!=null) {	//说明这个记录需要删掉
                                            recordRepo.delete(r);
                                        }
                                    }
                                    else {
                                        //此时在借车
                                        if(!bikeMap.containsKey(bikeId))			//第一次借车的时候才进行绑定
                                        {
                                            bikeMap.put(bikeId,s1.getInetAddress().toString().replace("/",""));		//每一次来一个就把ip和板子的id绑定
                                            System.out.println("当前绑定了"+bikeId+"  "+s1.getInetAddress().toString().replace("/",""));
                                        }
                                        //此时判断一下自行车有没有被租用（record表里面有没有这个bikeId）
                                        //如果记录的bikeId是空的说明还有没被租用，需要将用户id和bikeid先插入数据库后再更新
                                        //userid-bikeId-statu-jindu-weidu-sudu-batteryid-dianliang-wendu-lasttime
                                        bike b = new bike(strs[1], strs[3], strs[4], strs[6], strs[2], strs[5], strs[9], strs[7], strs[8]);
                                        bikeRepo.save(b);
                                        if(recordRepo.findByBikeId(bikeId)== null) {    //如果是第一次遇到需要添加一次绑定
                                            recordRepo.save(new record(strs[0], bikeId));
                                        }
                                    }
                                    //-------------------------------------------------------end
                                    //………………………………………………………………………………………………………………new………………………………………………………………………………………………//
                                }
                                System.out.println("关闭s");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                System.out.println("返回失败！");
                            }
                        }
                    }).start();

                }
                // throw new ArithmeticException();

            } catch (IOException ex) {
                Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception e) {
                System.out.println("Exceptiopn: "+e);
            }
        }
    }
    public Socket getSocket(int index) {
        return hostMap.get(index);
    }
}
