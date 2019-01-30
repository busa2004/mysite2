package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

public class CommentAction implements Action {

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
		String contents = request.getParameter("contents");
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		if(contents.equals("")||contents.equals(null)) {
			WebUtils.redirect(request, response, request.getContextPath()+"/board?a=viewform&no="+boardNo);
			return;
		}
		long authUserNo = Long.parseLong(request.getParameter("authUserNo"));
		CommentVo vo = new CommentVo();
		vo.setBoardNo(boardNo);
		vo.setUserNo(authUserNo);
		vo.setContents(contents);
	
		new CommentDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=viewform&no="+boardNo);
		
	}

}
