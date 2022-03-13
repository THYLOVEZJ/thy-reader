package com.thylovezj.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thylovezj.reader.entity.Book;
import com.thylovezj.reader.entity.Evaluation;
import com.thylovezj.reader.mapper.BookMapper;
import com.thylovezj.reader.mapper.EvaluationMapper;
import com.thylovezj.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;

    /***
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 图书编号
     */
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //这里的两个参数，第一个是数据库的字段名，另一个是从Servlet发送来的参数
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("state","enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluations = evaluationMapper.selectList(queryWrapper);
        return evaluations;
    }
}
