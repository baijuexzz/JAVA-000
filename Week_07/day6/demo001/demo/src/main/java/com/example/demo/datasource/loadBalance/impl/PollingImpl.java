package com.example.demo.datasource.loadBalance.impl;


import com.example.demo.datasource.loadBalance.LoadBalanceService;
import com.example.demo.datasource.loadBalance.LoadBlanceBean;

import java.util.List;


/**
 * 轮询算法实现
 */
public class PollingImpl implements LoadBalanceService {

    /**
     * 设置轮询起始位置
     */
    private Integer beginIndex=0;

    @Override
    public String getDataSourceName(List<LoadBlanceBean> dataSourceList) {
        for (LoadBlanceBean loadBlanceBean : dataSourceList) {
            int nextIndex = (beginIndex + 1) % dataSourceList.size();
            beginIndex=nextIndex;
            break;
        }
        return dataSourceList.get(beginIndex).getDataSourceName();
    }





}
