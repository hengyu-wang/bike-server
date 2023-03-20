package com.example.demo.repositiry;

import com.example.demo.entity.PrimaryKey;
import com.example.demo.entity.record;
import com.example.demo.repository.bikeRepository;
import com.example.demo.repository.recordRepository;
import com.example.demo.repository.subscriberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookRepositoryTest {

    @Autowired      //自动注入
    private subscriberRepository sub;

    @Autowired
    private bikeRepository bi;

    @Autowired
    private recordRepository re;
    @Test
    void findAll(){

        String data = "userid-bikeid-0-jindu-weidu-v-batteryid-dianliang-wendu-lasttime-N1]userid-bikeid-0-jindu-weidu-v-batteryid-dianliang-wendu-lasttime-N2]";
        System.out.println(data.substring(data.length()-1));
        if(data.substring(data.length()-1).equals("]"))
        {
            System.out.println("断网重连，断网过程中阻塞的数据如下:");
            String[] st = data.split("]");
            for(int i=0;i<=1;i++)
            {
                String[] strs = st[i].split("-");
                System.out.println("第N"+Integer.toString(i+1)+"条信息:  "+"用户id:"+strs[0]+"  电车id:"+strs[1]+"  开关状态(1=开；0=关):"+strs[2]+"  经度:"+strs[3]+"  纬度:" +
                        strs[4]+ "  速度:"+strs[5]+"  电池id:"+strs[6]+"  电量:"+strs[7]+"  温度:"+strs[8]+"  lasttime:"+strs[9]);
            }
            System.out.println();

        }
        else{
            System.out.println("no");
        }

//        String[] strs = data.split("-");
//        System.out.println("用户id:"+strs[0]+"  电车id:"+strs[1]+"  开关状态(1=开；0=关):"+strs[2]+"  经度:"+strs[3]+"  纬度:" +
//               strs[4]+ "  速度:"+strs[5]+"  电池id:"+strs[6]+"  电量:"+strs[7]+"  温度:"+strs[8]+"  lasttime:"+strs[9]);

//        record r = re.findByBikeId("1A2B3C4D");
//        System.out.println(r==null);

    }

}