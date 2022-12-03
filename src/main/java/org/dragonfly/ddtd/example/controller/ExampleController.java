package org.dragonfly.ddtd.example.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.MoreObjects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dragonfly.ddtd.example.busi.ExampleBusiProcessor;
import org.dragonfly.ddtd.example.dao.entity.DdtdExampleTaskDO;
import org.dragonfly.ddtd.example.task.work1.context.ExampleTaskContext1;
import org.dragonfly.ddtd.example.task.work2.context.ExampleTaskContext2;
import org.dragonfly.ddtd.framework.exception.DuplicatedTaskCreationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * API
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/12/3
 **/
@Slf4j
@RestController
@RequestMapping("example")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleBusiProcessor exampleBusiProcessor;

    @GetMapping("work1")
    public ResponseEntity<String> triggerTask1(@RequestParam(value = "count", required = false) Integer count) {

        count = MoreObjects.firstNonNull(count, 10);
        ExampleTaskContext1 context1 = new ExampleTaskContext1(
                "t1",
                RandomUtil.randomString(3) + System.currentTimeMillis()
        );
        List<DdtdExampleTaskDO> data = IntStream.range(0, count)
                .mapToObj(index -> {
                    DdtdExampleTaskDO taskDO = new DdtdExampleTaskDO();
                    taskDO.setTenant(context1.getTenant());
                    taskDO.setTaskData(JSON.toJSONString(new String[]{String.format("%04d", index)}));
                    taskDO.setTaskId(context1.getTaskId());
                    return taskDO;
                }).collect(Collectors.toList());

        try {
            return ResponseEntity.ok(exampleBusiProcessor.triggerExampleTask1(data, context1) ? "success": "fail");
        } catch (DuplicatedTaskCreationException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("work2")
    public ResponseEntity<String> triggerTask2(@RequestParam(value = "count", required = false) Integer count) {

        count = MoreObjects.firstNonNull(count, 10);
        ExampleTaskContext2 context2 = new ExampleTaskContext2(
                "t2",
                RandomUtil.randomString(3) + System.currentTimeMillis()
        );
        List<DdtdExampleTaskDO> data = IntStream.range(0, count)
                .mapToObj(index -> {
                    DdtdExampleTaskDO taskDO = new DdtdExampleTaskDO();
                    taskDO.setTenant(context2.getTenant());
                    taskDO.setTaskData(JSON.toJSONString(new String[]{String.format("%04d", index)}));
                    taskDO.setTaskId(context2.getTaskId());
                    return taskDO;
                }).collect(Collectors.toList());

        try {
            return ResponseEntity.ok(exampleBusiProcessor.triggerExampleTask2(data, context2) ? "success": "fail");
        } catch (DuplicatedTaskCreationException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
