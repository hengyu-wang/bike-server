# bikeshare
2022年物联网比赛-服务端代码
JDK版本为1.8，需要添加的Spring boot依赖（web，JPA，MYSQL Driver）
1.resources/application.yml：用于配置数据库连接信息，服务器端口，以及是否展示SQL语句等  
2.demo/controller/backpower:主要用于接收安卓请求，访问数据库，以及和板子进行通信  
3.demo/entity：实体类文件  
4.demo/repository:对实体类进行增删改查  
5.SimpleServer:接收板子的信息，开线程并绑定套接字，以及存库操作  
6.DemoApplication:服务器运行文件  
