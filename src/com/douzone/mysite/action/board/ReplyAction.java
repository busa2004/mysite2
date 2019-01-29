package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

public class ReplyAction implements Action {

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
		long no =Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().getVo(no);
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		long userno = authUser.getNo();
		
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setHit(0);
		vo.setoNo(new BoardDao().getMaxono(vo.getgNo())+1);
		vo.setDepth(vo.getDepth()+1);
		vo.setUserNo(userno);

		new BoardDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
