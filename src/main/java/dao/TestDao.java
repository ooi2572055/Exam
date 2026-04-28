package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = new Test();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            
            statement = connection.prepareStatement("SELECT * FROM test WHERE school_cd = ?");
            statement.setString(1, school.getSchoolCd());
            statement.setString(2, student.getStudentNo());
            statement.setString(3, subject.getSubject_cd());
            statement.setInt(4, no);

            ResultSet rSet = statement.executeQuery();
            // postFilterを呼び出してリストを取得
            List<Test> list = postFilter(rSet, school);

            if (!list.isEmpty()) {
                test = list.get(0);
            } else {
                test = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return test;
    }

    /**
     * ResultSetからTestのリストを作成する非公開メソッド
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                Test test = new Test();
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                // 必要に応じて学生や科目の情報をセット
                list.add(test);
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    /**
     * 条件に応じたテストリストを取得するメソッド
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(" AND ent_year = ? AND class_num = ? AND subject_cd = ?");
            statement.setString(1, school.getSchoolCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subject.getSubject_cd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * テストリストを一括保存するメソッド
     */
    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();
        boolean result = true;

        try {
            connection.setAutoCommit(false); // トランザクション開始
            for (Test test : list) {
                // 内部のsaveメソッドを呼び出し
                if (!save(test, connection)) {
                    result = false;
                    break;
                }
            }
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * 単体のテスト情報を保存する非公開メソッド
     */
    private boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        int count = 0;

        try {
            // 更新または挿入の処理（例として更新）
            statement = connection.prepareStatement( "UPDATE test SET point = ? WHERE student_id = ? AND subject_cd = ? AND no = ?");
            statement.setInt(1, test.getPoint());
            statement.setString(2, test.getStudent().getStudentNo());
            statement.setString(3, test.getSubject().getSubject_cd());
            statement.setInt(4, test.getNo());

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return count > 0;
    }
}