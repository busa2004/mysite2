package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.CommentDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

public class viewFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");

		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardDao dao = new BoardDao();
		  dao.update(no);//조회수증가
		BoardVo vo = dao.getVo(no);
		
		request.setAttribute("vo", vo);
		
		
		List<CommentVo> commentList = new CommentDao().getList(no);
		request.setAttribute("commentList",commentList);
		
		
		

		
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
