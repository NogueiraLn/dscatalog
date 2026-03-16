package com.dscatalog.aula.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dscatalog.aula.projections.IdProjection;

public class Utils {

	public static <ID> List<? extends IdProjection<ID>> replace(List<? extends IdProjection<ID>> ordered, 
			List<? extends IdProjection<ID>> unordered) {

		Map<ID, IdProjection<ID>> map = new HashMap<>();
		for(IdProjection<ID> x : unordered) {
			map.put(x.getId(), x);
		}
		List<IdProjection<ID>> result = new ArrayList<>();
		for(IdProjection<ID> prodP : ordered) {
			result.add(map.get(prodP.getId()));
		}
		

		return result;
	}

}
