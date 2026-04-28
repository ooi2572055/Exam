package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	
	private String baseSql =
		    "SELECT s.student_no, sub.subject_cd, sub.subject_name, t.num, t.point " +
		    "FROM student s " +
		    "JOIN test t ON s.student_no = t.student_no " +
		    "JOIN subject sub ON t.subject_cd = sub.subject_cd ";
	
	
	public List<TestListStudent> filter(Student student) throws Exception {

	    List<TestListStudent> list = new ArrayList<>();

	    Connection con = getConnection();

	    String sql = baseSql + "WHERE s.student_no = ?";

	    PreparedStatement st = con.prepareStatement(sql);
	    st.setString(1, student.getStudentNo());

	    ResultSet rs = st.executeQuery();

	    list = postFilter(rs);

	    st.close();
	    con.close();

	    return list;
	}
        
	
	private List<TestListStudent> postFilter(ResultSet rs) throws Exception {

	    List<TestListStudent> list = new ArrayList<>();

	    while (rs.next()) {
	        TestListStudent bean = new TestListStudent();

	        bean.setSubjectCd(rs.getString("subject_cd"));
	        bean.setSubjectName(rs.getString("subject_name"));
	        bean.setNum(rs.getInt("num"));
	        bean.setPoint(rs.getInt("point"));

	        list.add(bean);
	    }

	    return list;
	}
	
	
}