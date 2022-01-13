package com.august.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;


import java.io.InputStream;
import java.sql.Connection;

import java.util.Properties;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-11-28   21:01
 */
public class JdbcUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    //初始化
    static{
        try {
            //读取配置文件
            //方式一
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //方式二
//            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            //从流中加载数据
            pros.load(is);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据库连接池的连接
      * @return null 连接失败 ,conn
     */
    public static Connection getConnection(){
        /**
         * 方案一(没有考虑到回滚数据)
         */
//        DruidPooledConnection conn = null;
//        try {
//            conn = dataSource.getConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 方案二(考虑上回滚数据)
         */
        //从ThreadLocal对象中获取连接
        Connection conn =  conns.get();
        if(conn == null){
            try {
                //从数据库连接池获取连接
                conn = dataSource.getConnection();
                //关闭自动提交
                conn.setAutoCommit(false);
                //保存到ThreadLocal对象中,确保使用同一个连接
                conns.set(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

//    /**
//     * 关闭数据库连接(方案一)
//     * @param conn
//     */
//    public  static void close(Connection conn){
//        if(conn != null){
//            try {
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 提交事物并关闭数据库连接(方案二)
     */
    public static void commitAndClose(){
        Connection conn = conns.get();
        if(conn != null){
            try {
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错。(因为Tomcat服务器底层使用了线程池技术)
        conns.remove();
    }
    /**
     * 回滚事物并关闭数据库连接(方案二)
     */
    public static void RollbackAndClose(){
        Connection conn = conns.get();
        if(conn != null){
            try {
                conn.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错。(因为Tomcat服务器底层使用了线程池技术)
        conns.remove();
    }


}
