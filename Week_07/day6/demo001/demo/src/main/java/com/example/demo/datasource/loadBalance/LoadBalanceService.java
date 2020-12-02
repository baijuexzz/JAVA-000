package com.example.demo.datasource.loadBalance;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡算法实现接口
 */
public interface LoadBalanceService {

    String getDataSourceName(List<LoadBlanceBean> dataSourceList);
}
