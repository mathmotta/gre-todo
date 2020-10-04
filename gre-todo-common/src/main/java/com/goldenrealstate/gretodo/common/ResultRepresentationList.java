package com.goldenrealstate.gretodo.common;

import java.util.List;

/**
 * Base DTO to represent Entity and Paging data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class ResultRepresentationList<T extends AbstractResultRepresentation> {

    private Long total;
    private Integer size;
    private Integer start;
    private List<T> data;

    public ResultRepresentationList(long total, int size, int start, List<T> data) {
        setTotal(total);
        setSize(size);
        setStart(start);
        setData(data);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
