package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// 1. パスを取得 (例: scoremanager/main/SubjectList.action)
			String path = req.getServletPath().substring(1);

			// 2. クラス名に変換 (例: scoremanager.main.SubjectListAction)
			// スラッシュをドットに、.action を Action という文字に置き換えています
			String name = path.replace(".a", "A").replace('/', '.');

			// 3. 変換した名前（name）を使ってクラスを読み込み、インスタンス化
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// 4. アクションを実行
			action.execute(req, res);

		} catch (Exception e) {
			e.printStackTrace();
			// もしエラーが出たら、ブラウザに直接エラー内容を表示するようにします（デバッグ用）
			res.setContentType("text/plain; charset=UTF-8");
			res.getWriter().println("FrontControllerでエラーが発生しました:");
			e.printStackTrace(res.getWriter());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}