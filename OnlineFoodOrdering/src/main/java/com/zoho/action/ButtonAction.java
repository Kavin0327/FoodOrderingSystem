package com.zoho.action;

public class ButtonAction {
	private String actionType;

	public String execute() {
		System.out.println("Struts...");
		if (actionType.equals("Login"))
			return "login";
		if (actionType.equals("Signup"))
			return "signup";
		return "error";
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
