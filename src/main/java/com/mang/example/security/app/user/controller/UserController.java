package com.mang.example.security.app.user.controller;

import com.mang.example.security.app.user.model.UserVO;
import com.mang.example.security.app.user.service.UserService;
import com.mang.example.security.enums.role.UserRole;
 
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
@Log4j2
public class UserController {

    @Autowired 
    private  UserService userService; 

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/loginView")
    public String loginView(){
        return "user/login";
    }

    // 사용않는다. 
    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute UserVO userVO){
        log.error("@@@");
        String userPw = userVO.getUserPw(); 
        userVO = userService.findUserByUserEmail(userVO.getUserEmail());
        if(userVO == null || !passwordEncoder.matches(userPw, userVO.getUserPw())){
            redirectAttributes.addFlashAttribute("rsMsg", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:/user/loginView";
        }

        request.getSession().setAttribute("userVO", userVO);
        return "redirect:/index";
    }
    

    @GetMapping(value = "/init")
    public String createAdmin(@ModelAttribute UserVO userVO){
        userVO.setUserEmail("user@naver.com");
        userVO.setUserPw(passwordEncoder.encode("test"));
        userVO.setRole(UserRole.USER);
        if(userService.createUser(userVO) == null){
            log.error("Create Admin Error");
        }
        UserVO user2 = new UserVO();
        user2.setUserEmail("admin@naver.com");
        user2.setUserPw(passwordEncoder.encode("test"));
        user2.setRole(UserRole.ADMIN);
        if(userService.createUser(user2) == null){
            log.error("Create Admin Error");
        }
        return "redirect:/index";
    }

}
