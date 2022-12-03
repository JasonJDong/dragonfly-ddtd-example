package org.dragonfly.ddtd.example.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * Default abstract class for database entity.
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2021/11/4
 **/
@Data
public abstract class BaseDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * Tenant
     */
    private String tenant;
    /**
     * creator id
     */
    private String createId;
    /**
     * create time
     */
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Date created;
    /**
     * logic delete flag
     */
    private Integer yn;
    /**
     * user last modified
     */
    private String updateId;
    /**
     * time last modified
     */
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Date modified;
}
