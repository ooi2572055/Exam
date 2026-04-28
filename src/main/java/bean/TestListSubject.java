package bean;

import java.util.HashMap;
import java.util.Map;

/**
 * クラス別成績一覧情報を表す Bean 
 */
public class TestListSubject {

	/** 入学年度 */
	private int entYear;

	/** 学籍番号 */
	private String studentNo;

	/** 氏名 */
	private String studentName;

	/** クラス番号 */
	private String classNum;

	/** 点数 */
	private Map<Integer, Integer> points = new HashMap<>();

	//getter / setter 

	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key) {
		Integer value = points.get(key);
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public void putPoint(int key, int value) {
		points.put(key, value);
	}
}