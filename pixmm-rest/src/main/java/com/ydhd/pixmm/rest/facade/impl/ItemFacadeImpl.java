package com.ydhd.pixmm.rest.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ydhd.pixmm.pojo.TbItem;
import com.ydhd.pixmm.pojo.TbItemDesc;
import com.ydhd.pixmm.pojo.TbItemParamItem;
import com.ydhd.pixmm.rest.facade.ItemFacade;
import com.ydhd.pixmm.rest.pojo.ItemInfo;
import com.ydhd.pixmm.rest.service.ItemService;
import com.ydhd.pixmm.utils.JsonUtils;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 王朋波 on 2017/8/15.
 */
@Component("itemFacade")
@Service
public class ItemFacadeImpl implements ItemFacade {

    @Autowired
    private ItemService itemService;

    @Override
    public ItemInfo getItemById(Long itemId) {
        return itemService.getItemById(itemId);
    }

    @Override
    public String getItemDescById(Long itemId) {
        return itemService.getItemDescById(itemId);
    }

    @Override
    public String getItemParamById(Long itemId) {
        String paramJson = itemService.getItemParamById(itemId);
        // 把规格参数的json数据转换成java对象
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
        // 遍历list生成html
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");
            // 取规格项
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();

    }
}
