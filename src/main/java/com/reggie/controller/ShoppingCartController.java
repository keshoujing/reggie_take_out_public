package com.reggie.controller;

import com.azure.core.annotation.Post;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.entity.ShoppingCart;
import com.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Add shopping cart.
     * @param shoppingCart
     * @return
     */

//    @RequestMapping("/add")
//    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
//
//        //Set userId, which user changed this shopping cart.
//        shoppingCart.setUserId(BaseContext.getCurrentId());
//
//        //search the dish or setmeal under same user, if exist, update data instead insert.
//        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
//        queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId()).or();
//        queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
//
//        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
//        if(cart != null) {
//            cart.setNumber(cart.getNumber() + 1);
//            shoppingCartService.updateById(cart);
//        } else {
//            //not exist, add to shopping cart.
//            shoppingCartService.save(shoppingCart);
//            cart = shoppingCart;
//        }
//
//        return Result.success(cart);
//    }

    @RequestMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {

        //Set userId, which user changed this shopping cart.
        shoppingCart.setUserId(BaseContext.getCurrentId());

        //search the dish or setmeal under same user, if exist, update data instead insert.
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);


        if(cart != null) {
            //if exist, update data instead insert.
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartService.updateById(cart);
        }else {
            //not exist, add to shopping cart.
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }

        return Result.success(cart);
    }

    @PostMapping("/sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId()).or();
        queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart != null) {
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartService.updateById(cart);
        } else {
            return Result.error("No such item in cart.");
        }

        if (cart.getNumber() == 0) {
            shoppingCartService.removeById(cart.getId());
        }

        return Result.success(cart);

    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(currentId != null, ShoppingCart::getUserId, currentId);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return Result.success(list);
    }

    @DeleteMapping("/clean")
    public Result<String> clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return Result.success("Shopping cart cleaned.");
    }
}
