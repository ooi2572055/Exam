<%-- 学生変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
            <div class="alert alert-success">
                学生情報の変更が完了しました。
            </div>
            <a href="StudentList.action">学生一覧へ戻る</a>
        </section>
    </c:param>
</c:import>