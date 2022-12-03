package org.dragonfly.ddtd.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * example database entity
 * </p>
 *
 * @author jian.dong1
 * @since 2022-02-08
 */
@Getter
@Setter
@TableName("ddtd_example_task")
public class DdtdExampleTaskDO extends BaseDO {

    /**
     * task id
     */
    private String taskId;
    /**
     * task data
     */
    private String taskData;
    /**
     * state of task
     */
    private Integer state;
    /**
     * machine information
     */
    private String instInfo;
    /**
     * start time
     */
    private Date startAt;
    /**
     * elapsed time
     */
    private String elapsed;
}
