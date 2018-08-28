package com.wzitech.gamegold.shrobot.service.order.impl;

import com.wzitech.chaos.framework.server.common.AbstractBaseService;
import com.wzitech.chaos.framework.server.common.IServiceResponse;
import com.wzitech.chaos.framework.server.common.ResponseStatus;
import com.wzitech.chaos.framework.server.common.exception.SystemException;
import com.wzitech.chaos.framework.server.common.utils.BeanMapper;
import com.wzitech.gamegold.common.enums.*;
import com.wzitech.gamegold.common.utils.SignHelper;
import com.wzitech.gamegold.shorder.business.*;
import com.wzitech.gamegold.shorder.dao.*;
import com.wzitech.gamegold.shorder.dto.RobotFCRequest;
import com.wzitech.gamegold.shorder.entity.*;
import com.wzitech.gamegold.shorder.enums.PicSourceEnum;
import com.wzitech.gamegold.shorder.enums.ShowUserImgEnum;
import com.wzitech.gamegold.shorder.enums.SplitRepositoryStatus;
import com.wzitech.gamegold.shrobot.dto.BaseResponse;
import com.wzitech.gamegold.shrobot.service.order.IOrderService;
import com.wzitech.gamegold.shrobot.service.order.dto.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单service
 *
 * @author yemq
 */
@Service("SHOrderService")
@Path("/order")
@Produces("application/json;charset=UTF-8")
public class OrderServiceImpl extends AbstractBaseService implements IOrderService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private IDeliveryOrderLogManager deliveryOrderLogManager;
    @Autowired
    private IDeliveryOrderManager deliveryOrderManager;
    //    @Autowired
//    private IDeliveryOrderFinishManager deliveryOrderFinishManager;
    @Autowired
    private IGtrOrderManager gtrOrderManager;

    @Autowired
    IDeliverySubOrderDao deliverySubOrderDao;
    @Autowired
    IRobotImgDAO robotImgDAO;

    @Autowired
    IAutomationManager automationManager;

    @Autowired
    ISystemConfigManager systemConfigManager;

    @Autowired
    IExistLogRedis existLogRedis;


    @Autowired
    ISplitRepositoryRequestManager splitRepositoryRequestManager;

    @Autowired
    ISplitRepositoryRequestDao splitRepositoryRequestDao;
    //
    @Autowired
    IRepositorySplitRequestManager repositorySplitRequestManager;

    @Autowired
    ISplitRepositoryLogManager splitRepositoryLogManager;

    @Autowired
    IStockManager stockManager;

//    @Autowired
//    private IShStoreManager shStoreManager;
    /**
     * 签名KEY
     */
    @Value("${shrobot.sign_key}")
    private String signKey = "";
    /**
     * 加密KEY
     */
    @Value("${shrobot.secret_key}")
    private String secretKey = "";

    /**
     * 收货订单列表
     *
     * @param params
     * @param request
     * @return
     */
    @Override
    @Path("/list")
    @GET
    public IServiceResponse orderList(@QueryParam("") OrderListRequest params, @Context HttpServletRequest request) {
        OrderListResponse response = new OrderListResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
//        try {
//            if (params == null) {
//                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
//                        ResponseCodes.EmptyParams.getMessage());
//            }
//            if (StringUtils.isBlank(params.getSign())) {
//                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
//            }
//            String orderId = params.getOrderId();
//            Long chId = params.getId();
//            Integer pageSize = params.getPageSize();
//            if (pageSize == null) {
//                pageSize = Integer.valueOf(50);
//            }
//
//            Map<String, String> map = new LinkedHashMap<String, String>();
//            map.put("pageSize", String.valueOf(pageSize));
//            map.put("orderId", orderId);
//            map.put("id", chId == null ? "" : String.valueOf(chId));
//            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
//            if (!StringUtils.equals(params.getSign(), sign2)) {
//                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
//            }
//
//            Map<String, Object> queryMap = new HashMap<String, Object>();
//            queryMap.put("pageSize", String.valueOf(pageSize));
//            if (StringUtils.isNotBlank(orderId)) {
//                queryMap.put("orderId", orderId);
//                queryMap.put("id", chId);
//            } else {
//                queryMap.put("status", DeliveryOrderStatus.TRADING.getCode());
//            }
//            //查询不是机器转人工的订单
//            queryMap.put("statusNotAutoMate", true);
//
//            List<GtrShOrder> list = gtrOrderManager.queryShOrderListInPage(queryMap, 0, pageSize, "id", true);
//            response.setOrders(list);
//            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
//        } catch (SystemException ex) {
//            // 捕获未知异常
//            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
//            logger.error("采购订单列表接口发生异常", ex);
//        }
        return response;
    }

