package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.Result;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;

import java.util.List;
import java.util.Set;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    SetmealDto getInfo(Long id);

    void removeWithDish(List<Long> ids);

    void updateWithDish(SetmealDto setmealDto);
}
