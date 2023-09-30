package com.mang.example.security.app.user.controller;

import com.mang.example.security.app.user.model.UserVO;
import com.mang.example.security.app.user.service.UserService;
import com.mang.example.security.comm.ControllerException;
import com.mang.example.security.comm.TestVo;
import com.mang.example.security.enums.role.UserRole;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/test")
@Log4j2
public class TestController extends CommController{
 
    @GetMapping( "/test")
    public String loginView(){
        return "/home/test";
    }

    @RequestMapping("/select1000")  
    public @ResponseBody Map<String, Object> select1000(
            @RequestBody Map<String, Object> param
            , HttpServletRequest request
            ) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		UserVO usrInfo = this.getSessionUserInfo(request);
		//HashMap<String, Object> paramMap = (HashMap<String, Object>)param.get("dl_out");
		
		//logger.debug("selectAPRAPM8101R01 AAAAAAAAAA START !!!!!!! : " + paramMap);
		
		try {
			   List<TestVo> attachList = getObjectList(param, "dl_out", TestVo.class, usrInfo);
		         
		// 리스트
		logger.debug("result ==> " + attachList.size());
		
		
		} catch (Exception e) {
		
		// 메시지 출력 필요시 메시지 추가 ("" 으로 할 시 『서버 오류입니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.』 메시지 출력)
		throw new ControllerException(e.getMessage(),e.getCause());
		}
	return rtnMap;
	}
}
