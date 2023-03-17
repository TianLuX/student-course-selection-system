package dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import entity.Sc;
import entity.Student;
import utils.PropertiesUtils;

public class ScDao {

//    支持批量保存
    public int[] add(List<Integer> cIdArray,Integer stdId) throws Exception {
        DataSource dataSource = PropertiesUtils.getDataSource();
//        从datasource中拿到数据库的连接，使用同一个数据库连接实现
        Connection connection = dataSource.getConnection();
//        因为jdbc的事务默认自动提交，setAutoCommit值为false时不让它自动提交
        connection.setAutoCommit(false);
//        需要先删除
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String _sql = "delete from sc where stdId = ?";
        queryRunner.update(connection,_sql, stdId);
//        第二维需要定义，只需要定义一次，否则会报错
        Object[][] object = new Object[cIdArray.size()][2];
        //批量保存需要二维数组，将cIdArray和stuID保存为二维数组
//        一个对象保存两个信息
        for(int i=0;i<cIdArray.size();i++) {
            object[i][0] = stdId;
            object[i][1] = cIdArray.get(i);
        }
        String sql = "insert into sc(stdId,cid) values(?,?)";
        int[] arr = queryRunner.batch(connection,sql, object);
        connection.commit();
//        当arr等于0时，表示更新失败，否则更新成功
        return arr;
    }
    //    支持批量保存
    public int[] add1(List<Integer> cIdArray,Integer stdId) throws Exception {
        DataSource dataSource = PropertiesUtils.getDataSource();
//        从datasource中拿到数据库的连接，使用同一个数据库连接实现
        Connection connection = dataSource.getConnection();
//        因为jdbc的事务默认自动提交，setAutoCommit值为false时不让它自动提交
        connection.setAutoCommit(false);
//        需要先删除
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String _sql = "delete from sc_copy where stdId = ?";
        queryRunner.update(connection,_sql, stdId);
//        第二维需要定义，只需要定义一次，否则会报错
        Object[][] object = new Object[cIdArray.size()][2];
        //批量保存需要二维数组，将cIdArray和stuID保存为二维数组
//        一个对象保存两个信息
        for(int i=0;i<cIdArray.size();i++) {
            object[i][0] = stdId;
            object[i][1] = cIdArray.get(i);
        }
        String sql = "insert into sc_copy(stdId,cid) values(?,?)";
        int[] arr = queryRunner.batch(connection,sql, object);
        connection.commit();
//        当arr等于0时，表示更新失败，否则更新成功
        return arr;
    }

    public void delete(Integer scId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "delete from sc where scId = ?";
        queryRunner.update(sql, scId);
    }
//   评分更新，不需要重复评分
    public void update(String[] stuIdArr,String[] scoreArr,Integer cId) throws Exception {
//        批量处理，使用同一个connection
        DataSource dataSource = PropertiesUtils.getDataSource();
        Connection connection = dataSource.getConnection();
//        自动提交设置为false
        connection.setAutoCommit(false);
        QueryRunner queryRunner = new QueryRunner(dataSource);
//        使用二维数组，第二维有三个，分别用于保存分数，课程id，学生id
        Object[][] objects = new Object[stuIdArr.length][3];
//        遍历循环学生
        for(int i=0;i<stuIdArr.length;i++) {
//            做分数判断，如果为空则是0
            objects[i][0] = Integer.parseInt(scoreArr[i]==null?"0":scoreArr[i]) ;
//            每次评分一次都是一个课程，同一个cid
            objects[i][1] = cId;
            objects[i][2] = Integer.parseInt(stuIdArr[i]);
        }
//        通过课程cid以及学生stdid去更新成绩
        String sql = "update sc set score = ? where cId = ? and stdId = ?";
//        batch批量更新
        queryRunner.batch(sql,objects);
        connection.commit();
    }
    //   评分更新，不需要重复评分
    public void update1(String[] stuIdArr,String[] scoreArr,Integer cId) throws Exception {
//        批量处理，使用同一个connection
        DataSource dataSource = PropertiesUtils.getDataSource();
        Connection connection = dataSource.getConnection();
//        自动提交设置为false
        connection.setAutoCommit(false);
        QueryRunner queryRunner = new QueryRunner(dataSource);
//        使用二维数组，第二维有三个，分别用于保存分数，课程id，学生id
        Object[][] objects = new Object[stuIdArr.length][3];
//        遍历循环学生
        for(int i=0;i<stuIdArr.length;i++) {
//            做分数判断，如果为空则是0
            objects[i][0] = Integer.parseInt(scoreArr[i]==null?"0":scoreArr[i]) ;
//            每次评分一次都是一个课程，同一个cid
            objects[i][1] = cId;
            objects[i][2] = Integer.parseInt(stuIdArr[i]);
        }
//        通过课程cid以及学生stdid去更新成绩
        String sql = "update sc_copy set score = ? where cId = ? and stdId = ?";
//        batch批量更新
        queryRunner.batch(sql,objects);
        connection.commit();
    }

