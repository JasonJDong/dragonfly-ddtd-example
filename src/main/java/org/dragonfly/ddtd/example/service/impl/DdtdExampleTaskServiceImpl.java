package org.dragonfly.ddtd.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dragonfly.ddtd.example.dao.entity.DdtdExampleTaskDO;
import org.dragonfly.ddtd.example.dao.mapper.DdtdExampleTaskMapper;
import org.dragonfly.ddtd.example.service.IDdtdExampleTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Implement of example service
 * </p>
 *
 * @author jian.dong1
 * @since 2022-02-08
 */
@Service
public class DdtdExampleTaskServiceImpl extends ServiceImpl<DdtdExampleTaskMapper, DdtdExampleTaskDO> implements IDdtdExampleTaskService {

}
