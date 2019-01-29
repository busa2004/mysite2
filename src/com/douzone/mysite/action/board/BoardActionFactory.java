package com.douzone.mysite.action.board;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;

import com.douzone.mysite.action.board.ListAction;

public class BoardActionFactory extends AbstractActionFactory {
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("writeform".equals(actionName)){
			action = new WriteFormAction();
		}else if("viewform".equals(actionName)){
			action = new viewFormAction();
		}else if("modifyform".equals(actionName)){
			action = new ModifyformAction();
		}else if("replyform".equals(actionName)){
			action = new ReplyformAction();
		}else if("reply".equals(actionName)){
			action = new ReplyAction();
		}else if("modify".equals(actionName)){
			action = new ModifyAction();
		}else if("remove".equals(actionName)){
			action = new RemoveAction();
		}else if("comment".equals(actionName)){
			action = new CommentAction();
		}else if("commentremove".equals(actionName)){
			action = new CommentRemoveAction();
		}else {
		
			action = new ListAction();
		}
			

		
		return action;
	}
}
