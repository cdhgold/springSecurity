package com.mang.example.security.comm;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * 메뉴 VO
 * @author 조원희 (whcho90@valuelinku.com)
 */
@Data
@XmlRootElement
public class MenuVo {

    private String pgmlvl;
    private String pgmmid;
    private String pgmumn;
    private String pgmseq;
    private String pgmdes;
    private String pgmurl;
    private String pgmopt;

    private String tabdes;
}
