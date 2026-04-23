package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定 1
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        String student_no = ""; // 画面から送られてくる学生番号
        
        // 使用するDAOなどの準備
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // リクエストパラメーターの取得 2
        // URLの末尾（?no=〇〇）にくっついてきた学生番号を取得
        student_no = req.getParameter("no");

        // DBからデータ取得 3
        // ①変更対象の学生データを1件取得する（新規登録の重複チェックで作ったメソッドの再利用！）
        Student student = studentDao.get(student_no);
        
        // ②変更画面のプルダウン用に、ログイン中の先生の学校のクラス一覧を取得する
        List<String> list = classNumDao.filter(teacher.getSchool());

        // レスポンス値をセット 6
        // 取得した学生データとクラス一覧を、JSPに渡すためにリクエストにセット
        req.setAttribute("student", student);
        req.setAttribute("class_num_set", list);

        // JSPへフォワード 7
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}