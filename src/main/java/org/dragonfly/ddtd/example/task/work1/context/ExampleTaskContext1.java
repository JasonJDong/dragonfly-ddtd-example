package org.dragonfly.ddtd.example.task.work1.context;

import lombok.*;
import org.dragonfly.ddtd.enums.TaskDistributionStrategy;
import org.dragonfly.ddtd.framework.entity.ITaskContext;

/**
 * Task context for using in whole lifecycle of task.
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/2/8
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ExampleTaskContext1 extends ExampleBaseTaskContext implements ITaskContext {

    private TaskDistributionStrategy strategy = TaskDistributionStrategy.DYNAMIC;

    public ExampleTaskContext1(String tenant, String taskId) {
        super(tenant, taskId);
    }

    @Override
    public TaskDistributionStrategy getStrategy() {
        return strategy;
    }

    @Override
    public Integer getFixedCount() {
        return 6;
    }
}
