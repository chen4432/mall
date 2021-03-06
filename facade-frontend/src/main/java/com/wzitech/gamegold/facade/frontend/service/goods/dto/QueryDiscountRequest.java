/************************************************************************************
 *  Copyright 2014 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		QueryDiscountRequest
 *	包	名：		com.wzitech.gamegold.facade.frontend.service.goods.dto
 *	项目名称：	gamegold-facade-frontend
 *	作	者：		HeJian
 *	创建时间：	2014-2-20
 *	描	述：		
 *	更新纪录：	1. HeJian 创建于 2014-2-20 上午10:14:49
 * 				
 ************************************************************************************/
package com.wzitech.gamegold.facade.frontend.service.goods.dto;

import com.wzitech.chaos.framework.server.common.AbstractServiceRequest;

/**
 * 查询商品对应折扣请求
 * @author HeJian
 *
 */
public class QueryDiscountRequest extends AbstractServiceRequest{
	/**
	 * 商品ID
	 */
	private Long goodsId;

	/**
	 * @return the goodsId
	 */
	public Long getGoodsId() {
		return goodsId;
	}

	/**
	 * @param goodsId the goodsId to set
	 */
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	
}
