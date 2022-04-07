package com.group13.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group13.entity.StatisticsDaily;
import com.group13.mapper.StatisticsDailyMapper;
import com.group13.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author group13
 * @since 2022-04-07
 */
@Service
@Transactional
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    private UserService userService;
    private CommodityService commodityService;
    private StatisticsDailyMapper staMapper;
    private PostService postService;

    @Autowired
    public StatisticsDailyServiceImpl(UserService userService, CommodityService commodityService, StatisticsDailyMapper staMapper, PostService postService) {
        this.userService = userService;
        this.commodityService = commodityService;
        this.staMapper = staMapper;
        this.postService = postService;
    }

    /**
     * 统计某一天数据
     * @param day
     */
    @Override
    public void getCount(String day) {
        //添加记录之前删除表相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        staMapper.delete(wrapper);

        // 得到截止某一天注册人数
        Integer registerCount = userService.countRegister(day);
        // 得到截止某一天购买人数
        Integer buyCount = commodityService.countBuy(day);
        // 得到截止某一天访问人数
        Integer visitCount = commodityService.countVisit(day);
        // 得到截止某一天买家秀个数
        Integer postCount = postService.countPost(day);

        // 添加到统计分析表
        StatisticsDaily sta = new StatisticsDaily();
        //注册人数
        sta.setRegisterNum(registerCount);
        sta.setCommodityBuyNum(buyCount);
        sta.setCommodityVisitNum(visitCount);
        sta.setPostNum(postCount);
        //统计日期
        sta.setDateCalculated(day);
        staMapper.insert(sta);
    }
    /**
     * 图表显示
     * 返回两部分数据，日期json数组，数量json数组
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);
        // 因为if安徽有两部分数据， 日期 和 日期对应数量
        // 前端要求数组json结构，对应后端java代码是list集合
        // 创建两个list集合， 一个日期list，一个日期对应数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        // 遍历查询的所有数据list集合，进行封装
        for (StatisticsDaily statisticsDaily : list) {
            // 封装集合
            date_calculatedList.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "register_num":
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "commodity_visit_num":
                    numDataList.add(statisticsDaily.getCommodityVisitNum());
                    break;
                case "commodity_buy_num":
                    numDataList.add(statisticsDaily.getCommodityBuyNum());
                    break;
                default:
                    numDataList.add(statisticsDaily.getPostNum());
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);

        return map;
    }
}
