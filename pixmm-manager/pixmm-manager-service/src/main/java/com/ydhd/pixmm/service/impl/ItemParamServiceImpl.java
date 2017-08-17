package com.ydhd.pixmm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ydhd.pixmm.dao.TbItemParamMapper;
import com.ydhd.pixmm.pojo.EasyUIDataGridResult;
import com.ydhd.pixmm.pojo.TbItemParam;
import com.ydhd.pixmm.pojo.TbItemParamExample;
import com.ydhd.pixmm.service.ItemParamService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 王朋波 on 10/08/2017.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EasyUIDataGridResult getItemParam(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        TbItemParamExample example=new TbItemParamExample();
        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);
        //获取分页信息
        PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(itemParamList);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(itemParamList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public PixmmResult getItemParamByCid(Long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        //执行查询
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list != null&&list.size() > 0) {
            TbItemParam itemParam = list.get(0);
            return PixmmResult.ok(itemParam);
        }
        return PixmmResult.ok();

    }

    @Override
    public PixmmResult insertItemParam(Long cid, String paramData) {
        //创建一个pojo
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入记录
        itemParamMapper.insert(itemParam);
        return PixmmResult.ok();

    }
}
