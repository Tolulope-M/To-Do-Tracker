package com.morenike.activitytracker.Service;

import com.morenike.activitytracker.DTO.TaskDTO;
import com.morenike.activitytracker.DTO.UserDTO;
import com.morenike.activitytracker.Entity.User;
import com.morenike.activitytracker.Entity.Task;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);

    boolean loginAuth(User user);
}
