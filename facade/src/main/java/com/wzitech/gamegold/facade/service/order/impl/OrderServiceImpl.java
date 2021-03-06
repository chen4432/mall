/************************************************************************************
 * Copyright 2013 WZITech Corporation. All rights reserved.
 * <p>
 * 模	块：		OrderServiceImpl
 * 包	名：		com.wzitech.gamegold.facade.service.order.impl
 * 项目名称：	gamegold-facade
 * 作	者：		SunChengfei
 * 创建时间：	2014-2-24
 * 描	述：
 * 更新纪录：	1. SunChengfei 创建于 2014-2-24 下午5:32:28
 ************************************************************************************/
package com.wzitech.gamegold.facade.service.order.impl;

import com.wzitech.chaos.framework.server.common.AbstractBaseService;
import com.wzitech.chaos.framework.server.common.exception.SystemException;
import com.wzitech.chaos.framework.server.dataaccess.pagination.GenericPage;
import com.wzitech.gamegold.common.enums.OrderState;
import com.wzitech.gamegold.common.enums.ResponseCodes;
import com.wzitech.gamegold.common.enums.TradeType;
import com.wzitech.gamegold.common.utils.EncryptHelper;
import com.wzitech.gamegold.facade.service.order.IOrderService;
import com.wzitech.gamegold.facade.service.order.dto.*;
import com.wzitech.gamegold.facade.utils.DESHelper;
import com.wzitech.gamegold.order.business.IOrderConfigManager;
import com.wzitech.gamegold.order.business.IOrderInfoManager;
import com.wzitech.gamegold.order.entity.ConfigResultInfoEO;
import com.wzitech.gamegold.order.entity.OrderInfoEO;
import com.wzitech.gamegold.repository.entity.RepositoryInfo;
import com.wzitech.gamegold.repository.entity.SellerInfo;
import com.wzitech.gamegold.usermgmt.entity.UserInfoEO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.*;

/**
 * @author SunChengfei
 */

/**
 * Created by Administrator on 2017/1/4.
 * <p>
 * Update History
 * Date        Name                Reason for change
 * ----------  ----------------    ----------------------
 * 2017/05/16  lvchengsheng           ZW_C_JB_00008 商城增加通货
 */

@Service("OrderService")
@Path("order")
@Produces("application/xml;charset=gb2312")
@Consumes("application/json;charset=UTF-8")
public class OrderServiceImpl extends AbstractBaseService implements IOrderService {
    @Autowired
    IOrderInfoManager orderInfoManager;

    @Autowired
    IOrderConfigManager orderConfigManager;

    @Value("${encrypt.5173.key}")
    private String encryptKey = "";

