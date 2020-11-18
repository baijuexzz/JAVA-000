package day06.jdbcdemo;

import com.google.common.collect.Lists;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

/**
 * JDBC连接工具类
 */
public class ConnectionPool {

    private static final String JDBCURL = "jdbc:h2:~/test";

    private static final String USER = "sa";

    private static final String PASSWORD = "sa";

    private static final String DRIVERCLASS = "org.h2.Driver";

    /**
     * 数据库连接池
     */
    private static LinkedList<Connection> pool = Lists.newLinkedList();

    /**
     * 初始化数据库连接池
     */
    public static void initConnectionPool(Integer initSize) {
        if (initSize <= 0) {
            throw new RuntimeException("连接池数量必须大于0");
        }
        for (int i = 0; i < initSize; i++) {
            try {
                pool.add(createConnection());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    /**
     * 创建链接
     *
     * @return
     * @throws SQLException
     */
    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection(JDBCURL, USER, PASSWORD);
        return conn;
    }


    /**
     * 获取链接
     *
     * @param time 指定获取链接时长 单位为毫秒
     * @return
     */
    public static Connection getConnection(long time) throws InterruptedException {
        synchronized (pool) {
            if (time <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
            } else {
                while (pool.isEmpty() && time > 0) {
                    pool.wait(time);
                }
            }
            if (!pool.isEmpty()) {
                return pool.remove();
            }
        }
        return null;
    }

    /**
     * 释放连接
     *
     * @param connection
     */
    public static void releaseConnection(Connection connection) {
        if (null != connection) {
            synchronized (pool) {
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }

}
