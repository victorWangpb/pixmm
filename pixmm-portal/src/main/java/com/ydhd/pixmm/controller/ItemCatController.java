package com.ydhd.pixmm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ydhd.pixmm.rest.facade.ItemCatFacade;
import com.ydhd.pixmm.rest.pojo.ItemCatResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 王朋波 on 12/08/2017.
 */

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Reference
    private ItemCatFacade itemCatFacade;

    @RequestMapping(value="/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        ItemCatResult result = itemCatFacade.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            //需要把result转换成字符串
            return result;
        }
        //如果字符串不为空，需要支持jsonp调用
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;


    }
}
