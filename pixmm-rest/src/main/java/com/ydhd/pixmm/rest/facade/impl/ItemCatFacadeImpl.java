package com.ydhd.pixmm.rest.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ydhd.pixmm.rest.facade.ItemCatFacade;
import com.ydhd.pixmm.rest.pojo.ItemCatResult;
import com.ydhd.pixmm.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 王朋波 on 12/08/2017.
 */
@Component("itemCatFacade")
@Service
public class ItemCatFacadeImpl implements ItemCatFacade {

    @Autowired
    private ItemCatService itemCatService;

    @Override
    public ItemCatResult getItemCatList() {
        return itemCatService.getItemCatList();
    }
}
