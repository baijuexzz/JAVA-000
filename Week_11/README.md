# 学习笔记
> 作业相关记录
>>

## 1.Redis 命令学习  只覆盖常用的使用方式
<a href="https://www.runoob.com/redis">基本指令学习资料</a>
<br>
<a href="https://juejin.cn/book/6844733724618129422/section/6844733724702015501">bitmap指令参考资料</a>

### 1.1 通用指令
|指令 |描述 |相关参数|
|:------|:------|:------|
|exists|判断是否存在|exists key |
|del|删除key操作| del key|
|info|查看redis实例详情|info|
|expire|给对应的key设置过期时间|expire key second|
|keys |通配符查询key，会阻塞redis生产环境禁用|keys *|
|ttl|显示当前key还有多长时间过期|ttl key|

### 1.2 String 类型命令学习

|指令 |描述 |相关参数|
|:------|:------|:------|
|set|进行赋值操作 可为任何类型|set key value EX/PX NX/XX (EX/PX代表设置过期时间 EX 单位为s，PX单位为毫秒，NX代表键不存在时设置，XX代表键存在时设置)|
|get|获取值操作|get key|
|getset|获取当前值并赋值新值|getset key value|
|incr|自增+1操作 适用int存储|incr key|
|decr|减少-1 操作 适用int存储|decr key|
|incrby|自增操作 可设置增加大小 |incrby key increment|
|decrby|减少操作 可设置减少大小| decrby key decrement|
|append|追加操作 在当前值进行追加|append key|


### 1.3 Hash 类型命令学习 对应JAVA中的map

|指令 |描述 |相关参数|
|:------|:------|:------|
|hset|给子键赋值 可赋值多个|hset key [field value]|
|hget|获取子键的值|hget key filed |
|hmset|批量子键赋值|hmset key [field value] |
|hmget|批量获取子键值|hmget key [field]|
|hgetall|获取所有值包括键、值|hgetall key|
|hdel|删除子键操作 可删除多个|hdel key [field]|
|hexists|判断子键是否存在| hexists key field|
|hlen|获取该Key的长度|hlen key|
|hkeys|获取该Key的所有子键| hkeys key |
|hvals|获取该Key的所有子键值| hvals key|


### 1.4 List 类型命令学习 

|指令 |描述 |相关参数|
|:------|:------|:------|
|lpush|从链表左侧插入|lpush key [element]|
|rpush|从链表右侧插入|rpush key [element] |
|lrange|获取链表值|lrange key start stop (stop为 -1时代表获取从start往后的全部)  |
|lpop|从链表左侧弹出元素|lpop key|
|rpop|从链表右侧弹出元素|rpop key|


### 1.5 Set 类型命令学习
|指令 |描述 |相关参数|
|:------|:------|:------|
|sadd|向set集合插入元素 可插入多个|sadd key [member]|
|srem|从set集合删除元素 可删除多个|srem key [member] |
|scard|获取集合长度|scard key |
|smembers|获取set集合所有元素|smembers key  |
|sismember|判断set集合是否存在某一元素|sismember key member|
|sdiff|获取多个set集合的差集|sdiff key....key1|
|sinter|获取多个set集合的交集|sinter key....key1|
|sunion|获取多个set集合的并集|sunion key....key1|

### 1.6 Sorted Set 类型命令学习
|指令 |描述 |相关参数|
|:------|:------|:------|
|zadd|向集合插入元素 可插入多个|zadd key [score memeber...]|
|zrevrange|按照分数从大到小的顺序返回索引从start|zrevrange key start stop [withscores]|
|zrem|删除特定元素|zrem [score memeber...]  |

### 1.7 Bitmaps 类型命令学习（本质还是String类型）
|指令 |描述 |相关参数|
|:------|:------|:------|
|setbit|对应位置插入|setbit key offset value|
|getbit|获取对应位置的值|getbit key offset|
|bitcount |统计在指定"范围"内为1的值|bitcount key [start stop] (start/stop 指的的byte不是bit )  |

### 1.7 HyperLogLog  有一定误差率的去重 不存储数据本身的信息
|指令 |描述 |相关参数|
|:------|:------|:------|
|pfadd|增加数据|pfadd [element]|
|pfcount|获取总数|pfcount key|
|pfmerge |数据合并| pfmerge key...key1 |