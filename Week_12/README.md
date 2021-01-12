##Kafka 压测记录

### 压测环境
CPU: AMD 3700X<br>
内存: 16G(3000MHz)
硬盘: 1TB西数机械硬盘 500G三星970EVO

### Windows10 搭建kafka集群
https://articles.zsxq.com/id_ww5w6wuz5h6p.html

### 三节点机械硬盘测试
1.创建topic test 指定分区为1个 副本数为2<br>
.\bin\windows\kafka-topics.bat --zookeeper 127.0.0.1:2181 --create --topic test --partitions 1 --replication-factor 2
2.