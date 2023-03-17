package dao;

import entity.Student;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import utils.PageInfo;
import utils.PropertiesUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDao {
    //新增学生
    public void add(Student std) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "insert into std VALUES(null,?,?,?)";
        queryRunner.update(sql,std.getStdName(), std.getStdNo(),std.getStdPwd());
    }
    //删除学生信息
    public void delete(Integer stdId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "delete from std where stdId = ?";
        queryRunner.update(sql, stdId);
    }
    //更新学生信息
    public void update(Student std) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update  std set stdName = ?,stdNo = ? where stdId = ? ";
        queryRunner.update(sql, std.getStdName(),std.getStdNo(),std.getStdId());
    }
    public void update1(Student std) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update  std set stdPhone = ?, stdEmail = ? ,stdAddress = ? where stdId = ? ";
        queryRunner.update(sql, std.getStdPhone(),std.getStdEmail(),std.getStdAddress(),std.getStdId());
    }

    //分页DAO改造，直接传入封装好的pageInfo对象
    public PageInfo<Student> list(Student std, PageInfo<Student> pageInfo) throws Exception {
//        查询到的数量联动
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String _sql = "";
        //组合条件查询多用like，拼接查询
        List<Object> _list = new ArrayList<Object>();
        //        按照id查询
        if(std.getStdId() != null) {
            _sql += " and stdId = ?";
            _list.add(std.getStdId());
        }
        //        当前端输入学生姓名的时候
        if(StringUtils.isNoneBlank(std.getStdName())) {
            _sql += " and stdName like ?";
            _list.add("%"+std.getStdName()+"%");
        }
        //        当前端输入学生学号的时候
        if(StringUtils.isNoneBlank(std.getStdNo())) {
            _sql += " and stdNo like ?";
            _list.add("%"+std.getStdNo()+"%");
        }
        //_list转数组
        Object[] arr = new Object[_list.size()];
        for (int i=0;i<_list.size();i++) {
            arr[i] = _list.get(i);
        }
        String sql = "select * from std where 1=1 "+_sql+" limit  "+(pageInfo.getPageNo()-1)*pageInfo.getPageSize()+" , "+pageInfo.getPageSize();
//        System.out.println(sql);
//        System.out.println(Arrays.toString(arr));
        List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class),arr);

        pageInfo.setList(list);
        pageInfo.setTotalCount(this.count(std));
        return pageInfo;
    }
    //count方法 必须是Long类型Interger类型报错
    public Long count(Student std) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String _sql = "";
//        组合条件查询多用like，拼接查询
        List<Object> _list = new ArrayList<Object>();
//        按照id查询
        if(std.getStdId() != null) {
            _sql += " and stdId = ?";
            _list.add(std.getStdId());
        }
//        当前端输入学生姓名的时候
        if(StringUtils.isNoneBlank(std.getStdName())) {
            _sql += " and stdName like ?";
            _list.add("%"+std.getStdName()+"%");
        }
//        当前端输入学生学号的时候
        if(StringUtils.isNoneBlank(std.getStdNo())) {
            _sql += " and stdNo like ?";
            _list.add("%"+std.getStdNo()+"%");
        }
        //_list转数组
        Object[] arr = new Object[_list.size()];
        for (int i=0;i<_list.size();i++) {
            arr[i] = _list.get(i);
        }
        String sql = "select count(*) from std where 1=1 "+_sql;
        Long count = (Long)queryRunner.query(sql,new ScalarHandler(),arr);
        return count;
    }
    //通过id查找学生
    public Student findById(Integer sId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from std where stdid = ?";
        Student std = queryRunner.query(sql,new BeanHandler<>(Student.class),sId);
        return std;
    }
    //学生登录验证
    public Student login(String stdNo,String stdPwd) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from std where stdNo = ? and stdPwd = ?";
        Student std = queryRunner.query(sql,new BeanHandler<>(Student.class),stdNo,stdPwd);
        return std;
    }

    public void update(String pwd,Integer stuId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update  std set stdPwd = ? where stdId = ? ";
        queryRunner.update(sql, pwd,stuId);
    }

}
