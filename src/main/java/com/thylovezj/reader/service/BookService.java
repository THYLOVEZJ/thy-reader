package com.thylovezj.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thylovezj.reader.entity.Book;

public interface BookService {
    /***
     * 分页查询图书
     * @param categoryId 分类编号
     * @param order 排序方式
     * @param page 页号
     * @param rows 每页记录数
     * @return
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows);

    /***
     * 根据编号查询图书对象
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId);
}
