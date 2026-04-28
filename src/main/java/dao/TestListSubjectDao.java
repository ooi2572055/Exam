package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql = "select s.ent_year, s.student_no, s.student_name, s.class_num, t.no as test_no, t.point "
			+ "from student s left join test t "
			+ "on s.student_no = t.student_no and s.school_cd = t.school_cd and t.subject_cd = ? "
			+ "where s.school_cd = ? and s.ent_year = ? and s.class_num = ? and s.is_attend = true "
			+ "order by s.student_no, t.no";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

		Map<String, TestListSubject> map = new LinkedHashMap<>();
		try {
			while (rSet.next()) {
				String studentNo = rSet.getString("student_no");
				TestListSubject t = map.get(studentNo);
				if (t == null) {
					t = new TestListSubject();
					// インスタンスに検索結果をセット
					t.setEntYear(rSet.getInt("ent_year"));
					t.setStudentNo(studentNo);
					t.setStudentName(rSet.getString("student_name"));
					t.setClassNum(rSet.getString("class_num"));
					map.put(studentNo, t);
				}
				// 回数を取得
				int testNo = rSet.getInt("test_no");
				// テストが存在する場合のみ点数をセット
				if (!rSet.wasNull()) {
					t.putPoint(testNo, rSet.getInt("point"));
				}
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(map.values());
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement(baseSql);
			
			statement.setString(1, subject.getSubject_cd());
			
			statement.setString(2, school.getSchoolCd());
			
			statement.setInt(3, entYear);
			
			statement.setString(4, classNum);
			// 実行
			rSet = statement.executeQuery();
			// リストへの格納処理
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// 閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}