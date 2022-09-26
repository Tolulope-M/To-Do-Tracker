package com.morenike.activitytracker.ServiceImpl;

import com.morenike.activitytracker.DTO.TaskDTO;
import com.morenike.activitytracker.DTO.UserDTO;
import com.morenike.activitytracker.Entity.Task;
import com.morenike.activitytracker.Entity.User;
import com.morenike.activitytracker.Exception.TaskNotFoundException;
import com.morenike.activitytracker.Exception.UserNotFoundException;
import com.morenike.activitytracker.Repository.TaskRepository;
import com.morenike.activitytracker.Repository.UserRepository;
import com.morenike.activitytracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    /*
     * Register User implementation
     */
    @Override
    public boolean registerUser(User user) {
        Optional<User> tmpuser = userRepository.findUserByEmail(user.getEmail());
        if (tmpuser.isEmpty()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /*
     * user login User implementation
     */
    @Override
    public boolean loginAuth(User user) {
        Optional<User> tmpUser = userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (tmpUser.isEmpty()) return false;
        else {
            return tmpUser.get().getEmail().equals(user.getEmail()) &&
                    tmpUser.get().getPassword().equals(user.getPassword());
        }
    }
}
