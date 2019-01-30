package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

public class RemoveAction implements Action {

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
		long no = Long.parseLong( request.getParameter("no"));
		BoardVo vo = new BoardDao().getVo(no);
		List<BoardVo> list = new BoardDao().getList();
		for(BoardVo v : list) {
			if(v.getgNo() == vo.getgNo() && v.getDepth()>vo.getDepth()) {
				new CommentDao().deleteAll(no);
				new BoardDao().updateCheck(no);
				WebUtils.redirect(request, response, request.getContextPath()+"/board");
				return;
			}
		}
		new CommentDao().deleteAll(no);
		new BoardDao().remove(no);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