//    /**
//     * 订单交易完成接口
//     *
//     * @param params
//     * @return
//     */
//    @Override
//    @Path("/finish")
//    @POST
//    public IServiceResponse finish(OrderFinishRequest params) {
//        logger.info("订单完成接口参数:{}", params);
//        BaseResponse response = new BaseResponse();
//        ResponseStatus responseStatus = new ResponseStatus();
//        response.setResponseStatus(responseStatus);
//        try {
//            if (params == null) {
//                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
//                        ResponseCodes.EmptyParams.getMessage());
//            }
//            if (StringUtils.isBlank(params.getOrderId())) {
//                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
//                        ResponseCodes.EmptyOrderId.getMessage());
//            }
//            if (params.getType() == null) {
//                throw new SystemException(ResponseCodes.EmptyType.getCode(), ResponseCodes.EmptyType.getMessage());
//            }
//            if (params.getReceiveCount() == null) {
//                throw new SystemException(ResponseCodes.EmptyReceivingCount.getCode(),
//                        ResponseCodes.EmptyReceivingCount.getMessage());
//            }
//            if (StringUtils.isBlank(params.getServiceId())) {
//                throw new SystemException(ResponseCodes.UnknowShOrderType.getCode(), ResponseCodes.UnknowShOrderType.getMessage());
//            }
//            if (StringUtils.isBlank(params.getSign())) {
//                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
//            }
//
//            Map<String, String> map = new LinkedHashMap<String, String>();
//            map.put("orderId", params.getOrderId());
//            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
//            map.put("serviceId", params.getServiceId());
//            map.put("offline", params.getOffline() == null ? "" : String.valueOf(params.getOffline()));
//            map.put("type", params.getType() == null ? "" : String.valueOf(params.getType()));
//            map.put("receiveCount", params.getReceiveCount() == null ? "" : String.valueOf(params.getReceiveCount()));
//            map.put("remark", params.getRemark());
//            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
//            if (!StringUtils.equals(params.getSign(), sign2)) {
//                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
//            }
//            //start ZW_C_JB_00004 sunyang
//            DeliveryOrder byOrderId = deliveryOrderManager.getByOrderId(params.getOrderId());
//            logger.info("订单交易完成接口DeliveryOrder:{}", byOrderId);
//            if (byOrderId.getMachineArtificialStatus() != null || byOrderId.getStatus() != DeliveryOrderStatus.TRADING.getCode()) {
//                throw new SystemException(ResponseCodes.NofinishOrder.getCode(),
//                        ResponseCodes.NofinishOrder.getMessage());
//            }
//            //end ZW_C_JB_00004 sunyang
//            if (params.getServiceId().equals(ShServiceType.COMPLETE.getCode()) || params.getServiceId().equals(ShServiceType.COMPLETE_PART.getCode())) {
//                shStoreManager.complete(params.getOrderId(), params.getId(), params.getServiceId(),
//                        params.getType(), params.getReceiveCount(), params.getRemark(), params.getOffline());
//            } else if (params.getServiceId().equals(ShServiceType.CANCEL.getCode()) || params.getServiceId().equals(ShServiceType.MANUAL_INTERVENTION.getCode())) {
//                //start ZW_C_JB_00004 sunyang
//                try {
//                    logger.info("撤单/人工介入接口DeliveryOrder:{},serviceId:{}", new Object[]{byOrderId, params.getServiceId()});
//                    //自动分配物服
//                    shStoreManager.voluntarilyRC(params.getOrderId(), params.getId(), params.getServiceId()
//                            , params.getRemark(), params.getOffline(), params.getType(), params.getReceiveCount());
//                } catch (Exception e) {
//                    deliveryOrderManager.setStartByOrderForUpdata(params.getOrderId(), params.getType());
//                }
//                //end ZW_C_JB_00004 sunyang
//            } else if (params.getServiceId().equals(ShServiceType.UPDATE_STORE.getCode())) {
//                deliveryOrderFinishManager.chanageReceiveCount(params.getOrderId(), params.getId(), params.getServiceId(), params.getReceiveCount(), params.getOffline());
//            }
////            deliveryOrderFinishManager.finish(params.getOrderId(),params.getId(), params.getType(), params.getReceiveCount(), params.getRemark());
//            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
//        } catch (
//                SystemException ex)
//
//        {
//            // 捕获系统异常
//            String msg = null;
//            if (ArrayUtils.isEmpty(ex.getArgs())) {
//                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
//            } else {
//                msg = ex.getArgs()[0];
//            }
//            responseStatus.setStatus(ex.getErrorCode(), msg);
//            logger.error("订单完成接口发生异常", ex);
//        } catch (
//                Exception ex)
//
//        {
//            // 捕获未知异常
//            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
//            logger.error("订单完成接口发生异常", ex);
//        }
//
//        return response;
//    }

    /**
     * 订单写日志接口
     *
     * @param params
     * @return
     */
    @Path("/writelog")
    @POST
    @Override
    public IServiceResponse writeLog(@FormParam("") WriteOrderLogRequest params) {
        logger.info("自动化GTR打印日志信息:{}", params);
        BaseResponse response = new BaseResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (StringUtils.isBlank(params.getOrderId())) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (params.getId() == 0 || params.getId() == null) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
//            if (params.getType() == null) {
//                throw new SystemException(ResponseCodes.EmptyType.getCode(), ResponseCodes.EmptyType.getMessage());
//            }
            if (StringUtils.isBlank(params.getLog())) {
                throw new SystemException(ResponseCodes.EmptyLogOperateInfo.getCode(),
                        ResponseCodes.EmptyLogOperateInfo.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }

            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("orderId", params.getOrderId());
//            map.put("type", String.valueOf(params.getType()));
//            map.put("role_name", params.getRoleName());
//            map.put("level", params.getLevel());
//            map.put("img", params.getImg());
//            map.put("count", (params.getCount() == null) ? null : params.getCount().toString());
            map.put("log", params.getLog());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
//            //start ZW_C_JB_00004 sunyang
//            DeliveryOrder byOrderId = deliveryOrderManager.getByOrderId(params.getOrderId());
//            if (byOrderId.getMachineArtificialStatus() != null) {
//                throw new SystemException(ResponseCodes.NoCreatLog.getCode(),
//                        ResponseCodes.NoCreatLog.getMessage());
//            }
            //end ZW_C_JB_00004 sunyang
            DeliverySubOrder deliverySubOrder = deliverySubOrderDao.selectById(params.getId());
            logger.info("订单写日志接口,deliverySubOrder:{}", deliverySubOrder);
            if (deliverySubOrder == null || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferSuccess.getCode() || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferFailed.getCode()) {
                responseStatus.setStatus(ResponseCodes.MachineArtificial.getCode(), ResponseCodes.MachineArtificial.getMessage());
                return response;
            }
            if (deliverySubOrder.getStatus() != DeliveryOrderStatus.TRADING.getCode()) {
                responseStatus.setStatus(ResponseCodes.NotTRADING.getCode(), ResponseCodes.NotTRADING.getMessage());
                return response;
            }
            OrderLog orderLog = BeanMapper.map(params, OrderLog.class);
            String mainOrderId = params.getOrderId();
            Long id = params.getId();
            orderLog.setOrderId(mainOrderId);
            orderLog.setSubId(id);
            deliveryOrderLogManager.writeLog(orderLog);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);
            logger.error("订单写日志发生异常", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("订单写日志发生异常", ex);
        }

        return response;
    }

    /**
     * 自动化异常转人工接口
     *
     * @param params
     * @return
     */
    @Path("/manual")
    @POST
    @Override
    public IServiceResponse RobotExceptional(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印异常信息:{}", params);
        BaseResponse response = new BaseResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == 0 || params.getId() == null) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getOtherReason())) {
                throw new SystemException(ResponseCodes.RobotExceptional.getCode(),
                        ResponseCodes.RobotExceptional.getMessage());
            }
            if (params.getTradeLogo() == 0 || params.getTradeLogo() == null) {
                throw new SystemException(ResponseCodes.SellerIsNull.getCode(),
                        ResponseCodes.SellerIsNull.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("otherReason", params.getOtherReason());
            map.put("tradeLogo", params.getTradeLogo() == null ? "" : String.valueOf(params.getTradeLogo()));
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            //判断该笔订单是否在交易中
            DeliverySubOrder deliverySubOrder = deliverySubOrderDao.selectById(params.getId());
            logger.info("订单自动化转人工操作,deliverySubOrder:{}", deliverySubOrder);
            if (deliverySubOrder == null || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferSuccess.getCode() || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferFailed.getCode()) {
                responseStatus.setStatus(ResponseCodes.MachineArtificial.getCode(), ResponseCodes.MachineArtificial.getMessage());
                return response;
            }
            if (deliverySubOrder.getStatus() != DeliveryOrderStatus.TRADING.getCode()) {
                responseStatus.setStatus(ResponseCodes.NotTRADING.getCode(), ResponseCodes.NotTRADING.getMessage());
                return response;
            }
            //异常问题逻辑处理
            automationManager.automationExceptional(params.getId(), params.getOtherReason());
            logger.info("自动化异常转人工!");
//            Integer exceptionFromUserClick = 2;
//            deliveryOrderManager.subOrderAutoDistributionManager(params.getId(),exceptionFromUserClick);
            logger.info("自动化异常转人工成功!");
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException e) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(e.getArgs())) {
                msg = ResponseCodes.getResponseByCode(e.getErrorCode()).getMessage();
            } else {
                msg = e.getArgs()[0];
            }
            responseStatus.setStatus(e.getErrorCode(), msg);
            logger.error("自动化转人工发生异常", e);
        } catch (Exception e) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("自动化发生异常时调我们接口发生异常", e);
        }
        return response;
    }


    /**
     * 自动化图片接口
     *
     * @param params
     * @return
     */
    @Path("/RobotImg")
    @POST
    @Override
    public IServiceResponse RobotImg(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印图片信息:{}", params);
        BaseResponse response = new BaseResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == 0 || params.getId() == null) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getOrderId())) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            if (params.getType() == 0) {
                throw new SystemException(ResponseCodes.SHOWIMG.getCode(),
                        ResponseCodes.SHOWIMG.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("orderId", params.getOrderId());
            map.put("imgSrc", params.getImgSrc());
            map.put("auctionImg", params.getAuctionImg());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            //传过来的信息存入数据库
            RobotImgEO robotImgEO = new RobotImgEO();
            robotImgEO.setSubOrderId(params.getId());
            robotImgEO.setOrderId(params.getOrderId());
            robotImgEO.setCreateTime(new Date());
            if (params.getType() == 1) {
                robotImgEO.setType(ShowUserImgEnum.SHOW_IMG.getCode());
            } else if (params.getType() == 2) {
                robotImgEO.setType(ShowUserImgEnum.NOT_SHOW_IMG.getCode());
            }
            if (StringUtils.isNotBlank(params.getImgSrc())) {
                robotImgEO.setImgSrc(params.getImgSrc());
            } else if (StringUtils.isNotBlank(params.getAuctionImg())) {
                robotImgEO.setImgSrc(params.getAuctionImg());
            }
            robotImgEO.setImgSource(PicSourceEnum.GTR.getCode());
            robotImgDAO.insert(robotImgEO);
            logger.info("图片信息已经存入数据库!{}", robotImgEO);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException e) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(e.getArgs())) {
                msg = ResponseCodes.getResponseByCode(e.getErrorCode()).getMessage();
            } else {
                msg = e.getArgs()[0];
            }
            responseStatus.setStatus(e.getErrorCode(), msg);
            logger.error("自动化传图片发生异常", e);
        } catch (Exception e) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("自动化图片接口发生异常", e);
        }
        return response;
    }

    /**
     * 自动化查询子订单状态
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/orderStatus")
    public IServiceResponse SelectSubOrder(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印查询子订单信息:{}", params);
        RobotResponse response = new RobotResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == null || params.getId() == 0) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getOrderId())) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("orderId", params.getOrderId());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            DeliverySubOrder deliverySubOrder = deliverySubOrderDao.selectById(params.getId());
            //判断是不是已经转人工,(不管成功或者失败都返回自动化不能再去上号收货)  true代表不可以上号收货  false代表可以上号收货
            if (deliverySubOrder.getMachineArtificialStatus() == null) {
                throw new SystemException(ResponseCodes.EmptyOrderState.getCode(),
                        ResponseCodes.EmptyOrderState.getMessage());
            }
            //查询redis该笔子订单是否已经存在记录
            Boolean result = existLogRedis.isWrite(params.getId());
            // result  (ture代表已经存在false代表没有存在)redis有效期一天
            logger.info("邮寄交易,自动化查询该笔子订单状态Redis存记录{}", result);
            //添加日志,用来判断该笔订单是否超时
            if (result) {
                OrderLog orderLog = new OrderLog();
                orderLog.setOrderId(deliverySubOrder.getOrderId());
                orderLog.setCreateTime(new Date());
                orderLog.setSubId(deliverySubOrder.getId());
                //写入内部日志
                orderLog.setType(OrderLog.TYPE_INNER);
                orderLog.setLog("【" + deliverySubOrder.getId() + "号机器人】" + "第一次调该接口查询当前子订单是否允许机器上号!");
                deliveryOrderLogManager.writeLog(orderLog);
            }
            if (deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferSuccess.getCode() || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferFailed.getCode()) {
                response.setIsMoilRount(false);
            } else {
                response.setIsMoilRount(true);
            }
            logger.info("自动化查询子订单状态成功!{}", response);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException e) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(e.getArgs())) {
                msg = ResponseCodes.getResponseByCode(e.getErrorCode()).getMessage();
            } else {
                msg = e.getArgs()[0];
            }
            responseStatus.setStatus(e.getErrorCode(), msg);
            logger.error("自动化查询子订单状态发生异常", e);
        } catch (Exception e) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("自动化查询子订单状态接口发生异常", e);
        }
        return response;
    }

    /**
     * 自动化拉子订单
     *
     * @param params
     * @return
     */
    @Path("/orderList")
    @POST
    @Override
    public IServiceResponse orderList(@FormParam("") RobotRequest params) {
        logger.info("自动化拉收货子订单请求：{}",params);
        SubOrderListResponse response = new SubOrderListResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Integer pageSize = params.getPageSize();
            if (pageSize == null) {
                pageSize = Integer.valueOf(100);
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("pageSize", String.valueOf(pageSize));
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("pageSize", String.valueOf(pageSize));
            queryMap.put("status", DeliveryOrderStatus.TRADING.getCode());
            //查询不是机器转人工的订单
            queryMap.put("statusNotAutoMate", true);
            List<RoboutOrder> list = gtrOrderManager.queryShOrderListPage(queryMap, 0, pageSize, "id", true);
            response.setOrders(list);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("出货订单列表接口发生异常", ex);
        }
        return response;
    }

    /**
     * 全自动确认发货(撤单)接口
     */
    @Path("/finish")
    @POST
    @Override
    public IServiceResponse finish(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印发货(撤单)信息:{}", params);
        BaseResponse response = new BaseResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == null || params.getId() == 0) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getSellerRoleName())) {
                throw new SystemException(ResponseCodes.NotSellerRleName.getCode(),
                        ResponseCodes.NotSellerRleName.getMessage());
            }
            if (params.getRealCount() == null) {
                throw new SystemException(ResponseCodes.RealCountNotNull.getCode(),
                        ResponseCodes.RealCountNotNull.getMessage());
            }
            if (params.getTradeLogo() == 0 || params.getTradeLogo() == null) {
                throw new SystemException(ResponseCodes.SellerIsNull.getCode(),
                        ResponseCodes.SellerIsNull.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("sellerRoleName", params.getSellerRoleName());
            map.put("realCount", params.getRealCount() == null ? "" : String.valueOf(params.getRealCount()));
            map.put("tradeLogo", params.getTradeLogo() == null ? "" : String.valueOf(params.getTradeLogo()));
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            //自动化发货逻辑
            automationManager.automationFinish(params.getId(), params.getRealCount(), params.getTradeLogo());
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
            logger.info("自动化发货操作成功");
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);
            logger.error("发货(撤单)接口发生异常", ex);
        } catch (Exception e) {
            logger.error("发货(撤单)接口发生未知异常", e);
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
//            e.printStackTrace();
        }
        return response;
    }


    /**
     * 全自动查(撤单)状态接口
     */
    @Path("/withdrawOrder")
    @POST
    @Override
    public IServiceResponse withdrawOrder(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印查询子订单撤单信息:{}", params);
        RobotResponse response = new RobotResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == null || params.getId() == 0) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
