package in.sobaleva.service;

import in.sobaleva.dao.TaskDAO;
import in.sobaleva.domain.Status;
import in.sobaleva.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskDAO taskDAO;

    @Autowired
    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    public Page<Task> getAllTasksWithPaging(Pageable pageable) {
        return taskDAO.findAll(pageable);
    }

    public Optional<Task> getTaskById(Integer id) {
        return taskDAO.findById(id);
    }

    public Task saveTask(Task task) {
        return taskDAO.save(task);
    }

    public void deleteTask(Integer id) {
        taskDAO.deleteById(id);
    }

    public Task createTask(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        return taskDAO.save(task);
    }

    public Task updateTask(Integer id, String description, Status status) {
        Optional<Task> optionalTask = taskDAO.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setDescription(description);
            task.setStatus(status);
            return taskDAO.save(task);
        }
        return null;
    }
}