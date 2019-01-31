package com.douzone.mysite.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.util.WebUtils;
import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
@WebServlet("/UploadService")
public class UploadService extends HttpServlet {
     /**
	 * 
	 */
	private static final long serialVersionUID = -8854810457148055637L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVo authUser=null;
		HttpSession session = request.getSession();
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/main/index.jsp");
			return;
		}
		
		
		
		
		
		ServletContext context = getServletContext(); //어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨. 
          String saveDir = context.getRealPath("/Upload"); //절대경로를 가져옴
          System.out.println("절대경로 >> " + saveDir);
          
          File Folder = new File(saveDir);

        	// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        	if (!Folder.exists()) {
        		try{
        		    Folder.mkdir(); //폴더 생성합니다.
        		    System.out.println("폴더가 생성되었습니다.");
        	        } 
        	        catch(Exception e){
        		    e.getStackTrace();
        		}        
                 }else {
        		System.out.println("이미 폴더가 생성되어 있습니다.");
        	}
          
          
          
          int maxSize = 3*1024*1024;
          String encoding = "UTF-8";
          MultipartRequest multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
          String fileName = multi.getFilesystemName("file");
          if(fileName==null) {
        	  fileName="";
          }
          
      	


          
          System.out.println(fileName);
          String div = multi.getParameter("a"); 
          String title = multi.getParameter("title");
  		  String contents = multi.getParameter("content");
  		  if(title.equals("")||title.equals(null)||contents.equals("")||contents.equals(null)) {
  			WebUtils.redirect(request, response, "/mysite2/board");
  			return;
  		  }
  		  long userno = authUser.getNo();
          
          
  		BoardVo vo = null; 
        if("write".equals(div)) {
  		int gno=new BoardDao().getMaxGno()+1;
  		int ono=1;
  		int depth=0;
  		
  		vo = new BoardVo();
  		vo.setgNo(gno);
  		vo.setoNo(ono);
  		vo.setDepth(depth);
  		
  		
        }  else if("reply".equals(div)) {
  		long no =Long.parseLong(multi.getParameter("no"));
		vo = new BoardDao().getVo(no);
		vo.setoNo(new BoardDao().getMaxono(vo.getgNo())+1);
		vo.setDepth(vo.getDepth()+1);
        }
        vo.setFileName(fileName);
        vo.setHit(0);
        vo.setTitle(title);
  		vo.setContents(contents);
  		vo.setUserNo(userno);
  		new BoardDao().insert(vo);
		
  		request.setAttribute("filePath",saveDir+"\\"+vo.getFileName());
  		System.out.println("--------"+saveDir+"\\"+vo.getFileName());
          WebUtils.redirect(request, response, "/mysite2/board");

     }
}
