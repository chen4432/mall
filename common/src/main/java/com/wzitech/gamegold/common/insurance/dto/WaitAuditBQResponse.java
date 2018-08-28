
package com.wzitech.gamegold.common.insurance.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WaitAuditBQResult" type="{http://tempuri.org/}BackDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "waitAuditBQResult"
})
@XmlRootElement(name = "WaitAuditBQResponse")
public class WaitAuditBQResponse {

    @XmlElement(name = "WaitAuditBQResult")
    protected BackDTO waitAuditBQResult;

    /**
     * 获取waitAuditBQResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link com.wzitech.gamegold.common.insurance.dto.BackDTO }
     *     
     */
    public BackDTO getWaitAuditBQResult() {
        return waitAuditBQResult;
    }

    /**
     * 设置waitAuditBQResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link com.wzitech.gamegold.common.insurance.dto.BackDTO }
     *     
     */
    public void setWaitAuditBQResult(BackDTO value) {
        this.waitAuditBQResult = value;
    }

}