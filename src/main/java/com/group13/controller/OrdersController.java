package com.group13.controller;


import com.group13.common.R;
import com.group13.entity.vo.OrderQueryVo;
import com.group13.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author group13
 * @since 2022-04-20
 */
@RestController
@Api(tags = "Orders Controller")
@RequestMapping("/order")
@CrossOrigin
public class OrdersController {

    private OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * page order list by condition
     * @param current
     * @param limit
     * @param orderQueryVo
     * @return
     */
    @ApiOperation("page order list by condition")
    @PostMapping("pageOrderListCondition/{current}/{limit}")
    public R pageListUser(@PathVariable("current") long current,
                          @PathVariable("limit") long limit,
                          @RequestBody(required = false) OrderQueryVo orderQueryVo) {
        Map<String, Object> map = ordersService.pageByVo(current, limit, orderQueryVo);
        return R.ok().data(map);
    }

    /**
     * 根据id删除Order
     * @param id
     * @return
     */
    @ApiOperation("delete order by id")
    @DeleteMapping("deleteOrder/{id}")
    public R deleteUser(@PathVariable String id){
        boolean b = ordersService.removeById(id);
        if (b){
            return R.ok();
        }
        return R.error();
    }


}

