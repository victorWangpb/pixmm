package com.ydhd.pixmm.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydhd.pixmm.pojo.TbItemDesc;
import com.ydhd.pixmm.rest.facade.ItemFacade;
import com.ydhd.pixmm.rest.pojo.ItemInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 王朋波 on 2017/8/17.
 */
@Service
public class RabbitmqService {

    @Autowired
    private SolrServer solrServer;

    @Reference
//    @Autowired
    private ItemFacade itemFacade;




    private static final ObjectMapper MAPPER=new ObjectMapper();

    public void synSolrItem(String msg){

        try {
            JsonNode jsonNode=MAPPER.readTree(msg);
            Long itemId=jsonNode.get("itemId").asLong();
            String type=jsonNode.get("type").asText();
            if(StringUtils.equals(type,"insert") || StringUtils.equals(type,"update")){
                ItemInfo item = itemFacade.getItemById(itemId);
                String itemDesc=itemFacade.getItemDescById(itemId);
                //创建Document
                SolrInputDocument doc=new SolrInputDocument();
                doc.addField("id",itemId);
                doc.addField("item_title",item.getTitle());
                doc.addField("item_price",item.getPrice());
                doc.addField("item_category_name",itemDesc);
                doc.addField("item_image",item.getImage());
                doc.addField("item_sell_point",item.getSellPoint());

                solrServer.add(doc);

                solrServer.commit();

            }else if(StringUtils.equals(type,"delete")){
                solrServer.deleteById(itemId.toString());
                solrServer.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
