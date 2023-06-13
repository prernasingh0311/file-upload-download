package farmmart.file.upload.download.service;

import farmmart.file.upload.download.entity.User;
import farmmart.file.upload.download.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class UserService {
    @Autowired
    UserRepository userRepository;

    public String checkIfAlreadyExists(User user) {
        return "success";
    }
    public String addUserService(String username, String password) {
        if(username.equals("") || password.equals(""))  return "ERROR";
        User user = new User(username, password);
        userRepository.save(user);
        return "SUCCESS";
    }

//    public String loginService(String username, String password) {
////        User name
////        userRepository.findAll()
//    }
}
