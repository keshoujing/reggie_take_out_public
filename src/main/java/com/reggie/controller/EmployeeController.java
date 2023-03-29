package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        /**
         * 1. md5 process
         */

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        /**
         * 2. check username from database
         */

        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(employeeLambdaQueryWrapper);

        /**
         * 3. if no such user, return false;
         */

        if(emp == null) {
            return Result.error("login failed, please try again.");
        }

        /**
         * 4. compare password, return false if not same
         */

        if(!emp.getPassword().equals(password)) {
            return Result.error("login failed, please try again.");
        }

        /**
         * 5. check employee status, see if employee account has been disabled
         * 0 means disable
         */

        if(emp.getStatus() == 0) {
            return Result.error("account has been disabled");
        }

        /**
         * 6. success, put employee id to Session and return login success.
         */

        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * Employee logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        /**
         * clear session
         */

        request.getSession().removeAttribute("employee");
        return Result.success("logout successful");
    }


    /**
     * Add Employee
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //Long empId = (Long) request.getSession().getAttribute("employee");

        //employee.setUpdateUser(empId);
        //employee.setCreateUser(empId);

        employeeService.save(employee);

        return Result.success("Add success");
    }

    /**
     * Employee page query.
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    /**
     * Update employee information
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {

        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);

        employeeService.updateById(employee);

        return Result.success("Success.");
    }

    /**
     * search employee base on id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee emp = employeeService.getById(id);
        return emp != null ? Result.success(emp) : Result.error("Did not found employee");
    }

}
