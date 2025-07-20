package com.localdelivery.local_delivery.services;

import com.localdelivery.local_delivery.dto.LoginRequest;
import com.localdelivery.local_delivery.dto.RegisterRequest;
import com.localdelivery.local_delivery.exceptions.EtAuthException;
import com.localdelivery.local_delivery.models.User;

public interface UserService {

    User registerUser(RegisterRequest registerRequest) throws EtAuthException;

    User loginUser(LoginRequest loginRequest) throws EtAuthException;
}
