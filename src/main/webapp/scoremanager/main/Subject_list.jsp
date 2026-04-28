<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目一覧</h2>
            
            <div class="my-3 text-end">
                <a href="SubjectCreate.action" class="btn btn-primary">新規登録</a>
            </div>

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>科目コード</th>
                        <th>科目名</th>
                        <th class="text-center">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="subject" items="${subjects}">
                        <tr>
                            <td>${subject.subject_cd}</td>
                            <td>${subject.subject_name}</td>
                            <td class="text-center">
                                <a href="SubjectUpdate.action?subject_cd=${subject.subject_cd}" class="btn btn-outline-success btn-sm">編集</a>
                                <a href="SubjectDelete.action?subject_cd=${subject.subject_cd}" class="btn btn-outline-danger btn-sm">削除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${empty subjects}">
                <div class="alert alert-info">登録されている科目がありません。</div>
            </c:if>

            <div class="mt-4">
                <a href="Menu.action" class="btn btn-secondary">メニューに戻る</a>
            </div>
        </section>
    </c:param>
</c:import>