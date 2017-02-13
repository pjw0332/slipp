package net.slipp.domain.users;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Authenticate {
	
	@NotEmpty @Size(min=4, max=12)
	private String userId;
	
	@NotEmpty @Size(min=4, max=12)
	private String password;
	
	public Authenticate() {
		// TODO Auto-generated constructor stub
	}
	
	public Authenticate(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean matchPassword(String password) {
		// TODO Auto-generated method stub
		return this.password.equals(password);
	}


}



	

