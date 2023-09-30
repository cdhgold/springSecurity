package com.mang.example.security.comm;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


/**
 * 메뉴 별 사용자 권한 VO
 * @author Doohyung Cha 
 */ 
@Data
@XmlRootElement
public class MenuAuthVo {

    private String bukrs;
    private String pgagid;
    private String pgauid;
    private String pgapgm;
    private String pgains;
    private String pgaudt;
    private String pgadlt;
    private String pgainq;
    private String pgaat1;
    private String pgaat2;
    private String pgaat3;
    private String pgmtop;
    
}
