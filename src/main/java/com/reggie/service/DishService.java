package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    /**
     * Add new dish and insert flavor data into it.
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * Search dish and flavor info by Id.
     */
    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void updateWithStatus(Integer status, List<Long> ids);
}
