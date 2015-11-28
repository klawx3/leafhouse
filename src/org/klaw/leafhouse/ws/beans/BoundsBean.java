/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.beans;

import javax.ws.rs.QueryParam;

/**
 *
 * @author Klaw Strife
 */
public class BoundsBean {
    
    private @QueryParam("start") int start;
    private @QueryParam("size") int size;
    private @QueryParam("startDate") long startDate;
    private @QueryParam("endDate") long endDate;
    
    public boolean isPaginationSet(){
        return start > 0 && size > 0;
    }
    
    public boolean isDateBoundSet(){
        return startDate > 0 && endDate > 0;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    
    
    
    
    
    
}