    @Path("queryorderlist")
    @GET
    @Override
    public QueryOrderListResponse queryOrderList(@QueryParam("") QueryOrderListRequest queryOrderListRequest,
                                                 @Context HttpServletRequest request) {
        logger.debug("当前查询订单列表:{}", queryOrderListRequest);
        // 初始化返回数据
        QueryOrderListResponse response = new QueryOrderListResponse();
        response.setStatus(false);

        try {
            // 校验MD5
            String toEncrypt = EncryptHelper.md5(String.format("%s_%s_%s", queryOrderListRequest.getRequestNum(),
                    queryOrderListRequest.getQueryType(), encryptKey));
            if (!StringUtils.equals(toEncrypt, queryOrderListRequest.getSign())) {
                return response;
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            //增加按商品类目查询条件 by lvchengsheng 2017.5.15 add ZW_C_JB_00008 商城增加通货
            if (StringUtils.isBlank(queryOrderListRequest.getGoodsTypeName())) {
                queryMap.put("goodsTypeName", "全部");
            } else {
                queryMap.put("goodsTypeName", queryOrderListRequest.getGoodsTypeName());
            }
            queryMap.put("orderState", OrderState.WaitDelivery.getCode());
            queryMap.put("operator", true);
            //queryMap.put("isConsignment", true);// 只获取寄售的单子
            if (queryOrderListRequest.getRequestNum() == null) {
                queryOrderListRequest.setRequestNum(1);
            }
            /*GenericPage<OrderInfoEO> orderPage = orderInfoManager.queryOrderInfo(queryMap, "CREATE_TIME", true, queryOrderListRequest.getRequestNum(), 0);
            List<OrderInfoEO> orders = orderPage.getData();
			if(orders != null && orders.size() > 0){
				List<OrderListItemDTO> listItemDTOs = new ArrayList<OrderListItemDTO>();
				for (OrderInfoEO orderInfoEO : orders) {
					List<ConfigResultInfoEO> configResultInfoEOs = orderConfigManager.orderConfigList(orderInfoEO.getOrderId());
					for (ConfigResultInfoEO configResult : configResultInfoEOs) {
                        // 只获取寄售的单子
                        if (configResult.getIsConsignment() == false) continue;

						//判断子订单状态是否为待发货
						if (configResult.getState().equals(OrderState.WaitDelivery.getCode())) {
							OrderListItemDTO itemDTO = new OrderListItemDTO();
							itemDTO.setId(orderInfoEO.getOrderId() +"_"+ configResult.getId());
							itemDTO.setGn(orderInfoEO.getGameName());
							itemDTO.setGsn(orderInfoEO.getServer());
							itemDTO.setGan(orderInfoEO.getRegion());
							if (configResult.getOptionUser() == null ||
									StringUtils.isEmpty(configResult.getOptionUser().getLoginAccount())) {
								itemDTO.setOpId(orderInfoEO.getServicerInfo().getLoginAccount());
							}else{
								// 如果是系统管理员等配置单，设置为主客服
								if(configResult.getOptionUser().getUserType() != UserType.AssureService.getCode()
										&& configResult.getOptionUser().getUserType() != UserType.MakeOrder.getCode()){
									itemDTO.setOpId(orderInfoEO.getServicerInfo().getLoginAccount());
								}else {
									itemDTO.setOpId(configResult.getOptionUser().getLoginAccount());
								}
							}

							itemDTO.setYxbMall("1");
							itemDTO.setNewRC(true);
							
							listItemDTOs.add(itemDTO);
						}
					}
					
				}
				response.setList(listItemDTOs);
			}*/

            GenericPage<ConfigResultInfoEO> results = orderInfoManager.queryConfigResultInfoList(queryMap, "CREATE_TIME", true, queryOrderListRequest.getRequestNum(), 0);
            List<ConfigResultInfoEO> subOrders = results.getData();
            if (subOrders != null && subOrders.size() > 0) {
                List<OrderListItemDTO> listItemDTOs = new ArrayList<OrderListItemDTO>();
                for (ConfigResultInfoEO configResult : subOrders) {
                    //判断子订单状态是否为待发货
                    if (configResult.getState().equals(OrderState.WaitDelivery.getCode())) {
                        OrderListItemDTO itemDTO = new OrderListItemDTO();
                        itemDTO.setId(configResult.getOrderId() + "_" + configResult.getId());
                        if (configResult.getOrderInfoEO() != null) {
                            itemDTO.setGn(configResult.getOrderInfoEO().getGameName());
                            //卖家所在区服
                            itemDTO.setGsn(configResult.getServer());
                            itemDTO.setGan(configResult.getRegion());
                            //增加通货返回值 add by lvchengsheng 2017.5.16 ZW_C_JB_00008 商城增加通货
                            itemDTO.setGoodsTypeName(configResult.getOrderInfoEO().getGoodsTypeName());
                            itemDTO.setGoodsTypeId(configResult.getOrderInfoEO().getGoodsTypeId());
                            if (StringUtils.isNotBlank(configResult.getOrderInfoEO().getGameNumberId())) {
                                itemDTO.setDigitalId(configResult.getOrderInfoEO().getGameNumberId());
                            }
                            if (null != configResult.getOrderInfoEO().getGameLevel()) {
                                itemDTO.setGameGrade(configResult.getOrderInfoEO().getGameLevel());
                            }
                            //跨区的要设置为1 没有就算了
                            if (!(configResult.getOrderInfoEO().getServer()).equals(configResult.getServer())) {
                                itemDTO.setInterregional("1");
                            }

                        }


                        itemDTO.setYxbMall("1");
                        itemDTO.setNewRC(true);

                        if (configResult.getIsConsignment() != null) {
                            itemDTO.setIsCon(configResult.getIsConsignment());
                        }

                        // 寄售的才设置寄售物服,担保的不能设置
                        if (configResult.getIsConsignment() != null && configResult.getIsConsignment()) {
                            // 不是寄售全自动机器人才设置寄售物服
                            if (configResult.getIsJsRobot() == null || configResult.getIsJsRobot() == false) {
                                if (configResult.getOptionUser() != null) {
                                    itemDTO.setOpId(configResult.getOptionUser().getLoginAccount());
                                }
                            }

                        }

                        listItemDTOs.add(itemDTO);
                    }

                }
                response.setList(listItemDTOs);
            }

            response.setMsg("操作成功");
            response.setStatus(true);
        } catch (SystemException ex) {
            // 捕获系统异常
            response.setMsg(ex.getArgs()[0].toString());
            logger.error("当前查询订单列表发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            response.setMsg("系统发生未知异常");
            logger.error("当前查询订单列表发生未知异常:{}", ex);
        }
        logger.debug("当前查询订单列表响应信息:{}", response);
        return response;
    }

    @Path("transferorder")
    @GET
    @Override
    public TransferOrderResponse transferOrder(@QueryParam("") TransferOrderRequest transferOrderRequest,
                                               @Context HttpServletRequest request) {
        logger.debug("当前订单移交:{}", transferOrderRequest);
        // 初始化返回数据
        TransferOrderResponse response = new TransferOrderResponse();
        response.setDeliverTime(null);
        response.setMsg("移交失败");
        response.setStatus(false);
        response.setYxbMall("1");
        try {
            // 校验MD5
            String decPwd = DESHelper.decrypt(transferOrderRequest.getOppwd(), encryptKey);
            String toEncrypt = EncryptHelper.md5(String.format("%s_%s_%s_%s", transferOrderRequest.getOrderId(),
                    transferOrderRequest.getOpid(), encryptKey, decPwd));

            if (!StringUtils.equals(toEncrypt, transferOrderRequest.getSign())) {
                return response;
            }

			/*//更新配置中的订单状态
            orderConfigManager.updateConfigState(Long.parseLong(this.getSubOrderId(transferOrderRequest.getOrderId())),OrderState.Delivery.getCode());
			
			boolean isDelivery = true;
			List<ConfigResultInfoEO> configList = orderConfigManager.orderConfigList(this.getOrderId(transferOrderRequest.getOrderId()));
			for (ConfigResultInfoEO configResultInfoEO : configList) {
				if (!configResultInfoEO.getState().equals(OrderState.Delivery.getCode())) {
					isDelivery = false;
					break;
				}
			}
			
			if (isDelivery) {
				orderInfoManager.changeOrderState(this.getOrderId(transferOrderRequest.getOrderId()),
						OrderState.Delivery.getCode(), null);
			}*/

            orderInfoManager.transferOrderForRC2(transferOrderRequest.getOrderId(), transferOrderRequest.getOpid());

            response.setDeliverTime(new Date());
            response.setMsg("移交成功");
            response.setStatus(true);

        } catch (SystemException ex) {
            // 捕获系统异常
            logger.error("当前订单移交发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            logger.error("当前订单移交发生未知异常:{}", ex);
        }
        logger.debug("当前订单移交响应信息:{}", response);
        return response;
    }

    @Path("cancelorder")
    @GET
    @Override
    public CancelOrderResponse cancelOrder(@QueryParam("") CancelOrderRequest cancelOrderRequest,
                                           @Context HttpServletRequest request) {
        logger.debug("当前取消订单:{}", cancelOrderRequest);
        // 初始化返回数据
        CancelOrderResponse response = new CancelOrderResponse();
        response.setStatus(false);
        response.setMsg("操作失败");
        response.setYxbMall("1");
        try {
            //校验MD5
            String decPwd = DESHelper.decrypt(cancelOrderRequest.getOppwd(), encryptKey);
            String toEncrypt = EncryptHelper.md5(String.format("%s_%s_%s_%s", cancelOrderRequest.getOrderId(),
                    cancelOrderRequest.getOpid(), encryptKey, decPwd));

            if (!StringUtils.equals(toEncrypt, cancelOrderRequest.getSign())) {
                return response;
            }

			/*//更新配置中的订单状态
            orderConfigManager.updateConfigState(Long.parseLong(this.getSubOrderId(cancelOrderRequest.getOrderId())),OrderState.Cancelled.getCode());
			
			// 退款操作
			if(cancelOrderRequest.getReconfig() != 1){
				boolean isDelivery = false;
				List<ConfigResultInfoEO> configList = orderConfigManager.orderConfigList(this.getOrderId(cancelOrderRequest.getOrderId()));
				for (ConfigResultInfoEO configResultInfoEO : configList) {
					if (configResultInfoEO.getState().equals(OrderState.Delivery.getCode())) {
						isDelivery = true;
						break;
					}
				}
				
				if(!isDelivery){
					orderInfoManager.changeOrderState(this.getOrderId(cancelOrderRequest.getOrderId()), OrderState.Refund.getCode(),
							null);
				}
			}
			if(StringUtils.isNotBlank(this.getOrderId(cancelOrderRequest.getOrderId()))&&StringUtils.isNotBlank(cancelOrderRequest.getCancelRemark())){
				String cancelRemark = new String(Base64.decode(cancelOrderRequest.getCancelRemark()), "GB2312");
				orderInfoManager.saveOrederCancelReason(this.getOrderId(cancelOrderRequest.getOrderId()), cancelRemark);
			}*/

            orderInfoManager.cancelOrder(cancelOrderRequest.getOrderId(), cancelOrderRequest.getReconfig(), cancelOrderRequest.getCancelRemark(), cancelOrderRequest.getOpid());
            response.setStatus(true);
            response.setMsg("操作成功");
        } catch (SystemException ex) {
            // 捕获系统异常
            logger.error("当前取消订单发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            logger.error("当前取消订单发生未知异常:{}", ex);
        }
        logger.debug("当前取消订单响应信息:{}", response);
        return response;
    }

    @Path("cancelreason")
    @GET
    @Override
    public String cancelOrderReason(@Context HttpServletRequest request) {
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/classes/META-INF/OrderCancelReason.xml");
//            in = new FileInputStream(this.getClass().getResource("/").getPath()+"META-INF/OrderCancelReason.xml");
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return out.toString().replace("utf-8", "gb2312");
    }

    @Path("queryorderstate")
    @GET
    @Override
    public QueryOrderStateResponse queryOrderState(@QueryParam("") QueryOrderStateRequest queryOrderStateRequest,
                                                   @Context HttpServletRequest request) {
        logger.debug("当前查询订单状态订单:{}", queryOrderStateRequest);
        // 初始化返回数据
        QueryOrderStateResponse response = new QueryOrderStateResponse();
        response.setMsg("验证失败");
        response.setOrderStatus(null);
        response.setStatus(false);
        response.setYxbMall("1");
        try {
            // 校验MD5
            String decPwd = DESHelper.decrypt(queryOrderStateRequest.getOppwd(), encryptKey);
            String toEncrypt = EncryptHelper.md5(String.format("%s_%s_%s_%s", queryOrderStateRequest.getOpid(),
                    decPwd, queryOrderStateRequest.getOrderId(), encryptKey));

            if (!StringUtils.equals(toEncrypt, queryOrderStateRequest.getSign())) {
                return response;
            }

            OrderInfoEO orderInfoEO = orderInfoManager.selectById(this.getOrderId(queryOrderStateRequest.getOrderId()));

            response.setMsg("验证成功");
            response.setOrderStatus(orderInfoEO.getOrderState().toString());
            response.setStatus(true);
        } catch (SystemException ex) {
            // 捕获系统异常
            logger.error("当前查询订单状态发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            logger.error("当前查询订单状态发生未知异常:{}", ex);
        }
        logger.debug("当前查询订单状态响应信息:{}", response);
        return response;
    }

    @Path("queryorderinfo")
    @GET
    @Override
    public String queryOrderInfo(@QueryParam("") QueryOrderInfoRequest queryOrderInfoRequest,
                                 @Context HttpServletRequest request) {
        logger.debug("当前查询订单:{}", queryOrderInfoRequest);
        // 初始化返回数据
        QueryOrderInfoResponse response = new QueryOrderInfoResponse();
        response.setYxbMall("1");
        try {
//            // 校验MD5
            String decPwd = DESHelper.decrypt(queryOrderInfoRequest.getOppwd(), encryptKey);
            String toEncrypt = EncryptHelper.md5(String.format("%s_%s_%s_%s", queryOrderInfoRequest.getOrderId(),
                    queryOrderInfoRequest.getOpid(), encryptKey, decPwd));

            if (!StringUtils.equals(toEncrypt, queryOrderInfoRequest.getSign())) {
                response.setMsg("验证失败");
                response.setStatus(false);

                JAXBContext jaxbContext = JAXBContext.newInstance(QueryOrderInfoResponse.class);
                Marshaller jaxbmarshaller = jaxbContext.createMarshaller();
                jaxbmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter writer = new StringWriter();
                jaxbmarshaller.marshal(response, writer);

                return DESHelper.encrypt(writer.toString(), encryptKey);
            }

            ConfigResultInfoEO configResult = orderConfigManager.orderConfigById(Long.parseLong(this.getSubOrderId(queryOrderInfoRequest.getOrderId())));
            if (!(queryOrderInfoRequest.getOpid()).equals(configResult.getOptionUser().getLoginAccount())) {
                throw new SystemException(ResponseCodes.ErrorOperatorIdWithOrder.getCode(), ResponseCodes.ErrorOperatorIdWithOrder.getMessage());
            }

            OrderInfoEO orderInfoEO = configResult.getOrderInfoEO();
            UserInfoEO servicerEO = orderInfoEO.getServicerInfo();

            response.setOrderId(orderInfoEO.getOrderId() + "_" + configResult.getId());
            if (StringUtils.isNotBlank(orderInfoEO.getField())) {
                response.setFields(orderInfoEO.getField());
            } else {
                response.setFields("");
            }
            //只有
            if (!(orderInfoEO.getServer()).equals(configResult.getServer())) {
                response.setInterregional("1");
            }
            //买家所在区服
            response.setBuyerGameAreaServer(orderInfoEO.getServer());
            response.setBuyerGameAreaName(orderInfoEO.getRegion());
            response.setCreateDate(orderInfoEO.getCreateTime());
            response.setBizOfferId(String.valueOf(orderInfoEO.getGoodsId()));
            response.setBizOfferName(orderInfoEO.getTitle());
            response.setGameName(orderInfoEO.getGameName());
            //卖家所在区服
            response.setGameAreaName(configResult.getRegion());
            response.setGameAreaServer(configResult.getServer());
            response.setGameRaceName(orderInfoEO.getGameRace());
            response.setBizOfferTypeName(orderInfoEO.getGoodsTypeName());
            response.setBuyerGameRole(orderInfoEO.getReceiver());
            response.setQuantity(configResult.getConfigGoldCount());
            response.setMoneyUnitName(orderInfoEO.getMoneyName());
            response.setSellMoney(orderInfoEO.getGoldCount());
            //增加通货类目返回值 add by lvchengsheng 2017.5.16 ZW_C_JB_00008 商城增加通货
            response.setGoodsTypeName(orderInfoEO.getGoodsTypeName());
            response.setGoodsTypeId(orderInfoEO.getGoodsTypeId());
            response.setOriginalPrice(orderInfoEO.getTotalPrice().doubleValue());
            response.setPrice(orderInfoEO.getTotalPrice().doubleValue());
            String loginAccount = servicerEO == null ? "" : servicerEO.getLoginAccount();
            response.setDeliverOpId(loginAccount);
            response.setOrderStatus(orderInfoEO.getOrderState().toString());
            response.setBizOfferContent(orderInfoEO.getTitle());
            response.setServerTime(new Date());
            response.setCurrentTime(new Date());
            if (StringUtils.isNotBlank(orderInfoEO.getGameNumberId())) {
                response.setDigitalId(orderInfoEO.getGameNumberId());
            }
            if (null != orderInfoEO.getGameLevel()) {
                response.setGameGrade(orderInfoEO.getGameLevel());
            }
            if (orderInfoEO.getDeliveryTime() != null) {
                response.setDelayTime(String.valueOf(orderInfoEO.getDeliveryTime() * 60));
            }
            response.setTradeSpeedTime(String.valueOf(orderInfoEO.getDeliveryTime() * 60));
            response.setAssignDeliverOpTime(orderInfoEO.getCreateTime());
            response.setBuyerName(orderInfoEO.getUserAccount());
            response.setBuyerTel(orderInfoEO.getMobileNumber());
            response.setBuyerQQ(orderInfoEO.getQq());

            if (orderInfoEO.getTradeType() != null) {
                if (orderInfoEO.getTradeType() == TradeType.NoDivid.getCode()) {
                    // 当面交易
                    response.setCustomBuyPatterns("当面");
                    response.setNamedPlacesOfDelivery(orderInfoEO.getTradePlaceEO().getPlaceName());
                } else {
                    response.setCustomBuyPatterns("邮寄");
//                response.setNamedPlacesOfDelivery(orderInfoEO.getTradePlaceEO().getPlaceName());
                }
            }

            RepositoryInfo repositoryInfo = configResult.getRepositoryInfo();
            SellerInfo seller = repositoryInfo.getSellerInfo();

            String sellerGameRoles = repositoryInfo.getSellerGameRole();
            String sonPassWord = "";
            if (!StringUtils.isEmpty(repositoryInfo.getSonGamePassWord())) {
                sonPassWord = repositoryInfo.getSonGamePassWord();
            }
            String accountRegInfos = ("游戏帐号：" + repositoryInfo.getGameAccount() + "；游戏密码：" + repositoryInfo.getGamePassWord() + "；二级密码：" + sonPassWord + "；游戏角色名："
                    + repositoryInfo.getSellerGameRole() + "；交易商品数量：" + configResult.getConfigGoldCount() + "；");

            String sellerNames = "";
            String sellerTels = "";
            String sellerQQ = "";
            if (null != seller) {
                sellerNames = seller.getName();
                sellerTels = seller.getPhoneNumber();
                sellerQQ = seller.getQq();
            }

            if (StringUtils.isEmpty(repositoryInfo.getPasspodUrl())) {
                response.setHasPasspod(false);
            } else {
                response.setHasPasspod(true);
            }

            response.setSellerGameRoles(sellerGameRoles);
//            response.setAccountRegInfos(accountRegInfos);
            response.setAccountRegInfos(accountRegInfos);
            response.setSellerNames(sellerNames);
            response.setSellerMobiles(sellerTels);
            response.setSellerQQs(sellerQQ);
            response.setAccount(repositoryInfo.getGameAccount());
            response.setPassword("");

            //start add by 汪俊杰 20170511 用于环信一对一vpn判断
            response.setSellerLoginAccount(configResult.getLoginAccount());
            //end

            response.setMsg("获取成功");
            response.setStatus(true);

            JAXBContext jaxbContext = JAXBContext.newInstance(QueryOrderInfoResponse.class);
            Marshaller jaxbmarshaller = jaxbContext.createMarshaller();
            jaxbmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            jaxbmarshaller.marshal(response, writer);

            logger.info("RC2订单详情返回数据：" + writer.toString());

            return DESHelper.encrypt(writer.toString(), encryptKey);
        } catch (SystemException ex) {
            // 捕获系统异常
            logger.error("当前查询订单发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            logger.error("当前查询订单发生未知异常:{}", ex);
        }
        logger.debug("当前查询订单响应信息:{}", response);
        return null;
    }

    /**
     * 获取订单号
     *
     * @param id
     * @return
     */
    private String getOrderId(String id) {
        if (id.length() >= 23) {
            return id.substring(0, 15);
        }
        return id.substring(0, 13);
    }

    /**
     * 获取子订单号
     *
     * @param id
     * @return
     */
    private String getSubOrderId(String id) {
        if (id.length() >= 23) {
            return id.substring(16);
        }
        return id.substring(14);
    }

}