//            if (StringUtils.isBlank(params.getOrderId())) {
//                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
//                        ResponseCodes.EmptyOrderId.getMessage());
//            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
//            map.put("orderId", params.getOrderId());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            DeliverySubOrder deliverySubOrder = deliverySubOrderDao.selectById(params.getId());
            if (deliverySubOrder == null) {
                throw new SystemException(ResponseCodes.NoDeliverySubOrder.getCode(), ResponseCodes.NoDeliverySubOrder.getMessage());
            }
            //判断该笔订单是否在撤单状态 如果"是(撤单)"传自动化(true)  "不是(没有撤单)"传(false)
            if (deliverySubOrder.getStatus() == DeliveryOrderStatus.CANCEL.getCode() || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferSuccess.getCode()
                    || deliverySubOrder.getMachineArtificialStatus() == MachineArtificialStatus.ArtificialTransferFailed.getCode()) {
                response.setWithdraw(true);
            } else {
                response.setWithdraw(false);
            }
            //查询redis该笔子订单是否已经存在记录
            Boolean result = existLogRedis.isWrite(params.getId());
            //添加日志,用来判断该笔订单是否超时
            if (result) {
                OrderLog orderLog = new OrderLog();
                orderLog.setOrderId(deliverySubOrder.getOrderId());
                orderLog.setCreateTime(new Date());
                orderLog.setSubId(deliverySubOrder.getId());
                //写入内部日志
                orderLog.setType(OrderLog.TYPE_INNER);
                orderLog.setLog("【" + deliverySubOrder.getId() + "号机器人】" + "第一次调该接口查询当前子订单是否已经撤单!");
                deliveryOrderLogManager.writeLog(orderLog);
            }
            logger.info("自动化查询子订单撤单状态成功!{}", response);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException e) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(e.getArgs())) {
                msg = ResponseCodes.getResponseByCode(e.getErrorCode()).getMessage();
            } else {
                msg = e.getArgs()[0];
            }
            responseStatus.setStatus(e.getErrorCode(), msg);
            logger.error("自动化查询子订单撤单状态发生异常", e);
        } catch (Exception e) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("自动化查询子订单撤单状态接口发生异常", e);
        }
        return response;
    }

    /**
     * 自动化6分钟没有收到货,调撤单接口
     */
    @Path("/withdraw")
    @POST
    @Override
    public IServiceResponse withdraw(@FormParam("") RobotRequest params) {
        logger.info("自动化GTR打印撤单信息:{}", params);
        BaseResponse response = new BaseResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getId() == null || params.getId() == 0) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (StringUtils.isBlank(params.getOtherReason())) {
                throw new SystemException(ResponseCodes.RobotExceptional.getCode(),
                        ResponseCodes.RobotExceptional.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("id", params.getId() == null ? "" : String.valueOf(params.getId()));
            map.put("otherReason", params.getOtherReason());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            Map<Long, Long> subOrdersInfos = new HashMap<Long, Long>();
            subOrdersInfos.put(params.getId(), 0L);
            Map<Long, String> appealReason = new HashMap<Long, String>();
            appealReason.put(params.getId(), params.getOtherReason());
            DeliverySubOrder deliverySubOrder = deliverySubOrderDao.selectById(params.getId());
            String mainOrderId = deliverySubOrder.getOrderId();
            //子订单撤单
            deliveryOrderManager.handleOrderForMailDeliveryOrderMax(subOrdersInfos, mainOrderId, appealReason, null, null);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
            logger.info("自动化撤单操作成功");
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);

            logger.error("撤单接口发生异常", ex);
        } catch (Exception e) {
            logger.error("撤单接口发生异常", e);
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
//            e.printStackTrace();
        }
        return response;
    }


    /**
     * 自动化获取查收邮件配置时间
     */
    @Path("obtainConfigTime")
    @GET
    @Override
    public IServiceResponse obtainConfigTime() {
        logger.info("根据配置值查询自动化查看邮箱配置时间请求：{}");
        // 初始化返回数据
        RobotResponse response = new RobotResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            // 查询配置   SystemConfigEnum
            SystemConfig systemConfig = systemConfigManager.getSystemConfig(SystemConfigEnum.ROBOT_CONGIGTIME.getKey());
            if (systemConfig == null) {
                throw new SystemException(
                        ResponseCodes.NotAppCurrency.getCode(), ResponseCodes.NotAppCurrency.getCode());
            }
            response.setConfigValue(systemConfig.getConfigValue());
            logger.error("根据配置值查询自动化查看邮箱配置时间:{}");
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);
            logger.error("根据配置值查询自动化查看邮箱配置时间发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            responseStatus.setStatus(
                    ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("根据配置值查询自动化查看邮箱配置时间发生未知异常:{}", ex);
        }
        logger.info("根据配置值查询自动化查看邮箱配置时间查询响应信息:{}", response);
        return response;
    }

    /**
     * 盘存数量接口
     * Inventory
     */
    @Path("/robotInventory")
    @POST
    @Override
    public IServiceResponse modifyInventory(@FormParam("") RobotFCRequest params) {
        logger.info("自动化盘存数量请求：{}", params);
        // 初始化返回数据
        RobotResponse response = new RobotResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getStockType() == null || params.getStockType() == 0) {
                throw new SystemException(ResponseCodes.StockTypeIsNot.getCode(),
                        ResponseCodes.StockTypeIsNot.getMessage());
            }
            if (StringUtils.isBlank(params.getGameName())) {
                throw new SystemException(ResponseCodes.NullGameNameAndRegionAndServer.getCode(),
                        ResponseCodes.NullGameNameAndRegionAndServer.getMessage());
            }
            if (StringUtils.isBlank(params.getServerName())) {
                throw new SystemException(ResponseCodes.NullGameNameAndRegionAndServer.getCode(),
                        ResponseCodes.NullGameNameAndRegionAndServer.getMessage());
            }
            if (StringUtils.isBlank(params.getRegionName())) {
                throw new SystemException(ResponseCodes.NullGameNameAndRegionAndServer.getCode(),
                        ResponseCodes.NullGameNameAndRegionAndServer.getMessage());
            }
            if (StringUtils.isBlank(params.getGameAccount())) {
                throw new SystemException(ResponseCodes.EmptyGameAccount.getCode(),
                        ResponseCodes.EmptyGameAccount.getMessage());
            }
            if (StringUtils.isBlank(params.getRoleName())) {
                throw new SystemException(ResponseCodes.NotroleName.getCode(),
                        ResponseCodes.NotroleName.getMessage());
            }
            if (StringUtils.isBlank(params.getOrderId())) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (params.getSubOrderId() == null) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("packageCount", params.getPackageCount() == null ? "" : String.valueOf(params.getPackageCount()));
            map.put("orderId", params.getOrderId());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            //盘存逻辑
            automationManager.modifyInventory(params);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);
            logger.error("根据配置值查询自动化查看邮箱配置时间发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            responseStatus.setStatus(
                    ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("根据配置值查询自动化查看邮箱配置时间发生未知异常:{}", ex);
        }
        logger.info("根据配置值查询自动化查看邮箱配置时间查询响应信息:{}", response);
        return response;
    }

    /**
     * 结单数量
     * Inventory
     */
    @Path("/finishFC")
    @POST
    @Override
    public IServiceResponse finishFC(@FormParam("") RobotFCRequest params) {
        logger.info("自动化结单数量请求：{}", params);
        // 初始化返回数据
        RobotResponse response = new RobotResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (params.getSubOrderId() == null || params.getSubOrderId() == 0) {
                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
                        ResponseCodes.EmptyOrderId.getMessage());
            }
            if (params.getRealCount() == null) {
                throw new SystemException(ResponseCodes.OrderConutIsNull.getCode(),
                        ResponseCodes.OrderConutIsNull.getMessage());
            }
            if (params.getUseCepertoryCount() == null) {
                throw new SystemException(ResponseCodes.OrderConutIsNull.getCode(),
                        ResponseCodes.OrderConutIsNull.getMessage());
            }
            if (params.getRobotReason() == null || params.getRobotReason() == 0) {
                throw new SystemException(ResponseCodes.NotAccount.getCode(),
                        ResponseCodes.NotAccount.getMessage());
            }
            if (StringUtils.isBlank(params.getRobotOtherReason())) {
                throw new SystemException(ResponseCodes.RobotExceptional.getCode(),
                        ResponseCodes.RobotExceptional.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("subOrderId", params.getSubOrderId() == null ? "" : String.valueOf(params.getSubOrderId()));
            map.put("realCount", params.getRealCount() == null ? "" : String.valueOf(params.getRealCount()));
            map.put("useCepertoryCount", params.getUseCepertoryCount() == null ? "" : String.valueOf(params.getUseCepertoryCount()));
            map.put("robotReason", params.getRobotReason() == null ? "" : String.valueOf(params.getRobotReason()));
            map.put("robotOtherReason", params.getRobotOtherReason());
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }
            Long realCount = params.getRealCount() / 10000L;
            Long UseCepertoryCount = params.getUseCepertoryCount() / 10000L;
            //分仓结单逻辑
            repositorySplitRequestManager.finishSplitRepositoryOrder(params.getSubOrderId(), realCount, params.getRobotReason(), UseCepertoryCount, params.getRobotOtherReason());
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获系统异常
            String msg = null;
            if (ArrayUtils.isEmpty(ex.getArgs())) {
                msg = ResponseCodes.getResponseByCode(ex.getErrorCode()).getMessage();
            } else {
                msg = ex.getArgs()[0];
            }
            responseStatus.setStatus(ex.getErrorCode(), msg);
            logger.error("自动化(分仓)结单发生异常:{}", ex);
        } catch (Exception ex) {
            // 捕获未知异常
            responseStatus.setStatus(
                    ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("自动化结单发生未知异常:{}", ex);
        }
        logger.info("自动化结单响应信息:{}", response);
        return response;
    }

