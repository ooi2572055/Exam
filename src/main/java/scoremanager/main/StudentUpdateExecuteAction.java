package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションからログイン中の先生情報を取得（学校コードをセットするため）
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. リクエストパラメータの取得（画面の入力値を受け取る）
        String studentNo = req.getParameter("no");
        String studentName = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        
        // チェックボックス特有の処理：
        // チェックされていれば "true" が送られてくる。チェックが外されていれば null になる。
        String isAttendStr = req.getParameter("is_attend");
        boolean isAttend = (isAttendStr != null); 

        // 3. Beanに新しいデータをセットする
        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setStudentName(studentName);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        // 更新用SQLで必要なため、先生の持っている学校コードもセットしておく
        student.setSchool(teacher.getSchool());

        // 4. DAOを呼び出してデータベースを更新（UPDATE）する
        StudentDao studentDao = new StudentDao();
        studentDao.save(student);

        // 5. 完了画面（JSP）へフォワード
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}