package org.dragonfly.ddtd.example.task.accsessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dragonfly.ddtd.example.dao.entity.DdtdExampleTaskDO;
import org.dragonfly.ddtd.framework.entity.ITaskContext;
import org.dragonfly.ddtd.framework.task.ITaskCacheableDataAccessor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Cached data access
 * <p>
 *     in this case use redis.
 * </p>
 *
 * @author jian.dong1
 * @version 1.0
 * @date 2022/2/8
 **/
@Slf4j
@SuppressWarnings("unchecked")
@Component
public class TaskDistributionCacheAccessor implements ITaskCacheableDataAccessor<DdtdExampleTaskDO> {

    private final RedisTemplate<String, Object> redisTemplate;

    public TaskDistributionCacheAccessor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(ITaskContext context) {
        return String.format("dragonfly-ddtd-example:%s", context.getTaskId());
    }

    @Override
    public void cacheAllData(ITaskContext context, Collection<DdtdExampleTaskDO> data) throws Exception {
        try {
            byte[] key = getKey(context).getBytes(StandardCharsets.UTF_8);
            redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                connection.openPipeline();
                for (Object one : data) {
                    connection.lPush(key, JSON.toJSONBytes(one));
                }
                // 1天过期
                connection.expire(key, TimeUnit.DAYS.toSeconds(1L));
                return null;
            });
        } catch (Exception e) {
            log.error("caught exception when put the data into cache, key: {}, exception: {}", getKey(context), ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

    @Override
    public boolean exists(ITaskContext context) {
        try {
            return BooleanUtils.toBooleanDefaultIfNull(redisTemplate.hasKey(getKey(context)), false);
        } catch (Exception e) {
            log.error("Determine whether the key exists and an exception occurs, key, {}, exception: {}", getKey(context), ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DdtdExampleTaskDO pollOne(ITaskContext context) {
        try {
            Object one = redisTemplate.opsForList().leftPop(getKey(context));
            if (one == null) {
                return null;
            }
            return new JSONObject((Map<String, Object>) one).toJavaObject(DdtdExampleTaskDO.class);
        } catch (Exception e) {
            log.error("An exception occurred when the first content of the list cache was taken out of the queue, key: {}, exception: {}", getKey(context), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    @Override
    public void clear(ITaskContext context) {
        try {
            redisTemplate.delete(getKey(context));
        } catch (Exception e) {
            log.error("An exception occurred while cleaning, key: {}, exception: {}", getKey(context), ExceptionUtils.getStackTrace(e));
        }
    }
}