    public List<Sc> list(Sc sc) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from sc";
        List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class));
        return list;
    }
//   通过调用学生已经选了的课，而返回list信息，防止重复选课
    public List<Sc> listByStuId(Integer stuId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from sc where stdId = ?";
        List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class),stuId);
        return list;
    }

    public Sc findById(Integer scId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from sc where SCID = ?";
        Sc sc = queryRunner.query(sql,new BeanHandler<>(Sc.class),scId);
        return sc;
    }
//    学生选课信息，选课中的stdid和学生中的stdid相等，两个表关联查询，返回student的list信息
//    添加学生score属性，便于直接返回分数
    public List<Student> listStudentByCId(Integer cId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select std.*,score from sc,std where sc.stdId = std.stdId and  cId = ?";
        List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class),cId);
        return list;
    }
    public List<Student> listStudentByCId1(Integer cId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select std.*,score from sc_copy,std where sc_copy.stdId = std.stdId and  cId = ?";
        List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class),cId);
        return list;
    }
//    成绩区间查询Dao方法，不需要参数，通过60、70、80、90进行分档
    public List<Map<String, Object>> query_range() throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
//        ifnull如果为空，则是0
//        拼接的时候左侧需要有空格
        String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best" +
                " from course" +
                " left join (" +
                " select cid,count(*) bad from sc where score<60 group by cid" +
                " ) A on course.cid = A.cid" +
                " left join (" +
                " select cid,count(*) common from sc where score>=60 and score<=70 group by cid" +
                " ) B on  course.cid = B.cid" +
                " left join(" +
                " select cid,count(*) good from sc where score>70 and score<=85 group by cid" +
                " ) C on course.cid = C.cid" +
                " left join (" +
                " select cid,count(*) best from sc where score>85 and score<=100 group by cid" +
                " ) D on course.cid =D.cid ";

        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
        return list;
    }


//    及格率查询方法
    public List<Map<String, Object>> query_jgl() throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
//        子查询select cname from course where A.cid = course.cid，通过cname直接把没有打分的过滤掉
//        round函数百分率round(jgnum/allnum,2)*100，保留两位小数
//        jgnum及格人数allnum总人数
        String sql = "select A.cid,(" +
                " select cname from course where A.cid = course.cid " +
                " ) cname,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from (" +
                " select cid, count(*) jgnum from sc where score>=60 group by cid " +
                " ) A,(" +
                " select cid, count(*) allnum from sc group by cid " +
                " ) B where A.cid = B.cid ";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
        return list;
    }
//   老师所教科目的分数区间
    public List<Map<String, Object>> query_rangeByTid(Integer tId) throws Exception {
//        与管理员查询结果相比，多一个where tid = ？
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best" +
                " from course" +
                " left join (" +
                " select cid,count(*) bad from sc where score<60 group by cid" +
                " ) A on course.cid = A.cid" +
                " left join (" +
                " select cid,count(*) common from sc where score>=60 and score<=70 group by cid" +
                " ) B on  course.cid = B.cid" +
                " left join(" +
                " select cid,count(*) good from sc where score>70 and score<=85 group by cid" +
                " ) C on course.cid = C.cid" +
                " left join (" +
                " select cid,count(*) best from sc where score>85 and score<=100 group by cid" +
                " ) D on course.cid =D.cid where tid = ?";

        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(),tId);
        return list;
    }
    //   老师所教科目的及格率情况
    public List<Map<String, Object>> query_jglByTid(Integer tId) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        //与管理员查询结果相比，多一个where tid = ？
        String sql = "select * from course,(" +
                " select A.cid,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from (" +
                " select cid, count(*) jgnum from sc where score>=60 group by cid " +
                " ) A,(" +
                " select cid, count(*) allnum from sc group by cid " +
                " ) B where A.cid = B.cid" +
                " ) C where course.cid = C.cid " +
                " and tid = ?";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(),tId);
        return list;
    }
    public List<Map<String, Object>> top5() throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select stdId,(select stdName from std where std.stdId = sc.stdId ) stdName,sum(score) sumx from sc group by stdId order by sumx desc  limit 0,5";
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
        return list;
    }

}
