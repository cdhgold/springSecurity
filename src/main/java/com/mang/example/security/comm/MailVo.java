/*=========================================================
* Copyright(c) 2020 남성해운
*@FileName :  MailVo.java
*@Create Data :2020. 10. 8. 
*@History :
* 1.0, 2020.10.05최초작성
* ehddh,   2020. 10. 8. 
===========================================================*/
package com.mang.example.security.comm;

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
public class MailVo  {
    String mailFrom;        // null 허용 값이 없을때 application.properties smtp.defaultFrom 에 설정된 값으로 이메일이 발송됩니다. 
    String mailTo;
    String mailCC;          // null 허용 
    String mailTitle;
    String mailFilePath;    // null 허용 
    String mailContent;
    String bukrs;
    
    String attachExcelName; //2021.11.19 추가
    /*
     * UI Data 컬럼
     * 
     : [{"id":"chk",             "name":"chk", "dataType":"text", "ignoreStatus":"true"}
     , {"id":"bukrs",           "name":"회사 구분", "dataType":"text"}
     , {"id":"mailfrom",        "name":"from mail", "dataType":"text"}
     , {"id":"mailto",          "name":"to mail", "dataType":"text"} 
     , {"id":"mailcc",          "name":"CC", "dataType":"text"}
     , {"id":"mailtitle",       "name":"제목", "dataType":"text"}
     , {"id":"mailfile",        "name":"첨부파일", "dataType":"text"}
     , {"id":"mailcontent",     "name":"내용", "dataType":"text"} 
     , {"id":"mailpath",        "name":"주소?", "dataType":"text"}
     , {"id":"mailorgfile",     "name":"첨부 실제파일", "dataType":"text"}
    */
}
