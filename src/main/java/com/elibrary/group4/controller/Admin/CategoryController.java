package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.Category;
import com.elibrary.group4.model.request.CategoryRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody CategoryRequest request) throws Exception{
        Category category = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Category>("Created", category));
    }

    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "categoryId") String sortBy
    ) throws Exception {
        Page<Category> get = categoryService.list(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",get));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) throws Exception {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Succes delete",null));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody CategoryRequest request, @PathVariable("id") String id) throws Exception {
        categoryService.update(request,id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Updated",request));
    }
}
