<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			
			<%-- 変更ボタンを押した時の送信先（Action）を指定 --%>
			<form action="StudentUpdateExecute.action" method="post">
				
				<div class="mb-3">
					<label class="form-label">学生番号</label>
					<%-- 学生番号は変更されては困る（DBの主キーのため）ので、表示専用にする --%>
					<input type="text" class="form-control-plaintext" readonly value="${student.studentNo }">
					<%-- ただし、次のActionに学生番号を教える必要があるので、隠し項目（hidden）でこっそり送る --%>
					<input type="hidden" name="no" value="${student.studentNo }">
				</div>
				
				<div class="mb-3">
					<label class="form-label" for="student-name-input">氏名</label>
					<%-- value属性に ${student.studentName} を入れることで、最初から名前が入った状態にする --%>
					<input type="text" class="form-control" id="student-name-input" name="name" value="${student.studentName }" required>
				</div>
				
				<div class="mb-3">
					<label class="form-label" for="student-f1-select">入学年度</label>
					<select class="form-select" id="student-f1-select" name="ent_year">
						<%-- 簡易的に2014年から2034年までをループで出力 --%>
						<c:forEach var="year" begin="2014" end="2034">
							<%-- 学生の入学年度と一致する年だけ「selected（選択済み）」にする --%>
							<option value="${year }" <c:if test="${year == student.entYear }">selected</c:if>>${year }</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="mb-3">
					<label class="form-label" for="student-f2-select">クラス</label>
					<select class="form-select" id="student-f2-select" name="class_num">
						<%-- Actionから渡されたクラス一覧（class_num_set）をループ --%>
						<c:forEach var="num" items="${class_num_set }">
							<%-- 学生の所属クラスと一致するクラスだけ「selected」にする --%>
							<option value="${num }" <c:if test="${num == student.classNum }">selected</c:if>>${num }</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="mb-3 form-check">
					<%-- 学生が在学中の場合のみ、最初からチェックを入れる（checked） --%>
					<input type="checkbox" class="form-check-input" id="student-attend-check" name="is_attend" value="true" 
						   <c:if test="${student.isAttend() }">checked</c:if>>
					<label class="form-check-label" for="student-attend-check">在学中</label>
				</div>
				
				<button class="btn btn-primary" type="submit">変更</button>
				<a href="StudentList.action" class="btn btn-secondary">戻る</a>
			</form>
		</section>
	</c:param>
</c:import>