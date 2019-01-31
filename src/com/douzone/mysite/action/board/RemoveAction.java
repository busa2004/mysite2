package com.douzone.mysite.action.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
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
		
		 String fileName = vo.getFileName(); //지울 파일명
		 String root = request.getSession().getServletContext().getRealPath("/Upload/");
 	    String filePath = root + fileName;
		 
		 
		 
		   
		   File f = new File(filePath); // 파일 객체생성
		   if( f.exists()) f.delete(); // 파일이 존재하면 파일을 삭제한다.
		
		   new CommentDao().deleteAll(no);
		   
		   
		for(BoardVo v : list) {
			if(v.getgNo() == vo.getgNo() && v.getDepth()>vo.getDepth()) {
				new BoardDao().updateCheck(no);
				WebUtils.redirect(request, response, request.getContextPath()+"/board");
				return;
			}
		}
		
		new BoardDao().remove(no);
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
