package day06.jdbcdemo;

import com.google.common.collect.Lists;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

/**
 * JDBCDemo实现
 */
public class JDBCDemo {

    public static void main(String[] args) throws InterruptedException, SQLException {
        //Statement Demo
        statementDemo();
        System.out.println("=================================");
        preparedStatementDemo();
    }

    private static void preparedStatementDemo() throws InterruptedException, SQLException {
        System.out.println("********PREPAREDSTATEMENT START********");
        Connection connection = ConnectionPool.getConnection(300);
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS t_user");
        stmt.execute("CREATE TABLE t_user(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100))");
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_user values(?,?)");
        LinkedList<String> uuidStrs = Lists.newLinkedList();
        for (int i=0; i<5; i++){
            String uuidStr = UUID.randomUUID().toString();
            uuidStrs.add(uuidStr);
            preparedStatement.setString(1, uuidStr);
            preparedStatement.setString(2, "百珏");
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        //查询
        System.out.println("新增后查询");
        ResultSet rs2 = stmt.executeQuery("select * from t_user");
        //遍历结果集
        while (rs2.next()) {
            System.out.println(rs2.getString("id") + "," + rs2.getString("name"));
        }
        PreparedStatement updateStatement = connection.prepareStatement("update t_user set name='BAIJUE' where id=?");
        for (String uuidStr : uuidStrs) {
            updateStatement.setString(1, uuidStr);
            updateStatement.addBatch();
        }
        updateStatement.executeBatch();
        System.out.println("修改后查询");
        ResultSet rs = stmt.executeQuery("select * from t_user");
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        PreparedStatement deleteStatement = connection.prepareStatement("delete from t_user where id=?");
        for (String uuidStr : uuidStrs) {
            deleteStatement.setString(1, uuidStr);
            deleteStatement.addBatch();
        }
        deleteStatement.executeBatch();
        connection.commit();
        System.out.println("********PREPAREDSTATEMENT END********");

    }


    private static void statementDemo() throws InterruptedException, SQLException {
        System.out.println("********STATEMENT START********");
        //初始化数据库连接池
        ConnectionPool.initConnectionPool(1);
        Connection connection = ConnectionPool.getConnection(300);
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS t_user");
        stmt.execute("CREATE TABLE t_user(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100))");
        String uuid = UUID.randomUUID().toString();
        System.out.println("新增");
        stmt.executeUpdate("insert into t_user  values('" + uuid + "','百珏')");
        //查询
        ResultSet rs = stmt.executeQuery("select * from t_user");
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        System.out.println("修改");
        stmt.executeUpdate("update t_user set name='BAIJUE' where id='" + uuid + "' ");
        //查询
        ResultSet rs2 = stmt.executeQuery("select * from t_user");
        //遍历结果集
        while (rs2.next()) {
            System.out.println(rs2.getString("id") + "," + rs2.getString("name"));
        }
        //删除
        stmt.executeUpdate("delete from t_user where id='" + uuid + "'");
        stmt.execute("DROP TABLE IF EXISTS t_user");
        //释放资源
        stmt.close();
        ConnectionPool.releaseConnection(connection);
        System.out.println("********STATEMENT END********");
    }
}
