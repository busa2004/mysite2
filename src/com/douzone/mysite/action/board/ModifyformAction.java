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

public class ModifyformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		
		long no =Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().getVo(no);
		if(authUser == null || authUser.getNo() != vo.getUserNo()) {
			WebUtils.redirect(request, response, request.getContextPath()+"/board");
			return;
		}
		
		
		request.setAttribute("vo", vo);

		WebUtils.forward(request, response, "/WEB-INF/views/board/modify.jsp");
				
	}

}
