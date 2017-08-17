package com.ydhd.pixmm.rest.pojo;

import com.ydhd.pixmm.pojo.TbItem;

import java.io.Serializable;

/**
 * Created by 王朋波 on 2017/8/15.
 */
public class ItemInfo extends TbItem implements Serializable{
    public String[] getImages(){
        String images=this.getImage();
        if(images !=null && !images.equals("")){
            String[] imgs=images.split(",");
            return imgs;
        }
        return null;
    }
}
