package com.ydhd.pixmm.rest.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ydhd.pixmm.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 王朋波 on 13/08/2017.
 */
@Component("contentFacade")
@Service()
public class ContentFacadeImpl implements com.ydhd.pixmm.rest.facade.ContentFacade {

    @Autowired
    private ContentService contentService;

    @Override
    public String getAdList() {
        return contentService.getAdList();
    }
}
