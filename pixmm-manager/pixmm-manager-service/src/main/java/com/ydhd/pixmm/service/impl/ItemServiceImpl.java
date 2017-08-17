package com.ydhd.pixmm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ydhd.pixmm.dao.TbItemCatMapper;
import com.ydhd.pixmm.dao.TbItemDescMapper;
import com.ydhd.pixmm.dao.TbItemMapper;
import com.ydhd.pixmm.dao.TbItemParamItemMapper;
import com.ydhd.pixmm.pojo.*;
import com.ydhd.pixmm.service.ItemService;
import com.ydhd.pixmm.utils.IDUtils;
import com.ydhd.pixmm.utils.JsonUtils;
import com.ydhd.pixmm.utils.PixmmResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 王朋波 on 08/08/2017.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER=new ObjectMapper();

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem item=itemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {

        //分页处理
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> list=itemMapper.selectByExample(example);
        //获取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
        //返回处理结果
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public PixmmResult createItem(TbItem item, String desc,String itemParam) {
        // 生成商品id
        long itemId = IDUtils.genItemId();
        // 补全TbItem属性
        item.setId(itemId);
        // '商品状态，1-正常，2-下架，3-删除'
        item.setStatus((byte) 1);
        // 创建时间和更新时间
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 插入商品表
        itemMapper.insert(item);
        // 商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 插入商品描述数据
        itemDescMapper.insert(itemDesc);

        //添加商品规格参数
        TbItemParamItem itemParamItem=new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        itemParamItemMapper.insert(itemParamItem);

        try {

            //synData(item,itemDesc);

            sendMsg(item.getId(),"insert");
        }catch (Exception e){
            e.printStackTrace();
        }

        return PixmmResult.ok();


    }

    @Override
    public String getItemParamHtml(Long itemId) {
        // 根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem>list = itemParamItemMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return"";
        }
        //取规格参数
        TbItemParamItem itemParamItem = list.get(0);
        //取json数据
        String paramData = itemParamItem.getParamData();
        //转换成java对象
        List<Map>mapList = JsonUtils.jsonToList(paramData, Map.class);
        //遍历list生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
            sb.append("		</tr>\n");
            //取规格项
            List<Map>mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
                sb.append("			<td>"+map2.get("v")+"</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();

    }

    @Override
    public TbItemDesc getItemDescByItemId(Long itemId) {
        TbItemDesc itemDesc=new TbItemDesc();
        TbItemDescExample example=new TbItemDescExample();
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemDesc> tbItemDescs = itemDescMapper.selectByExample(example);
        if(tbItemDescs.size()>0){
            itemDesc=tbItemDescs.get(0);
        }
        return itemDesc;
    }

    /**
     * 手动同步商品到索引库
     * @param item
     * @param itemDesc
     */
    private void synData(TbItem item,TbItemDesc itemDesc){
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id",item.getId());
        document.addField("item_title",item.getTitle());
        document.addField("item_sell_point",item.getSellPoint());
        document.addField("item_price",item.getPrice());
        document.addField("item_image",item.getImage());

        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(item.getCid());
        document.addField("item_category_name",itemCat.getName());
        document.addField("item_desc",itemDesc.getItemDesc());

        try {
            //添加文档
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用RabbitMQ实现信息同步
     */
   private void sendMsg(Long itemId,String type){
       try{

           Map<String,Object> map=new HashMap<>();
           map.put("type",type);
           map.put("itemId",itemId);
           map.put("date",System.currentTimeMillis());
           rabbitTemplate.convertAndSend("item."+type,MAPPER.writeValueAsString(map));
       }
       catch (Exception e){
           e.printStackTrace();
       }

   }

}
