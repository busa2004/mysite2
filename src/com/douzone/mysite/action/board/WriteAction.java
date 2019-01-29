package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("---------------------------");
		
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/main/index.jsp");
			return;
		}
		
		
//		String fileName = request.getParameter("file");
//		System.out.println(fileName+"d==");
//		if(fileName != null) {
//        ServletContext context = request.getSession().getServletContext(); //어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨. 
//        String saveDir = context.getRealPath("Upload"); //절대경로를 가져옴
//        System.out.println("절대경로 >> " + saveDir);
//        int maxSize = 3*1024*1024;
//        String encoding = "UTF-8";
//        MultipartRequest multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
//		}
		
		
		
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		System.out.println(title+"d===");
		System.out.println(contents+"d===");
		int hit=0;
		int gno=new BoardDao().getMaxGno()+1;
		int ono=1;
		int depth=0;
		long userno = authUser.getNo();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setHit(hit);
		vo.setgNo(gno);
		vo.setoNo(ono);
		vo.setDepth(depth);
		vo.setUserNo(userno);

		new BoardDao().insert(vo);

		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
