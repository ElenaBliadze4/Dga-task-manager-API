package com.savarjishodga.dgataskebismartva;

import com.savarjishodga.dgataskebismartva.dto.TaskRequestDTO;
import com.savarjishodga.dgataskebismartva.entity.Project;
import com.savarjishodga.dgataskebismartva.entity.Task;
import com.savarjishodga.dgataskebismartva.entity.TaskStatus;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import com.savarjishodga.dgataskebismartva.repository.ProjectRepository;
import com.savarjishodga.dgataskebismartva.repository.TaskRepository;
import com.savarjishodga.dgataskebismartva.repository.TaskStatusRepository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;


    private Long savedProjectId;
    private Long savedTaskId;


    @BeforeEach
    void setUp() {

        taskRepository.deleteAll();

        TaskStatus status = taskStatusRepository
                .findByCode(TaskStatusEnum.TO_DO)
                .orElseGet(() -> {

                    TaskStatus newStatus = new TaskStatus();

                    newStatus.setCode(TaskStatusEnum.TO_DO);

                    return taskStatusRepository.save(newStatus);
                });


        Project project = new Project();

        project.setName("Test Project");

        project = projectRepository.save(project);

        savedProjectId = project.getId();


        Task task = new Task();

        task.setTitle("Existing Task");
        task.setDescription("Existing Description");
        task.setStatus(status);
        task.setProject(project);

        task = taskRepository.save(task);

        savedTaskId = task.getId();


        log.info(
                "Test setup completed. Task ID: {}, Project ID: {}",
                savedTaskId,
                savedProjectId
        );
    }


    @AfterEach
    void tearDown() {
        log.info("Test execution finished.");
    }


    @Test
    void createTask_Success() throws Exception {

        log.info("Starting test: createTask_Success");

        TaskRequestDTO requestDTO = new TaskRequestDTO();

        requestDTO.setTitle("New Integration Task");
        requestDTO.setDescription("Testing endpoint");
        requestDTO.setStatus(TaskStatusEnum.TO_DO);
        requestDTO.setProjectId(savedProjectId);


        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(requestDTO)
                                )
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.title")
                                .value("New Integration Task")
                );

        log.info("SUCCESS: createTask_Success passed!");
    }


    @Test
    void createTask_WithoutTitle_BadRequest() throws Exception {

        log.info(
                "Starting test: createTask_WithoutTitle_BadRequest"
        );

        TaskRequestDTO dto = new TaskRequestDTO();

        dto.setDescription("Task without title");
        dto.setStatus(TaskStatusEnum.TO_DO);
        dto.setProjectId(savedProjectId);


        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(dto)
                                )
                )
                .andDo(print())
                .andExpect(status().isBadRequest());


        log.info(
                "SUCCESS: createTask_WithoutTitle_BadRequest passed!"
        );
    }


    @Test
    void getTaskById_Success() throws Exception {

        log.info("Starting test: getTaskById_Success");


        mockMvc.perform(
                        get("/tasks/" + savedTaskId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(savedTaskId)
                );


        log.info("SUCCESS: getTaskById_Success passed!");
    }


    @Test
    void getTaskById_NotFound() throws Exception {

        log.info("Starting test: getTaskById_NotFound");


        mockMvc.perform(
                        get("/tasks/99999")
                )
                .andDo(print())
                .andExpect(status().isNotFound());


        log.info("SUCCESS: getTaskById_NotFound passed!");
    }


    @Test
    void updateTaskStatus_Success() throws Exception {

        log.info("Starting test: updateTaskStatus_Success");


        mockMvc.perform(
                        patch("/tasks/" + savedTaskId + "/status")
                                .param("status", "IN_PROGRESS")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.status")
                                .value("IN_PROGRESS")
                );


        log.info(
                "SUCCESS: updateTaskStatus_Success passed!"
        );
    }
}
