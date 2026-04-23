package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.Subject;

public class SubjectDAO {
    // データベース接続を取得するメソッド
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/book");
        return ds.getConnection();
    }
    
    // 全件取得する仕事（メソッド）
    public List<Subject> findAll() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM Subjects";

        // ここで getConnection() を呼び出す！
        try (Connection con = getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Subject bean = new Subject();
                bean.setSchool_cd(rs.getString("SCHOOL_CD"));
                bean.setSubject_cd(rs.getString("SUBJECT_CD"));
                bean.setSubject_name(rs.getString("SUBJECT_NAME"));
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}