package com.dscatalog.aula.projections;

public interface UserDetailsProjection {

	String getPassword();
	String getUsername();
	Long getRoleId();
	String getAuthority();
	
}
