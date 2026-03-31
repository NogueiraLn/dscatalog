package com.dscatalog.aula.services;

import com.dscatalog.aula.dto.CategoryDTO;
import com.dscatalog.aula.entities.Category;
import com.dscatalog.aula.repositories.CategoryRepository;
import com.dscatalog.aula.tests.factories.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;
    @Mock
    private CategoryRepository repository;

    private Category category;
    private List<Category> listCategory;


    @BeforeEach
    void setUp() throws Exception {
        category = CategoryFactory.createCategory();
        listCategory = new ArrayList<>();
        listCategory.add(category);

        when(repository.findAll()).thenReturn(listCategory);

    }

    @Test
    public void findAllShouldReturnListCategoryDTO(){
        List<CategoryDTO> result = service.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(category.getId(), result.get(0).getId());
        Assertions.assertEquals(category.getName(), result.get(0).getName());

    }







}
