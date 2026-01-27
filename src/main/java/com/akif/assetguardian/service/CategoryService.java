package com.akif.assetguardian.service;


import com.akif.assetguardian.DTO.CategoryResponse;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Transactional
    public void save(Category currentCategory) {
        categoryRepo.findByCategoryName(currentCategory.getName()).ifPresent(existingCategory -> {throw new RuntimeException("Bu kategori zaten sistemde kayıtlı!");});
        categoryRepo.save(currentCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(this::mapToResponse).toList();

    }
    @Transactional
    public CategoryResponse delete(int ID) {
        Category category = categoryRepo.findById(ID).orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getAssets().isEmpty()) {
            throw new RuntimeException("Bu kategori silinemez! İçerisinde " +
                    category.getAssets().size() + " adet eşya tanımlı.");
        }

        categoryRepo.delete(category);
        return mapToResponse(category);
    }

    public CategoryResponse mapToResponse(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