//    /**
//     * 分仓日志
//     * Inventory
//     */
//    @Path("/writeFcLog")
//    @POST
//    @Override
//    public IServiceResponse writeFcLog(@FormParam("") FcLogRequest params) {
//        logger.debug("自动化分仓数量请求：{}");
//        // 初始化返回数据
//        RobotResponse response=new RobotResponse();
//        ResponseStatus responseStatus = new ResponseStatus();
//        response.setResponseStatus(responseStatus);
//        try {
//            if (params == null) {
//                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
//                        ResponseCodes.EmptyParams.getMessage());
//            }
//            if (params.getFcId() == 0 || params.getFcId() == null) {
//                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
//                        ResponseCodes.EmptyOrderId.getMessage());
//            }
//            if (params.getCount() == 0 || params.getCount() == null) {
//                throw new SystemException(ResponseCodes.OrderConutIsNull.getCode(),
//                        ResponseCodes.OrderConutIsNull.getMessage());
//            }
//            if (params.getLogType() == 0 || params.getLogType() == null) {
//                throw new SystemException(ResponseCodes.StockTypeIsNot.getCode(),
//                        ResponseCodes.StockTypeIsNot.getMessage());
//            }
//            if (StringUtils.isBlank(params.getFcOrderId())) {
//                throw new SystemException(ResponseCodes.EmptyOrderId.getCode(),
//                        ResponseCodes.EmptyOrderId.getMessage());
//            }
//            if (StringUtils.isBlank(params.getGameName())) {
//                throw new SystemException(ResponseCodes.EmptyGameName.getCode(),
//                        ResponseCodes.EmptyGameName.getMessage());
//            }
//            if (StringUtils.isBlank(params.getRegion())) {
//                throw new SystemException(ResponseCodes.EmptyRegion.getCode(),
//                        ResponseCodes.EmptyRegion.getMessage());
//            }
//            if (StringUtils.isBlank(params.getServer())) {
//                throw new SystemException(ResponseCodes.EmptyGameServer.getCode(),
//                        ResponseCodes.EmptyGameServer.getMessage());
//            }
//            if (StringUtils.isBlank(params.getBuyerAccount())) {
//                throw new SystemException(ResponseCodes.NoBuyerAccount.getCode(),
//                        ResponseCodes.NoBuyerAccount.getMessage());
//            }
//            if (StringUtils.isBlank(params.getGameAccount())) {
//                throw new SystemException(ResponseCodes.EmptyGameAccount.getCode(),
//                        ResponseCodes.EmptyGameAccount.getMessage());
//            }
//            if (StringUtils.isBlank(params.getRoleName())) {
//                throw new SystemException(ResponseCodes.EmptyRoleName.getCode(),
//                        ResponseCodes.EmptyRoleName.getMessage());
//            }
//            if (params.getIncomeType() == 0 || params.getIncomeType() == null) {
//                throw new SystemException(ResponseCodes.NotIncomeType.getCode(),
//                        ResponseCodes.NotIncomeType.getMessage());
//            }
//            if (StringUtils.isBlank(params.getLog())) {
//                throw new SystemException(ResponseCodes.FCNotLog.getCode(),
//                        ResponseCodes.FCNotLog.getMessage());
//            }
//            Map<String, String> map = new LinkedHashMap<String, String>();
//            map.put("fcId", params.getFcId() == null ? "" : String.valueOf(params.getFcId()));
//            map.put("count", params.getCount() == null ? "" : String.valueOf(params.getCount()));
//            map.put("fcOrderId",params.getFcOrderId());
//            map.put("log",params.getLog());
//            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
//            if (!StringUtils.equals(params.getSign(), sign2)) {
//                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
//            }
//            //获取分仓日志信息
//            SplitRepositoryLog splitlog=new SplitRepositoryLog();
//            splitlog.setFcId(params.getFcId());
//            splitlog.setGameName(params.getGameName());
//            splitlog.setRegion(params.getRegion());
//            splitlog.setServer(params.getServer());
////            splitlog.setGameRace(params.getRaceName());
//            splitlog.setBuyerAccount(params.getBuyerAccount());
//            splitlog.setGameAccount(params.getGameAccount());
//            splitlog.setRoleName(params.getRoleName());
//            splitlog.setFcOrderId(params.getFcOrderId());
//            splitlog.setCount(params.getCount());
//            splitlog.setIncomeType(params.getIncomeType());
//            splitlog.setCreateTime(new Date());
//            splitlog.setLog(params.getLog());
//            splitlog.setLogType(params.getLogType());
//            //分仓日志逻辑方法
//            splitRepositoryLogManager.saveLog(splitlog);
//            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
//        } catch (SystemException ex) {
//            // 捕获系统异常
//            responseStatus.setStatus(ex.getErrorCode(), ex.getArgs()[0].toString());
//            logger.error("自动化发送分仓日志发生异常:{}", ex);
//        } catch (Exception ex) {
//            // 捕获未知异常
//            responseStatus.setStatus(
//                    ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
//            logger.error("自动化发送分仓日志发生未知异常:{}", ex);
//        }
//        logger.debug("自动化发送分仓日志响应信息:{}", response);
//        return response;
//    }


    /**
     * 自动化拉分仓子订单
     *
     * @param params
     * @return
     */
    @Path("/orderFcList")
    @POST
    @Override
    public IServiceResponse orderFCList(@FormParam("") RobotFCRequest params) {
        logger.info("自动化拉分仓子订单请求：{}",params);
        FCSubOrderListResponse response = new FCSubOrderListResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        response.setResponseStatus(responseStatus);
        try {
            if (params == null) {
                throw new SystemException(ResponseCodes.EmptyParams.getCode(),
                        ResponseCodes.EmptyParams.getMessage());
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Integer pageSize = params.getPageSize();
            if (pageSize == null) {
                pageSize = Integer.valueOf(100);
            }
            if (StringUtils.isBlank(params.getSign())) {
                throw new SystemException(ResponseCodes.EmptySign.getCode(), ResponseCodes.EmptySign.getMessage());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("pageSize", String.valueOf(pageSize));
            String sign2 = SignHelper.sign2(map, signKey, "UTF-8");
            if (!StringUtils.equals(params.getSign(), sign2)) {
                throw new SystemException(ResponseCodes.InvalidSign.getCode(), ResponseCodes.InvalidSign.getMessage());
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("pageSize", String.valueOf(pageSize));
            queryMap.put("status", SplitRepositoryStatus.SPLITTING.getCode());
            List<GtrShOrder> list = gtrOrderManager.querySplitOrderListInPage(queryMap, 0, pageSize, "id", true);
            response.setOrders(list);
            responseStatus.setStatus(ResponseCodes.Success.getCode(), ResponseCodes.Success.getMessage());
        } catch (SystemException ex) {
            // 捕获未知异常
            responseStatus.setStatus(ResponseCodes.UnKnownError.getCode(), ResponseCodes.UnKnownError.getMessage());
            logger.error("出货订单列表接口发生异常", ex);
        }
        return response;
    }

}
