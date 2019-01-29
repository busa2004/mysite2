package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.CommentVo;

public class CommentAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		long authUserNo = Long.parseLong(request.getParameter("authUserNo"));
		String contents = request.getParameter("contents");
		CommentVo vo = new CommentVo();
		vo.setBoardNo(boardNo);
		vo.setUserNo(authUserNo);
		vo.setContents(contents);
	
		new CommentDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=viewform&no="+boardNo);
		
	}

}
