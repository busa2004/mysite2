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

public class AjaxDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");

		String no = request.getParameter("no");
		String password = request.getParameter("password");


		GuestBookVo vo = new GuestBookVo();
		vo.setNo(Long.parseLong(no));
		vo.setPassword(password);
		
		
		boolean passwordCheck = new GuestBookDao().select(vo);
		
		if(passwordCheck) {
			new GuestBookDao().delete(vo);
		}
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data",passwordCheck);
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(map);
		response.getWriter().print(jsonObject.toString());

	}

}
