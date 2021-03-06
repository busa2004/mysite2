package com.douzone.mysite.action.guest;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;


public class GuestBookActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		}else if("delete".equals(actionName)) {
			action = new DeleteAction();
		}else if("add".equals(actionName)) {
			action = new AddAction();
		}else if("ajax".equals(actionName)) {
			action = new AjaxAction();
		}else if("ajax-list".equals(actionName)) {
			action = new AjaxListAction();
		}else if("ajax-insert".equals(actionName)) {
			action = new AjaxInsertAction();
		}else if("ajax-delete".equals(actionName)) {
			action = new AjaxDeleteAction();
		}else {
			action = new ListAction();
		}
			

		
		return action;
	}

}
