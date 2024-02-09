package in.sobaleva.controller;

import in.sobaleva.domain.Status;
import in.sobaleva.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.sobaleva.service.TaskService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;

@Controller
@EnableWebMvc
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    @Value("${number.of.tasks.per.page}")
    private Integer numberOfTasksPerPage;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getHomePage(){
        System.out.println("Controller HomePageController is actioned");
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = numberOfTasksPerPage; // Количество задач на странице
        Page<Task> taskPage = taskService.getAllTasksWithPaging(PageRequest.of(page, pageSize));
        List<Task> tasks = taskPage.getContent();
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());
        return "taskslist";
    }

    @GetMapping("/add")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("statuses", Status.values());
        return "add";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Integer id, Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            model.addAttribute("task", task);
            model.addAttribute("statuses", Status.values());
            return "edit";
        } else {
            return "redirect:/tasks";
        }
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, @ModelAttribute Task task,
                           @RequestParam(defaultValue = "0") int page, Model model) {
        taskService.updateTask(id, task.getDescription(), task.getStatus());

        Optional<Task> editedTask = taskService.getTaskById(id);

        if (editedTask.isPresent()) {
            Page<Task> taskPage = taskService.getAllTasksWithPaging(PageRequest.of(page, 5));
            List<Task> tasks = taskPage.getContent();

            model.addAttribute("task", editedTask.get());
            model.addAttribute("tasks", tasks);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", taskPage.getTotalPages());
        }
        return "redirect:/tasks?page=" + page;
    }

    @GetMapping("/delete/{id}")
    public String getDeleteTaskPage(@PathVariable Integer id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);

        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "delete";
        } else {
            return "redirect:/tasks";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}