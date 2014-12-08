package Logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Shared.TCPClient;
import Shared.AuthUser;

public class User {
	
	public String usermail;
	public String password;
	
	public boolean isValid() throws Exception{
		Gson gson = new GsonBuilder().create();
		TCPClient TcpClient = new TCPClient();
		AuthUser AU = new AuthUser();
		AU.setAuthUserEmail(usermail);
		AU.setAuthUserPassword(password);
		// Users from client are not admins
		AU.setAuthUserIsAdmin(false);
		String gsonString = gson.toJson(AU);
		System.out.println(gsonString);
		// Send user class (username & password) to validateLogin-function in TCPClient class
		return TcpClient.validateLogin(gsonString);
	}

}
