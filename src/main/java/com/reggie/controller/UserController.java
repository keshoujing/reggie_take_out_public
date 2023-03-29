package com.reggie.controller;

import com.azure.core.annotation.Post;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.Result;
import com.reggie.entity.User;
import com.reggie.service.UserService;
import com.reggie.utils.EmailUtils;
import com.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * send code for verification
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
        //get email address

        String email = user.getEmail();


        if(StringUtils.hasText(email)) {

            //generate random 4 digit code

            String validateCode = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info("code={}", validateCode);

            //use azure email service address.

            emailUtils.sendEmail("", "", email, validateCode);

            //save code in session.

            //httpSession.setAttribute(email, validateCode);

            //put code to redis and set valid time 5 mins.
            redisTemplate.opsForValue().set(email, validateCode, 5, TimeUnit.MINUTES);

            return Result.success("Send successful");
        }

        return Result.error("Msg send fail.");
    }

    /**
     * User login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        //get email address
        String email = map.get("email").toString();

        //get verification code
        String code = map.get("code").toString();

        //get verification from session
        //Object verificationCode = session.getAttribute(email);

        //get verification code from redis
        Object verificationCode = redisTemplate.opsForValue().get(email);

        //compare
        if(verificationCode != null && verificationCode.equals(code)) {
            //login successful
            //check if this email registered in the database, if not register automatically.
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, email);
            User user = userService.getOne(queryWrapper);

            if (user == null) {
                user = new User();
                user.setEmail(email);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            //if loged in, remove verification code from redis.
            redisTemplate.delete(email);

            return Result.success(user);
        }

        return Result.error("Log in fail.");
    }

}
