package com.mang.example.security.app.user.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mang.example.security.app.user.model.UserVO;
import com.mang.example.security.comm.ComMap;

import com.mang.example.security.comm.ControllerException;
import lombok.extern.log4j.Log4j2;
 

@Controller
@Log4j2
public class CommController {
 
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(CommController.class);

    protected ComMap getAdminUserSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ComMap userMap = (ComMap) session.getAttribute("userMap");
        return userMap;
    }

    protected ComMap getFrontUserSession(HttpServletRequest request) throws Exception {
        ComMap frontUserSession = new ComMap();
        return frontUserSession;
    }

    protected UserVO getSessionUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO usr = (UserVO) session.getAttribute("usr_info");
        return usr;
    }

    protected ObjectMapper setObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 넘어온 값 중에 Vo에 없는 항목 값이 넘어와도 무시하는 설정.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // VO 대문자 키 사용시 대소문자 가리지 않고 값 세팅 하도록 하는 설정.
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper;
    }

    // Map List to VO List
    @SuppressWarnings("unchecked")
    public <T> List<T> getObjectList(Map<String, Object> param, String key, Class<?> clazz, UserVO... sessionUserInfo)
            throws ControllerException {
        List<T> objList = new ArrayList<>();
        try {
            // List<Object> gdList = (List<Object>)getRequestData().get(key);
            List<Object> gdList = (List<Object>) param.get(key);
            // Map<String, Object> actionMap = (Map<String, Object>)
            // getMapper().convertValue(getCommonData(), Map.class);
            Map<String, Object> actionMap = null;
            Map<String, Object> sessionMap = null;
            if (sessionUserInfo != null && sessionUserInfo.length > 0)
                sessionMap = (Map<String, Object>) setObjectMapper().convertValue(sessionUserInfo[0], Map.class);

            if (gdList != null)
                for (int i = 0; i < gdList.size(); i++) {
                    if (gdList.get(i) != null)
                        objList.add(getData(gdList.get(i), clazz, actionMap, sessionMap));
                }
        } catch (Exception e) {
            throw new ControllerException(e.getMessage(), e);
        }
        return objList;
    }
   
    /**
     * @param param
     * @param string
     * @param class1
     * @param b
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(Map<String, Object> param, String key, Class<?> clazz, UserVO... sessionUserInfo) throws ControllerException {
        try {
            Map<String, Object> actionMap = null;
            Map<String, Object> sessionMap = null;
            if (sessionUserInfo != null && sessionUserInfo.length > 0)
                sessionMap = (Map<String, Object>) setObjectMapper().convertValue(sessionUserInfo[0], Map.class);
            return getData(param.get(key), clazz, actionMap, sessionMap);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getData(Object obj, Class<?> clazz, Map<String, Object> actionMap, Map<String, Object> sessionMap)
            throws ControllerException {
        T rtnObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            if (actionMap != null)
                map.putAll(actionMap);
            if (sessionMap != null)
                map.putAll(sessionMap);
            if (obj != null)
                map.putAll((Map<? extends String, ?>) obj);

            // 넘어온 값 중에 Vo에 없는 항목 값이 넘어와도 무시하는 설정.
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // VO 대문자 키 사용시 대소문자 가리지 않고 값 세팅 하도록 하는 설정.
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            rtnObj = (T) mapper.convertValue(map, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
        return rtnObj;
    }

//    public SessionUserInfo getSessionUser() {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        return (SessionUserInfo) req.getSession().getAttribute(SessionUserInfo.class.getName());
//    }

}
