/************************************************************************************
 *  Copyright 2014 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		OrderHtmlQueryRequest
 *	包	名：		com.wzitech.gamegold.facade.frontend.service.order.dto
 *	项目名称：	gamegold-facade-frontend
 *	作	者：		SunChengfei
 *	创建时间：	2014-3-8
 *	描	述：		
 *	更新纪录：	1. SunChengfei 创建于 2014-3-8 下午7:01:28
 * 				
 ************************************************************************************/
package com.wzitech.gamegold.facade.frontend.service.order.dto;

import com.wzitech.gamegold.common.constants.ServicesContants;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 创建订单页联合查询请求
 * @author SunChengfei
 *
 */
public class OrderHtmlQueryRequest {
	/**
	 * 游戏名
	 */
	private String gameName;
	
	/**
	 * 游戏所在区
	 */
	private String region;
	
	/**
	 * 游戏所在服
	 */
	private String server;
	
	/**
	 * 所在阵营
	 */
	private String gameRace;
	
	/**
	 * 商品ID
	 */
	private Long goodsId;
	
	/**
	 * 商品栏目
	 */
	private Integer goodsCat;
	
	/**
	 * 需求金币数
	 */
	private int goldCount;
	
	/**
	 * 游戏等级
	 */
	private int gameLevel;
	
	/**
	 * 得到游戏等级
	 */
	public int getGameLevel() {
		return gameLevel;
	}
	/**
	 * 设置游戏等级
	 */
	public void setGameLevel(int gameLevel) {
		this.gameLevel = gameLevel;
	}
	
	
	/**
	 * 返回客服数
	 */
	private int size;

	/**
	 * 客服类型，接单客服或入驻客服
	 */
	private int servicerType;

	private String loginAccount;

	/**
	 * 通货类型ID
	 * hyl 5.16新增
	 */
	private Integer goodsTypeId;

	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 通货类型名称
	 * hyl 5.16新增
	 */
	private String goodsTypeName;

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	/**
	 *
	 * @return
	 */
	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName the gameName to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the gameRace
	 */
	public String getGameRace() {
		return gameRace;
	}

	/**
	 * @param gameRace the gameRace to set
	 */
	public void setGameRace(String gameRace) {
		this.gameRace = gameRace;
	}

	/**
	 * @return the goodsCat
	 */
	public Integer getGoodsCat() {
		return goodsCat;
	}

	/**
	 * @param goodsCat the goodsCat to set
	 */
	public void setGoodsCat(Integer goodsCat) {
		this.goodsCat = goodsCat;
	}

	/**
	 * @return the goldCount
	 */
	public int getGoldCount() {
		return goldCount;
	}

	/**
	 * @param goldCount the goldCount to set
	 */
	public void setGoldCount(int goldCount) {
		this.goldCount = goldCount;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public int getServicerType() {
		return servicerType;
	}

	public void setServicerType(int servicerType) {
		this.servicerType = servicerType;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
}
