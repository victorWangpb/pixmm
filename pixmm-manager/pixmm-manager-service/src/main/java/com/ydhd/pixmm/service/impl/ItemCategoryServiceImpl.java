package com.ydhd.pixmm.service.impl;

import com.ydhd.pixmm.dao.TbItemCatMapper;
import com.ydhd.pixmm.pojo.EasyUITreeNode;
import com.ydhd.pixmm.pojo.TbItemCat;
import com.ydhd.pixmm.pojo.TbItemCatExample;
import com.ydhd.pixmm.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋波 on 08/08/2017.
 */
@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCategoryList(long parentId) {
        //根据parentid查询分类列表
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list=itemCatMapper.selectByExample(example);

        //转换到EasyUITreeNode列表
        List<EasyUITreeNode> result=new ArrayList<>();
        for (TbItemCat itemCat:list
             ) {
            //创建一个节点对象
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setState(itemCat.getIsParent()?"closed":"open");
            node.setText(itemCat.getName());

            result.add(node);
        }
        return result;
    }
}
