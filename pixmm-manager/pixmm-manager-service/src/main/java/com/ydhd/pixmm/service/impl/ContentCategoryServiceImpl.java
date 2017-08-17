package com.ydhd.pixmm.service.impl;

import com.ydhd.pixmm.dao.TbContentCategoryMapper;
import com.ydhd.pixmm.pojo.EasyUITreeNode;
import com.ydhd.pixmm.pojo.TbContentCategory;
import com.ydhd.pixmm.pojo.TbContentCategoryExample;
import com.ydhd.pixmm.service.ContentCategoryService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一EasyUITreeNode节点
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            resultList.add(node);
        }
        return resultList;

    }

    @Override
    public PixmmResult insertCatgory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        //'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入数据
        contentCategoryMapper.insert(contentCategory);
        //取返回的主键
        Long id = contentCategory.getId();
        //判断父节点的isparent属性
        //查询父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //返回主键
        return PixmmResult.ok(id);
    }

}
