package Crm.service;

import java.security.Principal;

import Crm.model.entity.payload.request.ChangePasswordRequest;



public interface UserService {

	public void  changePassword(ChangePasswordRequest request, Principal connectedUser);
}
