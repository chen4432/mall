/************************************************************************************
 * Copyright 2014 WZITech Corporation. All rights reserved.
 * <p>
 * 模	块：		SellerManagerImpl
 * 包	名：		com.wzitech.gamegold.repository.business.impl
 * 项目名称：	gamegold-repository
 * 作	者：		HeJian
 * 创建时间：	2014-2-22
 * 描	述：
 * 更新纪录：	1. HeJian 创建于 2014-2-22 下午9:58:33
 ************************************************************************************/
package com.wzitech.gamegold.repository.business.impl;

import com.wzitech.chaos.framework.server.common.AbstractBusinessObject;
import com.wzitech.chaos.framework.server.common.ResponseStatus;
import com.wzitech.chaos.framework.server.common.exception.SystemException;
import com.wzitech.chaos.framework.server.common.utils.JsonMapper;
import com.wzitech.chaos.framework.server.dataaccess.pagination.GenericPage;
import com.wzitech.gamegold.common.context.CurrentUserContext;
import com.wzitech.gamegold.common.entity.BaseResponse;
import com.wzitech.gamegold.common.enums.CheckState;
import com.wzitech.gamegold.common.enums.ResponseCodes;
import com.wzitech.gamegold.common.enums.ShOpenState;
import com.wzitech.gamegold.common.log.business.ILogManager;
import com.wzitech.gamegold.common.main.IMainStationManager;
import com.wzitech.gamegold.common.main.MainStationConstant;
import com.wzitech.gamegold.common.servicer.IServicerOrderManager;
import com.wzitech.gamegold.common.utils.EncryptHelper;
import com.wzitech.gamegold.repository.business.IHuanXinManager;
import com.wzitech.gamegold.repository.business.ISellerGameManager;
import com.wzitech.gamegold.repository.business.ISellerManager;
import com.wzitech.gamegold.repository.dao.ISellerDBDAO;
import com.wzitech.gamegold.repository.dao.ISellerRedisDao;
import com.wzitech.gamegold.repository.entity.JBPayOrderTo7BaoEO;
import com.wzitech.gamegold.repository.entity.SellerInfo;
import com.wzitech.gamegold.repository.entity.SendDataDTO;
import com.wzitech.gamegold.shorder.business.*;
import com.wzitech.gamegold.shorder.dao.IPayOrderRedisDao;
import com.wzitech.gamegold.shorder.entity.PayOrder;
import com.wzitech.gamegold.shorder.entity.PurchaserData;
import com.wzitech.gamegold.shorder.utils.ISendHttpToSevenBao;
import com.wzitech.gamegold.usermgmt.business.IUserInfoManager;
import com.wzitech.gamegold.usermgmt.dao.rdb.IUsersGameDBDAO;
import com.wzitech.gamegold.usermgmt.entity.UserInfoEO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 卖家信息管理实现
 *
 * @author HeJian
 *         Update History
 *         Date        Name                Reason for change
 *         ----------  ----------------    ----------------------
 *         2017/05/17  zhujun           ZW_C_JB_00008 商城增加通货
 */
@Component
public class SellerManagerImpl extends AbstractBusinessObject implements ISellerManager {
    @Autowired
    ISellerDBDAO sellerDBDAO;

    @Autowired
    ISellerGameManager sellerGameManager;

    @Autowired
    IDeliveryOrderManager deliveryOrderManager;

    @Autowired
    private IPayOrderManager payOrderManager;

    @Autowired
    ILogManager logManager;

    @Autowired
    private IPayOrderRedisDao payOrderRedisDao;

//	@Autowired
//	PurchaserData purchaserData;

    @Autowired
    IServicerOrderManager servicerOrderManager;

    @Autowired
    IUserInfoManager userInfoManager;

    @Autowired
    ISellerRedisDao sellerRedisDao;

    @Autowired
    IUsersGameDBDAO usersGameDBDAO;

    @Autowired
    IHuanXinManager huanXinManager;

