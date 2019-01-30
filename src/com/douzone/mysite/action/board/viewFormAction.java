package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
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

		String no =request.getParameter("no");
		
		BoardDao dao = new BoardDao();

		BoardVo vo = dao.getVo(Long.parseLong(no));
		
		request.setAttribute("vo", vo);
		
		
		List<CommentVo> commentList = new CommentDao().getList( Long.parseLong(no));
		request.setAttribute("commentList",commentList);
		//ServletContext context = request.getSession().getServletContext(); //어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨. 
       // String saveDir = context.getRealPath("Upload");
        if(vo.getFileName()!=null) {
		request.setAttribute("filePath","Upload/"+vo.getFileName());
		request.setAttribute("fileName",vo.getFileName());
        }
		System.out.println("Upload/"+vo.getFileName());
		
		
		 // 쿠키값 가져오기
	    Cookie[] cookies = request.getCookies() ;
	     
	    if(cookies != null){
	         
	        for(int i=0; i < cookies.length; i++){
	            Cookie c = cookies[i] ;
	             
//	            // 저장된 쿠키 이름을 가져온다
//	            String cName = c.getName();
	             
	            // 쿠키값을 가져온다
	            String cValue = c.getValue() ;
	             if(cValue.equals(no)) {
	            	 WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	            	 System.out.println("쿠키 있음");
	            	 return;
	             }
	             
	        }
	    }


		 
	    Cookie c = new Cookie(no, no) ;
	     
	    // 쿠키에 설명을 추가한다
	    c.setComment("게시물 번호") ;
	     
	    // 쿠키 유효기간을 설정한다. 초단위 : 60*60*24= 1일
	    c.setMaxAge(60*60*24) ;
	     
	    // 응답헤더에 쿠키를 추가한다.
	    response.addCookie(c) ;

	    
		  dao.update(Long.parseLong(no));//조회수증가
		  System.out.println("쿠키 없음");
		  WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
		
		
		
	}

}
