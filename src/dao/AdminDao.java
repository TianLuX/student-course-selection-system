package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entity.Admin;
import utils.PropertiesUtils;


public class AdminDao {

    public void add(Admin admin) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "insert into admin(userName,pwd,name) values(?,?,?)";
        queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getName());
    }

    public void delete(Integer id) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "delete from admin where id = ?";
        queryRunner.update(sql, id);
    }

    public void update(Admin admin) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update admin set userName = ?,pwd = ?,name = ? where id = ?";
        queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getName(), admin.getId());
    }

    public void update(String pwd,Integer stuId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update  admin set pwd = ? where stdId = ? ";
        queryRunner.update(sql, pwd,stuId);
    }

    public List<Admin> list(Admin admin) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from admin";
        List<Admin> list = queryRunner.query(sql, new BeanListHandler<>(Admin.class));
        return list;
    }

    public Admin findById(Integer id) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from admin where id = ?";
        Admin admin = queryRunner.query(sql,new BeanHandler<>(Admin.class),id);
        return admin;
    }
    //管理员登录方法
    public Admin login(Admin myAdmin) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from admin where username = ? and pwd = ?";
        Admin admin = queryRunner.query(sql, new BeanHandler<>(Admin.class),myAdmin.getUserName(),myAdmin.getPwd());
        return admin;
    }

}
