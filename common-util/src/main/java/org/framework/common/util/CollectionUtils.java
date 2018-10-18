package com.aihuishou.recycle.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author jiashuai.xie
 */
public class CollectionUtils {

    /**
     * 将集合拆分成多个集合，每个集合的大小为${size}
     * @param list 待拆分的集合
     * @param size 小集合的大小
     * @param <T> 集合元素类型
     * @return  List<List<T>>
     */
    public static <T> List<List<T>> split(List<T> list, int size) {

        return Lists.partition(list,size);

    }

}

