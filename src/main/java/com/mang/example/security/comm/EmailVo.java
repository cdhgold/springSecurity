/*=========================================================
* Copyright(c) 2020 남성해운
*@FileName :  ComMap.java
*@Create Data :2020. 10. 5. 
*@History :
* 1.0, 2020.10.05최초작성
* ehddh,   2020. 10. 5. 
===========================================================*/
package com.mang.example.security.comm;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * 클래스설명: 
 * <pre>
 * 프로그램관리
 * </pre>
 * @author
 */

@Data
@XmlRootElement
public class EmailVo  {
    /** 보내는 사람 명. */
    String sendEmpName;
    /** 받는사람 이메일 주소1. */
    List<String> toEmailAddress;
    /** 보내는 사람 이메일 주소. */
    String sendEmailAddress;
    /** 제목. */
    String title;
    /** 내용. */
    String contents;
    /** 부서명. */
    String empDept;
    /** 이메일 타입. */
    String emailType;
    /** 주문번호. */
    String orderNo;
    /** 보내는사람 이메일. */
    String from;
    /** 받는사람 이메일. */
    String to;
    /** 받는사람 이름. */
    String toUserName;


}
