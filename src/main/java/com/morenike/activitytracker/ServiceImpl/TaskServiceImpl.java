package com.morenike.activitytracker.ServiceImpl;

import com.morenike.activitytracker.Entity.Status;
import com.morenike.activitytracker.Entity.Task;
import com.morenike.activitytracker.Entity.User;
import com.morenike.activitytracker.Exception.TaskNotFoundException;
import com.morenike.activitytracker.Exception.UserNotFoundException;
import com.morenike.activitytracker.Repository.TaskRepository;
import com.morenike.activitytracker.Repository.UserRepository;
import com.morenike.activitytracker.Service.TaskService;
import com.morenike.activitytracker.Utils.TimeAndDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    /*
     * Create task implementation
     */
    @Override
    public void createTask(Task task, String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            task.setCreatedAtDate(TimeAndDate.getCurrentDate());
            task.setCreatedTime(TimeAndDate.getCurrentTime());
            task.setUser(user.get());
            task.setStatus(Status.PENDING.name());
            taskRepository.save(task);
        } else throw new UserNotFoundException("This user was not found");
    }

    /*
     * get all task implementation
     */
    @Override
    public List<Task> getAllTask(String email) {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        return taskRepository.findAllByUser(user).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    /*
     * implementation for getting a specific task
     */
    @Override
    public Task getTask(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task Not found"));
    }

    /*
     * implementation for editing a specific task
     */
    @Override
    public void editTask(int id, Task task) {
        Task tmpTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task Not found"));
        tmpTask.setTitle(task.getTitle());
        tmpTask.setDescription(task.getDescription());
        tmpTask.setUpdateDate(task.getUpdateDate());
        tmpTask.setUpdateTime(task.getUpdateTime());
        tmpTask.setStatus(Status.IN_PROGRESS.name());

        taskRepository.save(tmpTask);
    }

    /*
     * implementation for deleting a specific task
     */
    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    /*
     * implementation for getting a pending task
     */
    @Override
    public List<Task> getPendingTask(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        return taskRepository.findTasksByUserAndStatus(user, Status.PENDING.name()).orElseThrow(() -> new TaskNotFoundException("Task Not found"));
    }

    /*
     * implementation for getting all completed task
     */

    @Override
    public List<Task> getCompletedTask(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        return taskRepository.findTasksByUserAndStatus(user, Status.COMPLETED.name()).orElseThrow(() -> new TaskNotFoundException("Task Not found"));
    }

    /*
     * implementation for marking task as completed
     */
    @Override
    public void completeTask(int id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        task.setStatus(Status.COMPLETED.name());
        taskRepository.save(task);
    }

    /*
     * implementation getting task that are in progress
     */
    @Override
    public List<Task> getInProgressTask(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        return taskRepository.findTasksByUserAndStatus(user, Status.IN_PROGRESS.name()).orElseThrow(() -> new TaskNotFoundException("Task Not found"));
    }

    /*
     * Move task to Done implementation
     */
    @Override
    public void moveToDone(String email, int id) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        Task task = taskRepository.findTasksByUserAndId(user, id);
        task.setStatus(Status.COMPLETED.name());
        task.setCompletedAtDate(TimeAndDate.getCurrentDate());
        task.setCompletedAtTime(TimeAndDate.getCurrentTime());
        taskRepository.save(task);

    }

    /*
     * Move task to pending implementation
     */
    @Override
    public void moveToPending(String email, int id) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new TaskNotFoundException("User not found"));
        Task task = taskRepository.findTasksByUserAndId(user, id);
        task.setStatus(Status.PENDING.name());
        task.setUpdateDate(TimeAndDate.getCurrentDate());
        task.setUpdateTime(TimeAndDate.getCurrentTime());
        taskRepository.save(task);

    }

    /*
     * Searching for a task implementation
     */
    @Override
    public Optional<List<Task>> searchTask(Task task) {

        return taskRepository.findTasksByTitle(task.getTitle());
    }
}
