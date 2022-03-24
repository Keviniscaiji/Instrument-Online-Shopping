package com.group13.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group13.entity.CommodityComment;
import com.group13.mapper.CommodityCommentMapper;
import com.group13.service.CommodityCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author group13
 * @since 2022-03-16
 */
@Service
public class CommodityCommentServiceImpl extends ServiceImpl<CommodityCommentMapper, CommodityComment> implements CommodityCommentService {

    private CommodityCommentMapper commentMapper;

    @Autowired
    public CommodityCommentServiceImpl(CommodityCommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * getInfoList by commodity id
     *
     * @param current
     * @param limit
     * @param commodityId
     * @return
     */
    @Override
    public Map<String, Object> getInfoList(long current, long limit, String commodityId) {
        Page<CommodityComment> page = new Page<>(current, limit);
        // condition
        QueryWrapper<CommodityComment> wrapper = new QueryWrapper<>();
        wrapper.eq("commodity_id", commodityId);
        commentMapper.selectPage(page, wrapper);
        long total = page.getTotal();
        List<CommodityComment> records = page.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return map;
    }
}
