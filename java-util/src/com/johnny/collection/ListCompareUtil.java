package com.johnny.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListCompareUtil {
	
	public static void main(String [] args){
		long st = System.nanoTime();
		System.out.println("Begin Time:----  " +st);
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            list1.add("test"+i);
            list2.add("test"+i*2);
        }
        HashSet hashSet = (HashSet)getDiffentNoDuplicate(list1, list2);
        Iterator iterator = hashSet.iterator();
        while(iterator.hasNext()){
        	System.out.println(iterator.next());
        }
        System.out.println("getDiffrent total times "+(System.nanoTime()-st));
	}

	/**
	 * 不允许实例化
	 */
	private ListCompareUtil() {
	}

	/**
	 * 获取两个集合的不同元素
	 * 
	 * @param collmax
	 * @param collmin
	 * @return
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public static Collection getDiffent(Collection collmax, Collection collmin) {
		// 使用LinkeList防止差异过大时,元素拷贝
		Collection csReturn = new LinkedList();
		Collection max = collmax;
		Collection min = collmin;
		// 先比较大小,这样会减少后续map的if判断次数
		if (collmax.size() < collmin.size()) {
			max = collmin;
			min = collmax;
		}
		// 直接指定大小,防止再散列
		Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
		for (Object object : max) {
			map.put(object, 1);
		}
		for (Object object : min) {
			if (map.get(object) == null) {
				csReturn.add(object);
			} else {
				map.put(object, 2);
			}
		}
		for (Map.Entry<Object, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				csReturn.add(entry.getKey());
			}
		}
		return csReturn;
	}

	/**
	 * 获取两个集合的不同元素,去除重复
	 * 
	 * @param collmax
	 * @param collmin
	 * @return
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public static Collection getDiffentNoDuplicate(Collection collmax,
			Collection collmin) {
		return new HashSet(getDiffent(collmax, collmin));
	}
	
	public static void compareList(List list1, List list2){
		List collectionMax = list1;
		List collectionMin = list2;
		if(list1.size() < list2.size()){
			collectionMax = list2;
			collectionMin = list1;
		}
		for (int i = 0; i < collectionMax.size(); i++) {
			
		}
	}
}
