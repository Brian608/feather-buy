package org.feather.user.service;

import org.feather.user.entity.User;
import org.feather.user.entity.UserElement;

public interface UserService {

     void registerUser (User user) throws Exception;

    UserElement login(User user);
}
