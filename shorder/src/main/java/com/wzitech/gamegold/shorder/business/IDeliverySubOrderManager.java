package com.wzitech.gamegold.shorder.business;

import com.wzitech.chaos.framework.server.dataaccess.pagination.GenericPage;
import com.wzitech.gamegold.shorder.entity.DeliverySubOrder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 出货子订单管理
 */
public interface IDeliverySubOrderManager {
    /**
     * 查询等待发送给机器人的订单
     *
     * @return
     */
    List<DeliverySubOrder> queryWaitForProcessOrders();

    /**
     * 改变订单状态
     *
     * @param id     出货子订单ID
     * @param status
     */
    void changeState(long id, int status);

    /**
     * 查找所有符合条件的数据
     *
     * @param paramMap
     * @return
     */
    List<DeliverySubOrder> querySubOrders(Map<String, Object> paramMap);

    /**
     * 分页查找订单
     *
     * @param map
     * @param start
     * @param pageSize
     * @param orderBy
     * @param isAsc
     * @return
     */
    GenericPage<DeliverySubOrder> queryListInPage(Map<String, Object> map, int start, int pageSize, String orderBy, boolean isAsc);

    /**
     * 根据主订单id查找子订单列表
     *
     * @param id
     * @return
     */
    List<DeliverySubOrder> querySubOrdersByOrderId(Long id);

    /**
     * 查询账号是否在交易中
     *
     * @param buyerAccount
     * @param buyerId
     * @param gameName
     * @param regionName
     * @param serverName
     * @param raceName
     * @param gameAccount
     * @return
     */
    boolean queryGameAccountIsInTrading(String buyerAccount, String buyerId, String gameName, String regionName,
                                        String serverName, String raceName, String gameAccount);



    /**
     * 查找异常转人工订单
     * @param queryMap
     * @return
     */
    List<DeliverySubOrder> queryMachineAbnormalTurnManualOrderList(Map<String,Object> queryMap);

    /**
     * 查询交易超时的订单(一直未点上游戏交易或是一直在排队的订单，超过30分钟后将被视为交易超时)
     *
     * @return
     */
    List<Long> queryWithdrawalTradeTimeoutOrders();
    /**
     * 推送消息实现
     * @param orderId
     * @param message
     */
    void pushMessage(String orderId,String message);

    /**
     * 根据订单唯一标识 id查询订单
     */
    DeliverySubOrder queryUniqueSubOrder(Long id);
    /**
     *取消订单
     * @param orderId
     */
    void cancellationOfOrder(String orderId ,String reason,String remarks);

    void robotWithdrawalOrder(long id) throws IOException;

    /**
     * 根据订单id查询子订单列表 并锁表
     * @param orderId
     * @return
     */
    List<DeliverySubOrder> queryAllByOrderIdForUpdate(String orderId);

    /**
     * 查询交易中订单
     *
     * @param chId
     * @param locked
     * @return
     */
    List<DeliverySubOrder> queryWaitForTradeOrders(Long chId, Boolean locked);

    DeliverySubOrder selectByOrderId(Long id,String orderId);


    /**
     * 查询主订单下所有子订单的输入发货数量
     * @param id
     * */
    Long queryInputCount(Long id);

    /**
     * 根据子订单id查询子订单信息
     * */
    DeliverySubOrder findSubOrderById(Long id);


    /**
     * 查询
      * @param id
     * @return
     */
    Integer getOrderStatus(Long id);

    /**
     * according to appealOrder to find subOrder details
     * */
    DeliverySubOrder findSubOrderByAppealOrder(String appealOrder);

    DeliverySubOrder findSubOrderByOrderId(String orderId);

    /**
     * 自动化响应超时订单
     */
    List<Long> queryMachineSellerDeliveryTimeoutOrders();
    /**
     * 自动化响应超时订单
     */
    List<Long> queryAllMachineSellerDeliveryTimeoutOrders();
}
