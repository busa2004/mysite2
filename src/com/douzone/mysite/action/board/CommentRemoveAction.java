package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;

public class CommentRemoveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		long no = Long.parseLong(request.getParameter("no"));
		new CommentDao().delete(no);
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=viewform&no="+boardNo);
	}

}
