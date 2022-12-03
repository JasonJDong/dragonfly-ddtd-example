package org.dragonfly.ddtd.example.busi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dragonfly.ddtd.example.dao.entity.DdtdExampleTaskDO;
import org.dragonfly.ddtd.example.task.work1.ExampleTaskWorker1;
import org.dragonfly.ddtd.example.task.work2.ExampleTaskWorker2;
import org.dragonfly.ddtd.example.task.work1.context.ExampleTaskContext1;
import org.dragonfly.ddtd.example.task.work2.context.ExampleTaskContext2;
import org.dragonfly.ddtd.framework.exception.DuplicatedTaskCreationException;
import org.dragonfly.ddtd.framework.task.DefaultTaskFactory;
import org.dragonfly.ddtd.spring.DdtdAutoFactoryRegistrar;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Business processor for example
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/12/3
 **/
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ExampleBusiProcessor {

    private final DdtdAutoFactoryRegistrar factoryRegistrar;

    public boolean triggerExampleTask1(List<DdtdExampleTaskDO> data, ExampleTaskContext1 context) {
        final DefaultTaskFactory<DdtdExampleTaskDO> factory = (DefaultTaskFactory<DdtdExampleTaskDO>) factoryRegistrar.getFactory(ExampleTaskWorker1.class);
        try {
            factory.applyTask(context, data);
        } catch (DuplicatedTaskCreationException e) {
            throw e;
        } catch (Exception e) {
            log.error("some error occurred", e);
            return false;
        }
        return true;
    }

    public boolean triggerExampleTask2(List<DdtdExampleTaskDO> data, ExampleTaskContext2 context) {
        final DefaultTaskFactory<DdtdExampleTaskDO> factory = (DefaultTaskFactory<DdtdExampleTaskDO>) factoryRegistrar.getFactory(ExampleTaskWorker2.class);
        try {
            factory.applyTask(context, data);
        } catch (DuplicatedTaskCreationException e) {
            throw e;
        } catch (Exception e) {
            log.error("some error occurred", e);
            return false;
        }
        return true;
    }
}
