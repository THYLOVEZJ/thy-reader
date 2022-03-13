package com.thylovezj.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thylovezj.reader.entity.Test;

//传入泛型
public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
