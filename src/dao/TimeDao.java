package dao;

import entity.Student;
import entity.Time;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.PropertiesUtils;

public class TimeDao {
    public void add(Time time) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "insert into Time(start,end) values(?,?)";
        queryRunner.update(sql, time.getStart(),time.getEnd());
    }
    public Time findNew() throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql="select * from time order by id desc LIMIT 1";
        Time time = (Time) queryRunner.query(sql,new BeanHandler(Time.class));
        return time;
    }
}
