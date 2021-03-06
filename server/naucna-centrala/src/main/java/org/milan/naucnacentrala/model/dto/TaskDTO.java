package org.milan.naucnacentrala.model.dto;

import org.camunda.bpm.engine.task.Task;

public class TaskDTO {

    String taskId;
    String name;
    String assignee;

    public TaskDTO() {
    }

    public TaskDTO(String taskId, String name, String assignee) {
        this.taskId = taskId;
        this.name = name;
        this.assignee = assignee;
    }

    public static TaskDTO formDto(Task t) {
        if (t == null) {
            return new TaskDTO();
        } else {
            return new TaskDTO(t.getId(), t.getName(), t.getAssignee());
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
