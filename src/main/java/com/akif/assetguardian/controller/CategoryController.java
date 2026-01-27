package com.akif.assetguardian.controller;


import com.akif.assetguardian.DTO.CategoryResponse;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasrole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(category,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PreAuthorize("hasrole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<Category> deleteCategory(@PathVariable int categoryID){
        categoryService.delete(categoryID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
