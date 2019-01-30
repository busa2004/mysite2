package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.UserVo;

public class CommentRemoveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/main/index.jsp");
			return;
		}
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		long no = Long.parseLong(request.getParameter("no"));
		new CommentDao().delete(no);
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=viewform&no="+boardNo);
	}

}
