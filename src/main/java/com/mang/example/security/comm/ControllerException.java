package com.mang.example.security.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 

public class ControllerException extends RuntimeException {

  /** IconPlusException.java */
  private static final long serialVersionUID = 8162612253643216040L;

  private String message = "";

  public ControllerException(String message, Throwable cause ,  HttpServletRequest request) { 
    super(cause);
    this.message = this.searchCode(message);
    HttpSession session = request.getSession(); 
    session.setAttribute("ERROR_MSG", message);
  }

  public ControllerException(String message, Throwable cause) {
    super(cause);
    this.message = this.searchCode(message);
  }

  public ControllerException(String message,  HttpServletRequest request) {
      HttpSession session = request.getSession(); 
      session.setAttribute("ERROR_MSG", message);
    this.message = message;
  }

  public ControllerException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private String searchCode(String message) {
    String msg = "";
    if (!StringUtils.isEmpty(message)) {
      msg = "[" + message + "]";
    } else {
      msg = "Server Error.<br/> Please Contact Administrator For More Information.";
    }
    return msg;
  }

}
