package org.dragonfly.ddtd.example.task.work2;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.dragonfly.ddtd.enums.TaskWorkState;
import org.dragonfly.ddtd.example.dao.entity.DdtdExampleTaskDO;
import org.dragonfly.ddtd.example.task.work2.context.ExampleTaskContext2;
import org.dragonfly.ddtd.framework.entity.ITaskContext;
import org.dragonfly.ddtd.framework.task.ITaskCacheableDataAccessor;
import org.dragonfly.ddtd.framework.task.ITaskDataAccessor;
import org.dragonfly.ddtd.framework.task.ITaskPersistDataAccessor;
import org.dragonfly.ddtd.framework.task.ITaskWorker;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 2nd Example task worker
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/12/3
 **/
@Slf4j
@Component
public class ExampleTaskWorker2 implements ITaskWorker<DdtdExampleTaskDO> {

    private final ITaskCacheableDataAccessor<DdtdExampleTaskDO> taskCacheableDataAccessor;

    private final ITaskPersistDataAccessor<DdtdExampleTaskDO> taskPersistDataAccessor;

    @Getter
    @Setter
    private Consumer<ExampleTaskContext2> onCompleted;

    ExampleTaskWorker2(ITaskCacheableDataAccessor<DdtdExampleTaskDO> taskCacheableDataAccessor,
                       ITaskPersistDataAccessor<DdtdExampleTaskDO> taskPersistDataAccessor) {
        this.taskCacheableDataAccessor = taskCacheableDataAccessor;
        this.taskPersistDataAccessor = taskPersistDataAccessor;
    }

    @Override
    public boolean accept(ITaskContext context) {
        return true;
    }

    @Override
    public ITaskDataAccessor<DdtdExampleTaskDO> getTaskDataAccessor() {
        return new ITaskDataAccessor<DdtdExampleTaskDO>() {
            @Override
            public ITaskCacheableDataAccessor<DdtdExampleTaskDO> getTaskCacheableDataAccessor() {
                return taskCacheableDataAccessor;
            }

            @Override
            public ITaskPersistDataAccessor<DdtdExampleTaskDO> getTaskPersistDataAccessor() {
                return taskPersistDataAccessor;
            }
        };
    }

    @Override
    public ITaskContext parseContext(String rawContext) {
        return JSON.parseObject(rawContext, ExampleTaskContext2.class);
    }

    @Override
    public TaskWorkState partitionWork(ITaskContext context, DdtdExampleTaskDO data) {

        try {
            if (data == null) {
                return TaskWorkState.PASS;
            }
            data.setStartAt(new Date());
            TimeUnit.SECONDS.sleep(RandomUtil.randomInt(6));
            return TaskWorkState.SUCCESS;
        } catch (InterruptedException e) {
            log.info("Task terminated...");
            return TaskWorkState.FAIL;
        }
    }

    @Override
    public TaskWorkState cleanupWork(ITaskContext context) {
        try {
            TimeUnit.SECONDS.sleep(5L);
            if (onCompleted != null) {
                onCompleted.accept((ExampleTaskContext2) context);
            }
            return TaskWorkState.SUCCESS;
        } catch (InterruptedException e) {
            log.info("Clean processing terminated...");
            return TaskWorkState.FAIL;
        }
    }

    @Override
    public String getVersion() {
        return "1";
    }

    @Override
    public String getName() {
        return ExampleTaskWorker2.class.getSimpleName();
    }

    @Override
    public String getDescription() {
        return "2nd Example task worker";
    }
}
