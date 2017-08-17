package com.ydhd.pixmm.pojo;

import java.util.List;

/**
 * Created by 王朋波 on 08/08/2017.
 */
public class EasyUIDataGridResult {
    private long total;
    private List<?> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
