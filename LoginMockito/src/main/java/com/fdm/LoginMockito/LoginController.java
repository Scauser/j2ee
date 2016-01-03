package com.fdm.LoginMockito;
//constructor
public class LoginController {
	
	DatabaseReader reader;
	
	public LoginController(DatabaseReader reader) {
		this.reader = reader;
	}
	
	public boolean Check(String name, String password) {
		User user = reader.getUser(name);
		
		if (user == null){
			return false;
		}
		
		if(name.equals(user.getName()) && password.equals(user.getPassword())){
			return true;
		}
		
		return false;
	}
}
