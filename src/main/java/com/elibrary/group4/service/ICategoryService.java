package com.elibrary.group4.service;

import com.elibrary.group4.model.Category;
import com.elibrary.group4.model.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    List<Category> list() throws Exception;
    Category create(CategoryRequest categoryRequest) throws Exception;
    Category get(String categoryId) throws Exception;
    void update(CategoryRequest categoryRequest, String categoryId) throws Exception;
    void delete(String categoryId) throws Exception;
}
