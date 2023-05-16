package com.elibrary.group4.service;

import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.Category;
import com.elibrary.group4.model.request.CategoryRequest;
import com.elibrary.group4.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> list() throws Exception {
        try {
            return categoryRepository.findAll();
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }

    @Override
    public Category create(CategoryRequest categoryRequest) throws Exception {
        try {
            Category category = new Category();
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        }catch (DataIntegrityViolationException e){
            throw new EntityExistsException();
        }
    }

    @Override
    public void update(CategoryRequest categoryRequest, String categoryId) throws Exception {
        try {
            Category existingCategory = get(categoryId);
            existingCategory.setName(categoryRequest.getName());
            categoryRepository.save(existingCategory);
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(String categoryId) throws Exception {
        try {
            Category category = get(categoryId);
            categoryRepository.delete(category);
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }

    @Override
    public Category get(String categoryId) throws Exception {
        try {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if(category.isEmpty()) throw new NotFoundException("Category not found");
            return category.get();
        }catch (NotFoundException e){
            throw new NotFoundException();
        }
    }
}
