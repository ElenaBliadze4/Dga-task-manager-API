package com.savarjishodga.dgataskebismartva.config;

import com.savarjishodga.dgataskebismartva.entity.Project;
import com.savarjishodga.dgataskebismartva.entity.Task;
import com.savarjishodga.dgataskebismartva.entity.TaskStatus;
import com.savarjishodga.dgataskebismartva.entity.User;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import com.savarjishodga.dgataskebismartva.repository.ProjectRepository;
import com.savarjishodga.dgataskebismartva.repository.TaskRepository;
import com.savarjishodga.dgataskebismartva.repository.TaskStatusRepository;
import com.savarjishodga.dgataskebismartva.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public DataInitializer(TaskRepository taskRepository,
                           TaskStatusRepository taskStatusRepository,
                           UserRepository userRepository,
                           ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) {
        if (taskRepository.count() == 0) {
            log.info("Starting initial seed data insertion into database...");

            TaskStatus statusTodo = taskStatusRepository.findByCode(TaskStatusEnum.TO_DO)
                    .orElseGet(() -> taskStatusRepository.save(new TaskStatus(null, TaskStatusEnum.TO_DO)));

            TaskStatus statusInProgress = taskStatusRepository.findByCode(TaskStatusEnum.IN_PROGRESS)
                    .orElseGet(() -> taskStatusRepository.save(new TaskStatus(null, TaskStatusEnum.IN_PROGRESS)));

            TaskStatus statusDone = taskStatusRepository.findByCode(TaskStatusEnum.DONE)
                    .orElseGet(() -> taskStatusRepository.save(new TaskStatus(null, TaskStatusEnum.DONE)));

            // 2. axali iuzeris sheqmna
            User user = userRepository.findByEmail("elene@example.com")
                    .orElseGet(() -> userRepository.save(new User(null, "Elene", "Bliadze", "elene@example.com", null)));

            // 3. proeqtis testi
            Project project = projectRepository.findByName("Task Management Project")
                    .orElseGet(() -> projectRepository.save(new Project(null, "Task Management Project", "Main Development Project", null)));

            LocalDateTime now = LocalDateTime.now();

            // 4. 10 satesto taskis damateba
            taskRepository.save(Task.builder().title("Setup Architecture").description("Configure Maven and Spring Boot").status(statusDone).assignee(user).project(project).dueDate(now.plusDays(1)).build());
            taskRepository.save(Task.builder().title("Swagger Docs Config").description("Add OpenAPI documentation").status(statusDone).assignee(user).project(project).dueDate(now.plusDays(2)).build());
            taskRepository.save(Task.builder().title("Health Check Endpoint").description("Implement GET /api/health").status(statusDone).assignee(user).project(project).dueDate(now.plusDays(3)).build());
            taskRepository.save(Task.builder().title("Task REST Controller").description("Implement CRUD REST endpoints").status(statusInProgress).assignee(user).project(project).dueDate(now.plusDays(5)).build());
            taskRepository.save(Task.builder().title("Database JPA Integration").description("Configure H2 and Spring Data JPA").status(statusInProgress).assignee(user).project(project).dueDate(now.plusDays(6)).build());
            taskRepository.save(Task.builder().title("DTO Bean Validation").description("Add validation annotations to DTOs").status(statusInProgress).assignee(user).project(project).dueDate(now.plusDays(7)).build());
            taskRepository.save(Task.builder().title("Service Unit Tests").description("Write unit tests for TaskService").status(statusTodo).assignee(user).project(project).dueDate(now.plusDays(10)).build());
            taskRepository.save(Task.builder().title("Integration Testing").description("Write MockMvc integration tests").status(statusTodo).assignee(user).project(project).dueDate(now.plusDays(12)).build());
            taskRepository.save(Task.builder().title("Postman Collection").description("Prepare API collection with examples").status(statusTodo).assignee(user).project(project).dueDate(now.plusDays(14)).build());
            taskRepository.save(Task.builder().title("Final README Documentation").description("Update setup guidelines and API details").status(statusTodo).assignee(user).project(project).dueDate(now.plusDays(15)).build());

            log.info("Successfully seeded database with 10 initial tasks, default status entities, user, and project!");
        } else {
            log.info("Database already contains tasks ({}), skipping initial seed.", taskRepository.count());
        }

        }

    private User saveUser(UserRepository repository, String firstName, String lastName, String email) {
        return repository.save(new User(null, firstName, lastName, email, null));
    }
}
