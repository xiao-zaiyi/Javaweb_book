package com.august.pojo;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-01-09   14:21
 */
public class Page<T> {

    public static int PAGE_SIZE = 4;

    // 每条显示的数量
    private Integer pageSize = PAGE_SIZE;
    // 当前页码
    private Integer pageNo;
    //   页码总数
    private Integer pageTotal;
    //  总记录数
    private Integer pageTotalCount;
    // 当前页的数据
    private List<T> items;

    private String url;



    public Page() {
    }

    public Page(Integer pageSize, Integer pageNo, Integer pageTotal, Integer pageTotalCount, List<T> items) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
//        数字边界检测
        if (pageNo < 0) {
            pageNo = 1;
        }
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }
}