    @Autowired
    ISendHttpToSevenBao sendHttpToSevenBao;

    @Autowired
    IPurchaserDataManager purchaserDataManager;

    @Autowired
    private IFundDetailManager fundDetailManager;

    @Autowired
    IMainStationManager mainStationManager;

    @Autowired
    IFundManager fundManager;

    @Value("${7bao.fund.uid}")
    private String fundSellerUid;

    @Value("${7bao.fund.account}")
    private String fundSellerAccount;

    @Value("${7bao.send.url}")
    private String sevenBaoUrl;

    @Value("${7bao.ser.key}")
    private String serKey;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public SellerInfo applySeller(SellerInfo seller) throws SystemException {
        if (seller == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerInfo.getCode(), ResponseCodes.EmptySellerInfo.getMessage());
        }
        if (seller.getServicerId() == null) {
            throw new SystemException(
                    ResponseCodes.EmptyServicerId.getCode(), ResponseCodes.EmptyServicerId.getMessage());
        }
        if (StringUtils.isEmpty(seller.getLoginAccount())) {
            throw new SystemException(
                    ResponseCodes.EmptyLoginAccount.getCode(), ResponseCodes.EmptyLoginAccount.getMessage());
        }
        if (StringUtils.isEmpty(seller.getPhoneNumber())) {
            throw new SystemException(
                    ResponseCodes.NullPhoneNumber.getCode(), ResponseCodes.NullPhoneNumber.getMessage());
        }
        if (StringUtils.isEmpty(seller.getName())) {
            throw new SystemException(
                    ResponseCodes.EmptySellerName.getCode(), ResponseCodes.EmptySellerName.getMessage());
        }
        if (seller.getUid() == null) {
            throw new SystemException(
                    ResponseCodes.EmptyUid.getCode(), ResponseCodes.EmptyUid.getMessage());
        }
        if (seller.getSellerType() == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerType.getCode(), ResponseCodes.EmptySellerType.getMessage());
        }
        if (StringUtils.isEmpty(seller.getQq())) {
            throw new SystemException(
                    ResponseCodes.EmptySellerQQ.getCode(), ResponseCodes.EmptySellerQQ.getMessage());
        }
        seller.setCreateTime(new Date());
        seller.setIsDeleted(false);
        seller.setCheckState(CheckState.UnAudited.getCode());
        seller.setLastUpdateTime(new Date());

        seller.setIsShielded(false);
        seller.setManualStatus(true);
        seller.setIsShieldedType(false);
        seller.setIsHelper(false);
        sellerDBDAO.insert(seller);

        //客服处理卖家入驻数增1
        servicerOrderManager.addEnterNum(seller.getServicerId());
        return seller;
    }

    @Override
    @Transactional
    public SellerInfo modifySeller(SellerInfo sellerInfo)
            throws SystemException {
        if (sellerInfo == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerInfo.getCode(), ResponseCodes.EmptySellerInfo.getMessage());
        }
        if (sellerInfo.getId() == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        sellerInfo.setLastUpdateTime(new Date());
        sellerDBDAO.update(sellerInfo);
        return sellerInfo;
    }

    @Override
    @Transactional
    public SellerInfo modifySeller(Long id, String loginAccount, String shopName, String name, String phoneNumber, String qq, String games, String notes)
            throws SystemException {

        SellerInfo sellerInfo = sellerDBDAO.selectById(id);
        if (sellerInfo == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerInfo.getCode(), ResponseCodes.EmptySellerInfo.getMessage());
        }
        sellerInfo.setLastUpdateTime(new Date());
        sellerInfo.setLoginAccount(loginAccount);
        sellerInfo.setShopName(shopName);
        sellerInfo.setName(name);
        sellerInfo.setPhoneNumber(phoneNumber);
        sellerInfo.setQq(qq);
        sellerInfo.setGames(games);
        sellerInfo.setNotes(notes);
        sellerDBDAO.update(sellerInfo);
        return sellerInfo;
    }


    /**
     * 开通收货的商家批量生成环信账号和密码
     */
    @Override
    public void MassProductionAccount() throws SystemException, InterruptedException {
        huanXinManager.registerHuanXinByIdS();
    }

    @Override
    @Transactional
    public SellerInfo deleteSeller(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(id);
        sellerInfo.setCreateTime(new Date());
        sellerInfo.setLastUpdateTime(new Date());
        sellerInfo.setIsDeleted(true);
        sellerDBDAO.update(sellerInfo);

        return sellerInfo;
    }

    @Override
    public GenericPage<SellerInfo> querySeller(Map<String, Object> queryMap,
                                               int limit, int start, String sortBy, boolean isAsc)
            throws SystemException {
        if (StringUtils.isEmpty(sortBy)) {
            sortBy = "ID";
        }
        queryMap.put("isDeleted", false);
        return sellerDBDAO.selectByMap(queryMap, limit, start, sortBy, isAsc);
    }

    @Override
    public SellerInfo findSellerByUid(Long uid) throws SystemException {
        if (uid != null) {
            Map<String, Object> queryParam = new HashMap<String, Object>();
            queryParam.put("uid", uid);
        }
        SellerInfo sellerInfo = sellerDBDAO.selectById(uid);
        return sellerInfo;
    }

    @Override
    @Transactional
    public void auditSeller(SellerInfo seller) {
        SellerInfo dbseller = sellerDBDAO.selectById(seller.getId());
        dbseller.setCheckState(seller.getCheckState());
        dbseller.setNotes(seller.getNotes());
        if (seller.getCheckState() == CheckState.UnPassAudited.getCode()) {
            dbseller.setOpenShState(ShOpenState.CLOSE.getCode());
            dbseller.setIsOnline(false);
        }
        sellerDBDAO.update(dbseller);

        //客服处理卖家入驻数减1
        servicerOrderManager.subEnterNum(seller.getServicerId());
    }

    @Override
    public SellerInfo querySellerInfo(String loginAccount)
            throws SystemException {
        List<SellerInfo> sellerInfoList = sellerDBDAO.querySellerInfo(loginAccount);
        if (sellerInfoList != null && sellerInfoList.size() > 0) {
            return sellerInfoList.get(0);
        }
        return null;
    }

    @Override
    public SellerInfo queryloginAccountNotLike(String loginAccount)
            throws SystemException {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("loginAccountNotLike", loginAccount);
        List<SellerInfo> sellerInfoList = sellerDBDAO.selectByMap(queryMap, "ID", true);
        if (sellerInfoList != null && sellerInfoList.size() > 0) {
            return sellerInfoList.get(0);
        }

        return null;
    }

    @Override
    public void updateAgrre(List<SellerInfo> sellerInfo) {
        sellerDBDAO.updateAgreeInit(sellerInfo);
    }

    @Override
    public List<SellerInfo> querySellerByOrderId(String orderId, String goodsTypeName) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int userType = CurrentUserContext.getUserType();
        /*if(UserType.SystemManager.getCode()!=userType){
            if(UserType.MakeOrder.getCode()==userType||UserType.RecruitBusiness.getCode()==userType){
				UserInfoEO user = (UserInfoEO)CurrentUserContext.getUser();
				paramMap.put("servicerId", user.getMainAccountId());
			}else{
				paramMap.put("servicerId", CurrentUserContext.getUid());
			}
		}*/
        paramMap.put("orderId", orderId);
        paramMap.put("configResultIsDel", false);
        paramMap.put("repositoryIsDel", false);
        paramMap.put("sellerIsDel", false);
        paramMap.put("goodsTypeName", goodsTypeName);//ZW_C_JB_00008_20170517 ADD
        return sellerDBDAO.selectByOrderId(paramMap);
    }

    @Override
    public SellerInfo givePower(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }

        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        dbSeller.setMessagePower(true);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    @Transactional
    public SellerInfo openNewFund(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        boolean isAgree = dbSeller.getisAgree() == null ? false : dbSeller.getisAgree();
        if (dbSeller.getisAgree() != null && dbSeller.getisAgree()) {
            JSONArray dataJson = new JSONArray();
            //在用户已经阅读协议的情况下，开通新资金的时候通知7bao,绑定用户
            Boolean openSh;
            if (dbSeller.getOpenShState() != null && dbSeller.getOpenShState() == 1) {
                openSh = true;
            } else {
                openSh = false;
            }
            PurchaserData purchaserData = purchaserDataManager.selectByIdForUpdate(id);
            if (purchaserData == null) {
                throw new SystemException(ResponseCodes.EmptyPurchaseAccount.getCode(), ResponseCodes.EmptyPurchaseAccount.getMessage());
            }
            BigDecimal avavilableAmount = purchaserData.getAvailableAmount();
            int count = deliveryOrderManager.queryFund(dbSeller.getLoginAccount());
            if (count > 0) {
                //抛异常，有未结完订单
                throw new SystemException(ResponseCodes.OrderEnd.getCode(), ResponseCodes.OrderEnd.getMessage());
            }
//            if (count == 0) {
//                List<JBPayOrderTo7BaoEO> jbPayOrderTo7BaoEOs = updatePurchaserData(dbSeller.getLoginAccount(), purchaserData);
//                dataJson = JSONArray.fromObject(jbPayOrderTo7BaoEOs);
//            } else {
//                //抛异常，有未结完订单
//                throw new SystemException(ResponseCodes.OrderEnd.getCode(), ResponseCodes.OrderEnd.getMessage());
//            }

            //如果是第一次，即之前没开通过新资金（没有绑定），则后台操作切新资金不将资金同步到7宝，因为此时需要用户在前台进行同意协议。同意协议的时候再进行同步资金。
            //如果已经切过一次新资金，则此时因为前台用户已经之前操作过同意协议，此时直接将数据同步到7宝
            if (isAgree) {
                List<JBPayOrderTo7BaoEO> jbPayOrderTo7BaoEOs = updatePurchaserData(dbSeller.getLoginAccount(), purchaserData);
                dataJson = JSONArray.fromObject(jbPayOrderTo7BaoEOs);
            }

            //调主站设置为7bao商户
            setMerChant(dbSeller.getUid(), 1);
            logger.info("将收货商设置为7bao商户成功");

            tellZBao(dbSeller.getLoginAccount(), true, openSh, avavilableAmount, dataJson);
            dbSeller.setisBind(true);
        }

        dbSeller.setIsNewFund(true);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    @Transactional
    public SellerInfo closeNewFund(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        if (dbSeller.getisAgree() != null && dbSeller.getisAgree()) {
            //判断是否仍有未完成的订单
            int count = deliveryOrderManager.queryFund(dbSeller.getLoginAccount());
            if (count != 0) {
                //抛异常，有未结完订单
                throw new SystemException(ResponseCodes.OrderEnd.getCode(), ResponseCodes.OrderEnd.getMessage());
            }
            //在用户已经阅读协议的情况下，关闭新资金的时候通知7bao,解绑用户
            Boolean openSh;
            if (dbSeller.getOpenShState() != null && dbSeller.getOpenShState() == 1) {
                openSh = true;
            } else {
                openSh = false;
            }

            //调用将收货商从7bao商户变为普通用户
            setMerChant(dbSeller.getUid(), 0);
            logger.info("将收货商从7bao商户变普通用户成功");
            JSONArray dataJson = new JSONArray();
            tellZBao(dbSeller.getLoginAccount(), false, openSh, BigDecimal.ZERO, dataJson);
            dbSeller.setisBind(false);
        }
        dbSeller.setIsNewFund(false);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    @Transactional
    public void openCross(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller.getIsCross() ==null  || dbSeller.getIsCross()==false){
            dbSeller.setIsCross(true);
            sellerDBDAO.update(dbSeller);
        }
    }

    @Override
    @Transactional
    public void closeCross(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if ( dbSeller.getIsCross() ==null  || dbSeller.getIsCross()==true){
            dbSeller.setIsCross(false);
            sellerDBDAO.update(dbSeller);
        }
    }

    @Override
    public SellerInfo cancelPower(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }

        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }

        dbSeller.setMessagePower(false);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    public SellerInfo shieldSeller(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }

        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        dbSeller.setIsShielded(true);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    public SellerInfo cancelShield(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }

        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }

        dbSeller.setIsShielded(false);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    public SellerInfo giveManualStatus(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        dbSeller.setManualStatus(true);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    public SellerInfo cancelManualStatus(Long id) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }
        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        dbSeller.setManualStatus(false);
        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    @Override
    public SellerInfo saveShieldSeller(Long id, Boolean isShield, Boolean isHelper) throws SystemException {
        if (id == null) {
            throw new SystemException(
                    ResponseCodes.EmptySellerId.getCode(), ResponseCodes.EmptySellerId.getMessage());
        }

        SellerInfo dbSeller = sellerDBDAO.selectById(id);
        if (dbSeller == null) {
            return null;
        }
        dbSeller.setIsShielded(isShield);
        dbSeller.setManualStatus(!isShield);
        dbSeller.setIsShieldedType(isShield);
        dbSeller.setIsHelper(isHelper);

        sellerDBDAO.update(dbSeller);
        return dbSeller;
    }

    /**
     * 根据卖家账号和ID获取卖家信息
     *
     * @param account
     * @param uid
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public SellerInfo findByAccountAndUid(String account, String uid) {
        return sellerDBDAO.findByAccountAndUid(account, uid);
    }

    /**
     * 更新客服
     *
     * @param servicerId
     * @return
     */
    @Override
    @Transactional
    public void UpdateService(Long servicerId) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userId", servicerId);
        usersGameDBDAO.deleteUsersGames(queryMap);

        userInfoManager.deleteUser(servicerId);
        UserInfoEO userInfoEO = userInfoManager.findRZServicer();
        if (userInfoEO != null) {
            Long servicerIdNew = userInfoEO.getId();
            sellerDBDAO.UpdateService(servicerId, servicerIdNew);
        }
    }

    /**
     * 设置卖家为上线
     *
     * @param account
     * @param uid
     */
    @Override
    public void online(String account, String uid) {
        sellerDBDAO.online(account, uid);
    }

    /**
     * 设置卖家为下线
     *
     * @param account
     * @param uid
     */
    @Override
    public void offline(String account, String uid) {
        boolean isUseOffline = sellerRedisDao.isUseOffline(account, uid);
        if (!isUseOffline) {
            // 1个小时内没有使用下线功能的，可以使用下线
            sellerDBDAO.offline(account, uid);
            sellerRedisDao.useOffline(account, uid);
        } else {
            // 卖家1个小时内不能多次使用下线功能
            throw new SystemException(ResponseCodes.CantUseOffline.getCode(), ResponseCodes.CantUseOffline.getMessage());
        }
    }

    /**
     * 设置卖家为下线，后台管理员使用
     *
     * @param account
     * @param uid
     */
    @Override
    public void offlineBack(String account, String uid) {
        sellerDBDAO.offline(account, uid);
    }

    /**
     * 设置开通/关闭出货
     *
     * @param account
     * @param uid
     */
    @Override
    public void checkSh(String account, String uid, int openShState) {
        sellerDBDAO.checkSh(account, uid, openShState);
    }

    /**
     * 开启/关闭自动更新价格
     *
     * @param account
     * @param uid
     * @param isPriceRob
     */
    @Override
    public void enablePriceRob(String account, String uid, boolean isPriceRob) {
        sellerDBDAO.enablePriceRob(account, uid, isPriceRob);
    }

    @Transactional
    public void tellZBao(String LoginAccount, Boolean flag, Boolean openSh, BigDecimal amount, JSONArray dataJson) {
//		PurchaserData purchaserData = purchaserDataManager.selectById(sellerInfo.getId());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        //md5加密
        SendDataDTO sendDataDTO = new SendDataDTO();
        if (flag) {
            sendDataDTO.setTotalAmount(amount);
            sendDataDTO.setAvailableAmount(amount);
            sendDataDTO.setFreezeAmount(BigDecimal.ZERO);
        } else {
            sendDataDTO.setTotalAmount(BigDecimal.ZERO);
            sendDataDTO.setAvailableAmount(BigDecimal.ZERO);
            sendDataDTO.setFreezeAmount(BigDecimal.ZERO);
        }

        //查资金是否与7bao一致
        SellerInfo sellerInfo = querySellerInfo(LoginAccount);
        if (sellerInfo == null) {
            throw new SystemException(ResponseCodes.EmptyPurchaseAccount.getCode(), ResponseCodes.EmptyPurchaseAccount.getMessage());
        }
        Long id = sellerInfo.getId();
        PurchaserData purchaserData = purchaserDataManager.selectByIdForUpdate(id);
        if (purchaserData == null) {
            throw new SystemException(ResponseCodes.EmptyPurchaseAccount.getCode(), ResponseCodes.EmptyPurchaseAccount.getMessage());
        }
        sendDataDTO.setCheckTotalAmount(purchaserData.getTotalAmountZBao());
        sendDataDTO.setCheckAvailableAmount(purchaserData.getAvailableAmountZBao());
        sendDataDTO.setCheckFreezeAmount(purchaserData.getFreezeAmountZBao());

        sendDataDTO.setLoginAccount(LoginAccount);
        sendDataDTO.setUserBind(flag);
        sendDataDTO.setIsShBind(openSh);
        String format = String.format("%s_%s_%s_%s_%s_%s_%s_%s_%s_%s_%s", sendDataDTO.getLoginAccount(), sendDataDTO.getUserBind(), sendDataDTO.getIsShBind(),
                df.format(sendDataDTO.getTotalAmount()), df.format(sendDataDTO.getFreezeAmount()), df.format(sendDataDTO.getAvailableAmount()),
                df.format(sendDataDTO.getCheckTotalAmount()), df.format(sendDataDTO.getCheckAvailableAmount()), df.format(sendDataDTO.getCheckFreezeAmount()), dataJson, serKey);
        logger.info("请求7bao新资金操作" + (flag ? "开通" : "关闭") + "，绑定7bao:{}", format);
        logger.info("参数format,{}", format);
        String md5 = null;
        try {
            md5 = EncryptHelper.md5(format);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendDataDTO.setDataJson(dataJson);
        sendDataDTO.setSign(md5);
        JSONObject jsonParam = JSONObject.fromObject(sendDataDTO);
        //发起http请求调用7bao接口
        String reponse = sendHttpToSevenBao.sendHttpPost(sevenBaoUrl, jsonParam);
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        BaseResponse baseResponse = jsonMapper.fromJson(reponse, BaseResponse.class);
        logger.info("tellZBao,baseResponse,{}", baseResponse);
        if (baseResponse == null) {
            throw new SystemException(ResponseCodes.ResponseError.getCode(), ResponseCodes.ResponseError.getMessage());
        }
        ResponseStatus responseStatus = baseResponse.getResponseStatus();
        if (responseStatus == null || !responseStatus.getCode().equals("00")) {
            throw new SystemException(responseStatus.getCode(), responseStatus.getMessage());
        }
//        //判断返回值
//        String responseStatusStr = jsonResult.getString("responseStatus");
//        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
//        ResponseStatus responseStatus = jsonMapper.fromJson(responseStatusStr, ResponseStatus.class);
//        if (!"00".equals(responseStatus.getCode())) {
//            throw new SystemException(responseStatus.getCode(), responseStatus.getMessage());
//        }
    }

    /**
     * 根据用户,判断是否有为结完订单
     * 老资金转 7bao
     *
     * @param
     * @return
     */
//    public PurchaserData initAmount(Long id, String userLoginAccount) {
////		SellerInfo sellerInfo = this.queryloginAccountNotLike(userLoginAccount);
////		int count;
////		if (sellerInfo == null) {
////			return null;
////		}
////		if (sellerInfo.getId() == null) {
////			return null;
////		}
//        //根据用户uid查该用户是否还有未完结的单子
//        PurchaserData purchaserData = null;
//        int count = deliveryOrderManager.queryFund(userLoginAccount);
//        if (count == 0) {
//            //根据用户ID查找给用户的资金,并添加到7bao账户
//            purchaserData = purchaserDataManager.selectByIdForUpdate(id);
//            if (purchaserData != null) {
//                List<JBPayOrderTo7BaoEO> jbPayOrderTo7BaoEOs = updatePurchaserData(userLoginAccount, purchaserData);
//            }
//        } else {
//            //抛异常，有未结完订单
//            throw new SystemException(ResponseCodes.OrderEnd.getCode(), ResponseCodes.OrderEnd.getMessage());
//        }
//        return purchaserData;
//    }
    public List<JBPayOrderTo7BaoEO> updatePurchaserData(String userLoginAccount, PurchaserData purchaserData) {
        //同意协议修改老充值单转新充值单逻辑
//            Map<String, Object> selectMap = new HashMap<String, Object>();
//            selectMap.put("","");
        BigDecimal payOrderBalance = BigDecimal.ZERO;
        List<PayOrder> payOrders = payOrderManager.queryAvailablePayOrders(userLoginAccount, true);
//        List<PayOrder> newPayOrderList = new ArrayList<PayOrder>();
        // 传给7bao 的订单号
        List<JBPayOrderTo7BaoEO> jbT7baoEOs = new ArrayList<JBPayOrderTo7BaoEO>();
        //讲所以得充值单更新写入
        StringBuffer sb = new StringBuffer("{");
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        for (PayOrder pay : payOrders) {
//            //生成新的充值单
//            PayOrder newPayOrder = new PayOrder();
//            newPayOrder.setBalance(pay.getBalance());
//            newPayOrder.setCreateTime(new Date());
//            newPayOrder.setAmount(pay.getAmount());
//            newPayOrder.setPayTime(new Date());
//            newPayOrder.setAccount(pay.getAccount());
//            newPayOrder.setStatus(pay.getStatus());
//            newPayOrder.setUid(pay.getUid());
//            newPayOrder.setUsedAmount(pay.getUsedAmount());
//            newPayOrder.setOrderId(payOrderRedisDao.getZBaoOrderId());
//            //生成返回7bao的订单号
//            JBPayOrderTo7BaoEO jbPayOrderTo7BaoEO = new JBPayOrderTo7BaoEO();
//            jbPayOrderTo7BaoEO.setBalance(pay.getBalance());
//            jbPayOrderTo7BaoEO.setNewOrderId(newPayOrder.getOrderId());
//            jbPayOrderTo7BaoEO.setOldOrderId(pay.getOrderId());
//            jbPayOrderTo7BaoEO.setUpdateTime(new Date());
//            jbT7baoEOs.add(jbPayOrderTo7BaoEO);
//
//            //将老资金清除
            //转为新资金订单
//            newPayOrderList.add(newPayOrder);
            //日志的记录
//            sb.append("老充值单号:" + pay.getOrderId() + "改为新充值单号:");
//            sb.append(newPayOrder.getOrderId() + ";");
            //判断充值单金额是否正确
            payOrderBalance = payOrderBalance.add(pay.getBalance());
            map.put(pay.getOrderId(), pay.getBalance());

            pay.setLastUpdateTime(new Date());
            pay.setBalance(BigDecimal.ZERO);
        }

        sb.append("}");
        if (payOrderBalance.compareTo(purchaserData.getTotalAmount()) != 0) {
            throw new SystemException(ResponseCodes.PayOrderNotPurchaserDate.getCode(), ResponseCodes.PayOrderNotPurchaserDate.getMessage());
        }
        //将老订单号批量更新
        payOrderManager.batchUpdateByIds(payOrders);

//        //将新的订单号批量插入
//        payOrderManager.batchInsert(newPayOrderList);
//        // 写入资金明细
//        String log = String.format("【金币资金转7bao充值单】充值订单号:%s,新充值金额:%s,当前新总金额:%s,新冻结金额:%s,新可用金额:%s",
//                sb.toString(), payOrderBalance, purchaserData.getTotalAmountZBao(), purchaserData.getFreezeAmountZBao(),
//                purchaserData.getAvailableAmountZBao());
//        FundDetail fundDetail = new FundDetail();
//        fundDetail.setBuyerAccount(userLoginAccount);
//        fundDetail.setType(FundType.TRANSFER.getCode());
//        fundDetail.setPayOrderId(null);
//        fundDetail.setAmount(payOrderBalance);
//        fundDetail.setLog(log);
//        fundDetail.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        fundDetailManager.save(fundDetail);
        //同意协议修改充值单逻辑
//        purchaserData.setTotalAmountZBao(isNUllToZero(purchaserData.getTotalAmountZBao()).add(purchaserData.getTotalAmount()));
        purchaserData.setTotalAmount(BigDecimal.ZERO);
//        purchaserData.setAvailableAmountZBao(isNUllToZero(purchaserData.getAvailableAmountZBao()).add(purchaserData.getAvailableAmount()));
        purchaserData.setAvailableAmount(BigDecimal.ZERO);
        if (purchaserData.getFreezeAmount() == null || purchaserData.getFreezeAmount().compareTo(BigDecimal.ZERO) != 0) {
            throw new SystemException(ResponseCodes.OrderEnd.getCode(), ResponseCodes.OrderEnd.getMessage());
        } else {
            purchaserData.setFreezeAmountZBao(BigDecimal.ZERO);
        }
        purchaserDataManager.update(purchaserData);

        if (payOrders != null && payOrders.size() > 0) {
            for (PayOrder payOrder : payOrders) {
                fundManager.transfer(payOrder.getOrderId(), payOrder.getOrderId(), payOrder.getUid(), payOrder.getAccount(), fundSellerUid, fundSellerAccount, map.get(payOrder.getOrderId()), payOrder);
            }
        }
        return jbT7baoEOs;
    }

    public BigDecimal isNUllToZero(BigDecimal b) {
        return b == null ? BigDecimal.ZERO : b;
    }

    public void setMerChant(String uid, int code) {
        String jsonParam = "{'UserId':'" + uid + "','merchantType':'" + code + "'}";
        try {
            mainStationManager.GetMainRest(MainStationConstant.URL_REST, MainStationConstant.RESULT_AUTHVERS, MainStationConstant.RESULT_TYPE, MainStationConstant.SET_USER_TOBE_MERCHANT,
                    jsonParam, MainStationConstant.RESULT_MD5, MainStationConstant.RESULT_FIELDS, MainStationConstant.RESULT_VERSION);
        } catch (Exception e) {
            logger.error("调用主站设置{}：商户失败", uid);
            throw new SystemException(ResponseCodes.FailToSetMerchant.getCode(), ResponseCodes.FailToSetMerchant.getMessage());
        }
    }

    /**
     * 根据卖家登录账号查询卖家详情
     */
    @Override
    public SellerInfo findSellerDetailByLoginAccount(String loginAccount) {
        return sellerDBDAO.findSellerDetailByLoginAccount(loginAccount);
    }
}
