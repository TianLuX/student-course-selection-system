package dao;

import entity.Course;
import entity.Teacher;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import utils.PageInfo;
import utils.PropertiesUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CourseDao {
    public void add(Course course) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "insert into course(cname,tid,ctime) values(?,?,?)";
        queryRunner.update(sql, course.getcName(), course.getTeacher().gettId(),course.getcTime());
    }

    public void delete(Integer cid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "delete from course where cid = ?";
        queryRunner.update(sql, cid);
    }

    public void update(Course course) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "update course set cname = ?,tid = ?,ctime = ? where cid = ?";
//        先取得getTeacher再取tid
        queryRunner.update(sql, course.getcName(), course.getTeacher().gettId(),course.getcTime(), course.getcId());
    }

//    课程查询，分页dao改造，和学生、老师中一样
    public PageInfo<Course> list(Course course, PageInfo<Course> pageInfo) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String _sql = "";
        List<Object> _list = new ArrayList<Object>();
//        通过id查询
        if(course != null && course.getcId() != null) {
            _sql += " and CID = ?";
            _list.add(course.getcId());
        }
//        使用like模糊查询
//        通过课程名称查询
        if(course != null && StringUtils.isNoneBlank(course.getcName())) {
            _sql += " and CNAME like ?";
            _list.add("%"+course.getcName()+"%");
        }
//        通过老师名称查询
        if(course != null && StringUtils.isNoneBlank(course.getTeacher().gettName())) {
            _sql += " and tname like ?";
            _list.add("%"+course.getTeacher().gettName()+"%");
        }
//        通过老师账号查询
        if(course != null && StringUtils.isNoneBlank(course.getTeacher().getUserName())) {
            _sql += " and USERNAME like ?";
            _list.add("%"+course.getTeacher().getUserName()+"%");
        }
        //通过老师id查询
        if(course != null && course.getTeacher().gettId() != null) {
            _sql += " and course.tid  = ?";
            _list.add(course.getTeacher().gettId());
        }
        if(course != null && StringUtils.isNoneBlank(course.getcTime())) {
            _sql += " and course.ctime  = ?";
            _list.add(course.getcTime());
        }
        //_list转数组
        Object[] arr = new Object[_list.size()];
        for (int i=0;i<_list.size();i++) {
            arr[i] = _list.get(i);
        }
        //TODO
        String sql = "select course.*,tech.tname,tech.userName from course,tech where course.tid = tech.tId "+_sql+" limit  "+(pageInfo.getPageNo()-1)*pageInfo.getPageSize()+" , "+pageInfo.getPageSize();
//        System.out.println(sql);
//        System.out.println(Arrays.toString(arr));
        List<Map<String, Object>> Maplist = queryRunner.query(sql, new MapListHandler(),arr);
        //设置list集合
        List<Course> list = new ArrayList<>();
        //通过for：each将list<Map>手动转换为list<Course>
        for (Map map :Maplist) {
            Course course1 = new Course();
            course1.setcId(Integer.parseInt(map.get("cId")+""));
            course1.setcName(map.get("cName")+"");
            Teacher teacher = new Teacher();
            teacher.settId(Integer.parseInt(map.get("tId")+""));
            teacher.settName(map.get("tName")+"");
            teacher.setUserName(map.get("userName")+"");
            course1.setTeacher(teacher);
            course1.setcTime(map.get("cTime")+"");
            list.add(course1);
        }
        pageInfo.setList(list);
        pageInfo.setTotalCount(this.count(course));
        return pageInfo;
    }

//    类似于list方法，返回总数
    public Long count(Course course) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String _sql = "";
        List<Object> _list = new ArrayList<Object>();
        if(course != null && course.getcId() != null) {
            _sql += " and CID = ?";
            _list.add(course.getcId());
        }
        if(course != null && StringUtils.isNoneBlank(course.getcName())) {
            _sql += " and CNAME like ?";
            _list.add("%"+course.getcName()+"%");
        }
        if(course != null && course.getTeacher().gettName() != null) {
            _sql += " and tname = ?";
            _list.add(course.getTeacher().gettName());
        }
//        查询tid，在教师评分时使用
        if(course != null && course.getTeacher().gettId() != null) {
            _sql += " and course.tid  = ?";
            _list.add(course.getTeacher().gettId());
        }
        if(course != null && StringUtils.isNoneBlank(course.getcTime())) {
            _sql += " and CTime like ?";
            _list.add("%"+course.getcTime()+"%");
        }
        //_list转数组
        Object[] arr = new Object[_list.size()];
        for (int i=0;i<_list.size();i++) {
            arr[i] = _list.get(i);
        }
        String sql = "select count(*) from course,tech where course.tid = tech.tId "+_sql;
        Long count = (Long)queryRunner.query(sql, new ScalarHandler(),arr);
        return count;
    }

//    查找课程的id
    public Course findById(Integer cid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
        String sql = "select * from course where cid = ?";
//        使用map，再将map转化成course
        Map<String, Object> map = queryRunner.query(sql, new MapHandler(),cid);
        Course course = new Course();
        course.setcId(Integer.parseInt(map.get("cId")+""));
        course.setcName(map.get("cName")+"");
        //二次查询老师信息设置关联
        Integer tid = Integer.parseInt(map.get("tId")+"");
//        通过teacherDao查询老师的id，返回teacher对象
        Teacher teacher =DaoFactory.getInstance().getTeacherDao().findById(tid);
        course.setTeacher(teacher);
        course.setcTime(map.get("cTime")+"");
        return course;
    }
}
