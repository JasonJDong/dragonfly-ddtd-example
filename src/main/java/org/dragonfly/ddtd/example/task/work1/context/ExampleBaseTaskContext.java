package org.dragonfly.ddtd.example.task.work1.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Abstract task context class
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/12/3
 **/
@Data
@RequiredArgsConstructor
public abstract class ExampleBaseTaskContext implements Serializable {
    private final String tenant;
    private final String taskId;
}
