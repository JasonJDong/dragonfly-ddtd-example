package org.dragonfly.ddtd.example.task;

import lombok.Getter;

/**
 * State of task process
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/2/8
 **/
@Getter
public enum TaskProcessState {

    SUCCESS(1, "org.dragonfly.ddtd.example.task.TaskProcessState.Success"),
    FAIL(-1, "org.dragonfly.ddtd.example.task.TaskProcessState.fail"),
    TERMINATE(-9, "org.dragonfly.ddtd.example.task.TaskProcessState.terminate"),
    ;
    /**
     * 值
     */
    private final int value;
    /**
     * 名称
     */
    private final String name;

    TaskProcessState(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
