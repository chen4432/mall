/************************************************************************************
 *  Copyright 2014 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		GameRaceInfo
 *	包	名：		com.wzitech.gamegold.common.game.entity
 *	项目名称：	gamegold-common
 *	作	者：		SunChengfei
 *	创建时间：	2014-2-21
 *	描	述：		
 *	更新纪录：	1. SunChengfei 创建于 2014-2-21 下午9:25:05
 * 				
 ************************************************************************************/
package com.wzitech.gamegold.common.game.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 阵营信息
 * @author SunChengfei
 *
 */
@XmlRootElement(name="GameRace")
public class GameRaceInfo {
	/**
	 * 阵营Id
	 */
	private String id;
	
	/**
	 * 阵营名称
	 */
	private String name;

	/**
	 * @return the id
	 */
	@XmlElement(name="id")
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
