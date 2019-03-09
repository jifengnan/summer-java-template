package com.github.jifengnan.template.base.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class provides some common paging conditions.
 *
 * @author jifengnan  2018-12-03
 */
@Getter
public class PageCondition {

    /**
     * current page number
     */
    private int pageNum;

    /**
     * The data number per page
     */
    private int pageSize = 10;

    /**
     * The offset to query
     */
    private int offset;

    /**
     * Whether to count the total number of the matched data
     */
    @Setter
    private boolean toCount;

    public void setPageNum(int pageNum) {
        if (pageNum <= 0) {
            throw new IllegalArgumentException("The page number must be a positive integer");
        }
        this.pageNum = pageNum;
        this.offset = (pageNum - 1) * pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("The page size must be a positive integer");
        }
        this.pageSize = pageSize;
        this.offset = (pageNum - 1) * pageSize;
    }

}
