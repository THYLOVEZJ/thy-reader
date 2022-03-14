package com.thylovezj.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thylovezj.reader.entity.Book;
import com.thylovezj.reader.entity.Category;
import com.thylovezj.reader.entity.Evaluation;
import com.thylovezj.reader.service.BookService;
import com.thylovezj.reader.service.CategoryService;
import com.thylovezj.reader.service.EvaluationService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;


    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("/index");
        List<Category> categoryList = categoryService.selectAll();
        mav.addObject("categoryList", categoryList);
        return mav;
    }


    /***
     * 分页查询图书列表
     * @param p 页号
     * @return 分页对象
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        if (p == null) {
            p = 1;
        }
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }

    @GetMapping("/book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id){
        Book book = bookService.selectById(id);
        List<Evaluation> evaluations = evaluationService.selectByBookId(id);
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        mav.addObject("evaluations",evaluations);
        return mav;
    }
}
