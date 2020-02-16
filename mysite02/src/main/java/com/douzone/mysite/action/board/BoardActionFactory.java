package com.douzone.mysite.action.board;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
		case "writeform" : return new BoardInsertFormAction();
		case "write" : return new BoardInsertAction();
		case "view" : return new BoardViewAction();
		case "modifyform" : return new BoardModifyFormAction();
		case "modify" : return new BoardModifyAction();
		case "deleteform" : return new BoardDeleteFormAction();
		case "delete" : return new BoardDeleteAction();
		default : return new BoardListAction();
		}
		
    }

}
