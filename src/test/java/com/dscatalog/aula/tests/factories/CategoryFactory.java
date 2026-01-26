package com.dscatalog.aula.tests.factories;

import com.dscatalog.aula.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(2L, "Electronics");
	}
	
}
