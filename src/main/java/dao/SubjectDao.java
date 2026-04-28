package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
    /**
     * 学校に紐づく科目をフィルタリングして取得する
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();
        
        // ログインユーザーの学校コードで絞り込むSQL
        PreparedStatement pstmt = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?"
        );
        pstmt.setString(1, school.getSchoolCd());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Subject bean = new Subject();
            bean.setSubject_cd(rs.getString("SUBJECT_CD"));
            bean.setSubject_name(rs.getString("SUBJECT_NAME"));
            list.add(bean);
        }

        if (con != null) {
            con.close();
        }
        return list;
    }
}