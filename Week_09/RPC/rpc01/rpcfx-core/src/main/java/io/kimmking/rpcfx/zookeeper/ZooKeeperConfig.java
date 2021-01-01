package io.kimmking.rpcfx.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zk 配置
 */
public class ZooKeeperConfig {


    /**
     * 默认组名称 无值时引用此值
     */
    private static final String DEFAULTGROUPNAME="DEFAULT-GROUP";

    /**
     * zk 地址
     */
    private CuratorFramework zooKeeperClient;


    private String groupName;



}
