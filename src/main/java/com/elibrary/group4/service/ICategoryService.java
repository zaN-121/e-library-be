package com.elibrary.group4.service;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Category;
import com.elibrary.group4.model.request.CategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    Category create(CategoryRequest categoryRequest) throws Exception;
    Category get(String categoryId) throws Exception;
    void update(CategoryRequest categoryRequest, String categoryId) throws Exception;
    void delete(String categoryId) throws Exception;
    Page<Category> list(Integer page, Integer size, String direction, String sortBy) throws Exception;
}
