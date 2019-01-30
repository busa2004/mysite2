package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;



public class ListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<BoardVo> list =null;
		int page = 0 ;
		int movePage = 0; 
		int move =  0;
		int totalSize;
		String search =null;
		BoardDao dao = new BoardDao();
		if( request.getParameter("kwd") !=null)
			search = request.getParameter("kwd");
		if( request.getParameter("page") !=null)
		 page = Integer.parseInt(request.getParameter("page"))-1;
		if( request.getParameter("movePage") !=null)
		 movePage =  Integer.parseInt(request.getParameter("movePage"));
		if( request.getParameter("move") !=null)
		 move =  Integer.parseInt(request.getParameter("move"));
		
		
		if(search != "" && search != null) {
			 list = dao.getList(page*10,10,search);
			 totalSize =dao.getList(search).size();
		}else {
			list = dao.getList(page*10,10);
			totalSize =dao.getList().size();
		}
		
		int totalPage = (int)(totalSize/10);
		if(totalSize%10 != 0) {
			totalPage+=1;
		}
		
		System.out.println("==========================" + movePage);
		if(move == -1 && movePage+1 <= 0 ) {
			System.out.println("==1");
			move=0;
		}
		
		// || move == 1 && movePage+5 > totalPage
		System.out.println(list.size());
		movePage+=move;
		if(movePage+1 <= 0) {
			System.out.println("==2");
			movePage = 0;
		}
		else if(movePage+5 > totalPage) {
			System.out.println("==3");
			movePage-=move;
		}
		// 데이터를 request 범위에 저장
//		for(int i = 0 ; i < list.size();i++) {
//			System.out.println("----------");
//			System.out.println(list);
//			System.out.println("----------");
//		}
		request.setAttribute("list", list);
		request.setAttribute("listSize", list.size());
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("totalSize", totalSize);
		request.setAttribute("movePage",movePage) ;
		request.setAttribute("kwd",search) ;
		System.out.println(page+" "+totalPage+" "+ movePage+" " + move);
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}
}
