package com.douzone.mysite.action.guest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

import net.sf.json.JSONObject;

public class AjaxInsertAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String message = request.getParameter("message");
		String password = request.getParameter("password");
		
		GuestBookVo vo = new GuestBookVo();
		vo.setName(name);
		vo.setMessage(message);
		vo.setPassword(password);
		GuestBookDao dao = new GuestBookDao();

		
		
		dao.insert(vo);
		//long no = dao.getId();
		GuestBookVo newVo = dao.get();
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data",newVo);
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(map);
		response.getWriter().print(jsonObject.toString());
	}
}
