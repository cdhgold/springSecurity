/*=========================================================
 * Copyright(c) 2020 남성해운
 * @FileName : StringUtil.java
 * @Created Date : 2020. 11. 16.
 * @History :
 * 1.0, 2020. 11. 16. 최초 작성
 * keyjong,   1.0, 2020. 11. 16., 기존 ICON SYSTEM 사용 Copy     
=========================================================*/

package com.mang.example.security.comm;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mang.example.security.comm.DateUtil;


public class StringUtils extends org.apache.commons.lang3.StringUtils {

  public static String getNull(Object stText) {
    try {
      if (stText == null || "null".equals(stText))
        return "";
      String stTextStr = stText.toString();
      return stTextStr.trim();
    } catch (Exception e) {
      return "";
    }
  }

  public static String getNull(String stText) {
    try {
      if (stText == null || "null".equals(stText))
        return "";
      return stText.trim();
    } catch (Exception e) {
      return "";
    }
  }

  public static String getNull(String stText, String defaultStr) {
    try {
      if (stText == null || "null".equals(stText))
        return defaultStr;
      return stText.trim();
    } catch (Exception e) {
      return defaultStr;
    }
  }

  public static int getIntNull(String stText) {
    try {
      if (stText == null || "null".equals(stText))
        return 0;
      return Integer.parseInt(stText);
    } catch (Exception e) {
      return 0;
    }
  }
  
  public static double getDoubleNull(String stText) {
    try {
      if (stText == null || "null".equals(stText))
        return 0;
      return Double.parseDouble(stText);
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 랜덤 key 생성.
   *
   * @param len the len
   * @return the random key
   */
  public static String getRandomKey(int len) {

    int nSeed;
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < len; i++) {
      nSeed = (int) (Math.random() * 10 + 1);
      sb.append(String.valueOf("0123456789".charAt(nSeed - 1)));
    }

    return sb.toString();
  }

  /**
   * 랜덤 key 생성.
   *
   * @param len the len
   * @return the random key
   */
  public static int getLength(String val) {

    int len = 0;
    if (val != null) {
      len = val.length();
    }
    return len;
  }

  public static String cutLeft(String s, char removeChar) {
    if (s == null) {
      return "";
    }

    int len = s.length();
    char[] val = s.toCharArray();
    int index = 0;

    for (index = 0; index < len; index++) {
      if (val[index] != removeChar) {
        break;
      }
    }

    return index > 0 ? s.substring(index) : s;
  }

  public static String cutRight(String s, char removeChar) {
    if (s == null) {
      return "";
    }

    int len = s.length();
    int index = 0;
    char[] val = s.toCharArray();
    for (index = len - 1; index >= 0; index--) {
      if (val[index] != removeChar) {
        break;
      }
    }
    return index > 0 ? s.substring(0, index + 1) : s;
  }

  // 입력된 날자 다음날을 구한다.
  public static String getAfterDay(String day) throws Exception {
    if (day.length() != 8)
      throw new Exception("You must enter 8 digits of the date.");

    return getYesterOrTomorrow(day, true);
  }

  // 입력된 전날을 구한다.
  public static String getBeforeDay(String day) throws Exception {
    if (day.length() != 8)
      throw new Exception("You must enter 8 digits of the date.");

    return getYesterOrTomorrow(day, false);
  }

  public static String getBlankString(int length) {
    char[] blank = new char[length];

    for (int i = 0, loop = blank.length; i < loop; i++) {
      blank[i] = ' ';
    }

    return new String(blank);
  }

  public static String getBlankString(String data, int length) {
    int dataLen = data.length();

    int blankLen = length - dataLen;
    if (blankLen < 0)
      return data;

    char[] blank = new char[blankLen];

    for (int i = 0; i < blankLen; i++) {
      blank[i] = ' ';
    }

    return new StringBuffer(data).append(new String(blank)).toString();
  }

  public static String[] getCurrentDate() {
    Date date = new Date();
    String info = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(date).toString();
    String day = info.substring(0, 8);
    String time = info.substring(8);

    return new String[] { day, time };
  }

  public static String getCurrentDateTime() {
    Date date = new Date();
    return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(date).toString();
  }

  public static String[] getSubstringTokens(String s, int[] tokenLen) {
    String[] res = new String[tokenLen.length];

    int totLen = 0;
    for (int i = 0; i < res.length; i++) {
      res[i] = s.substring(totLen, totLen + tokenLen[i]);
      totLen += tokenLen[i];
    }
    return res;
  }

  public static String getTomorrow() {
    return getYesterOrTomorrow(getCurrentDate()[0], true);
  }

  public static String getTomorrow(String day) {
    return getYesterOrTomorrow(day, true);
  }

  public static String getYesterDay() {
    return getYesterOrTomorrow(getCurrentDate()[0], false);
  }

  public static String getYesterDay(String day) {
    return getYesterOrTomorrow(day, false);
  }

  private static String getYesterOrTomorrow(String today, boolean isTommrow) {

    String year = today.substring(0, 4);
    String month = today.substring(4, 6);
    String day = today.substring(6);

    String _year = "";
    String _month = "";
    String _day = "";

    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1,
        Integer.parseInt(day));

    gc.roll(Calendar.DAY_OF_YEAR, isTommrow);
    gc.getGregorianChange();

    _year = Integer.toString(gc.get(java.util.Calendar.YEAR));

    if (gc.get(java.util.Calendar.MONTH) + 1 > 9)
      _month = Integer.toString(gc.get(java.util.Calendar.MONTH) + 1);
    else
      _month = "0" + Integer.toString(gc.get(java.util.Calendar.MONTH) + 1);

    if (gc.get(java.util.Calendar.DAY_OF_MONTH) > 9)
      _day = Integer.toString(gc.get(java.util.Calendar.DAY_OF_MONTH));
    else
      _day = "0" + Integer.toString(gc.get(java.util.Calendar.DAY_OF_MONTH));

    if (_day.equals("01") && _month.equals("01") && isTommrow) {
      gc.roll(java.util.Calendar.YEAR, isTommrow);
      gc.getGregorianChange();
      _year = Integer.toString(gc.get(java.util.Calendar.YEAR));
    } else if (day.equals("01") && month.equals("01") && !isTommrow) {
      gc.roll(java.util.Calendar.YEAR, isTommrow);
      gc.getGregorianChange();
      _year = Integer.toString(gc.get(java.util.Calendar.YEAR));
    }
    return _year + _month + _day;
  }

  public static boolean isAlphabet(char c) {
    if (c < 'A' || c > 'z')
      return false;
    return true;
  }

  public static boolean isAlphabet(String s) {
    int len = s.length();
    for (int i = 0; i < len; i++) {
      if (!isAlphabet(s.charAt(i)))
        return false;
    }
    return true;
  }

  /*
   * public static void main(String[] args) { String s = "000123400";
   * 
   * // System.out.println( "String [" + s + "] of cutLeft Value is " + //
   * StringUtil.cutLeft( "000123400", '0' )) ; // System.out.println( "String [" +
   * s + "] of cutRight Value is " + // StringUtil.cutRight( "000123400", '0' )) ;
   * 
   * // System.out.println( "Trim : [" + trimRight( "a " ) + "]" ) ; //
   * System.out.println( "Trim : [" + trimRight( "aa " ) + "]" ) ; //
   * System.out.println( "Trim : [" + trimRight( " " ) + "]" ) ; //
   * System.out.println( "Trim : [" + trimRight( "aaaaa" ) + "]" ) ; //
   * System.out.println( "Trim Left : [" + trimLeft( " a" )+ "]") ; //
   * System.out.println( "Trim Left : [" + trimLeft( " a a" )+ "]") ; //
   * System.out.println( "Trim Left : [" + trimLeft( " " )+ "]") ; //
   * System.out.println( "Trim Left : [" + trimLeft( "aaaaa" ) + "]" ) ; }
   */

  public static String removeStringBlock(String s, String from, String to) {
    int sp = 0;
    int ep = 0;
    String smallStr = s.toLowerCase();

    while (sp <= smallStr.length()) {
      sp = smallStr.indexOf(from);
      ep = smallStr.indexOf(to);

      if (ep < 0 || sp < 0) {
        return s;
      } else {
        s = s.substring(0, sp) + s.substring(ep + to.length());
        smallStr = smallStr.substring(0, sp) + smallStr.substring(ep + to.length());
      }
    }
    return s;
  }

  public static String replaceString(String s, String from, String to) {
    int sp = 0;
    int ep = 0;
    String smallStr = s;

    while (s.indexOf(from) >= 0) {
      sp = 0;
      ep = 0;
      smallStr = s;

      while (ep <= smallStr.length()) {
        ep = smallStr.indexOf(from, sp);
        sp = ep + to.length();

        if (ep < 0) {
          return s;
        } else {
          s = s.substring(0, ep) + to + s.substring(ep + from.length());
          smallStr = smallStr.substring(0, ep) + to + smallStr.substring(ep + from.length());
        }
      }
    }
    return s;
  }

  public static String stringCleanUp(String s, String sToMatch, boolean isToKeep) {
    int size = s.length();
    StringBuffer sb = new StringBuffer(size);
    if (!isToKeep) {
      for (int i = 0; i < size; i++) {
        if (sToMatch.indexOf(s.charAt(i)) == -1) {
          sb.append(s.charAt(i));
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (sToMatch.indexOf(s.charAt(i)) != -1) {
          sb.append(s.charAt(i));
        }
      }
    }
    return sb.toString();
  }

  public static String trimLeft(String s) {
    int len = s.length();
    int st = 0;
    char[] val = s.toCharArray();
    for (st = 0; st < len; ++st) {
      if (val[st] > ' ')
        break;
    }
    return st > 0 ? s.substring(st) : s;
    // return (st == -1) ? "" : s.substring(st) ;
  }

  public static String trimRight(String s) {
    int len = s.length();
    int st = 0;
    char[] val = s.toCharArray();
    for (st = len - 1; st >= 0; --st) {
      if (val[st] > ' ')
        break;
    }
    return (st != -1) ? s.substring(0, st + 1) : "";
  }

  /**
   * 양쪽으로 자리수만큼 문자 채우기
   *
   * @param str         원본 문자열
   * @param size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
   * @param strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
   * @return
   */
  public static String getCPad(String str, int size, String strFillText) {
    int intPadPos = 0;
    for (int i = str.getBytes().length; i < size; i++) {
      if (intPadPos == 0) {
        str += strFillText;
        intPadPos = 1;
      } else {
        str = strFillText + str;
        intPadPos = 0;
      }
    }
    return str;
  }

  /**
   * 왼쪽으로 자리수만큼 문자 채우기
   *
   * @param str         원본 문자열
   * @param size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
   * @param strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
   * @return
   */
  public static String getLPad(String str, int size, String strFillText) {
    for (int i = str.getBytes().length; i < size; i++) {
      str = strFillText + str;
    }
    return str;
  }

  /**
   * 오른쪽으로 자리수만큼 문자 채우기
   *
   * @param str         원본 문자열
   * @param size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
   * @param strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
   * @return
   */
  public static String getRPad(String str, int size, String strFillText) {
    for (int i = str.getBytes().length; i < size; i++) {
      str += strFillText;
    }
    return str;
  }

  public static String cutRight(String s, int limit) {
    if (s == null) {
      return "";
    }

    if (s.length() <= limit) {
      return s;
    } else {
      return s.substring(0, limit);
    }
  }

  public static boolean isEmpty(String str) {
    if (str == null || str.trim().length() == 0) {
      return true;
    }

    return false;
  }

  public static String coalesce(String s1, String s2) {
    if (s1 != null && !s1.equals("")) {
      return s1;
    } else if (s2 != null && !s2.equals("")) {
      return s2;
    } else {
      return "";
    }
  }

  /**
   * HASHMAP KEY에 대한 대,소문자 변환
   *
   * @param map               변환대상 HashMap
   * @param isLetterUpperCase 대,소문자 처리 구분 (true:대문자, false:소문자)
   * @return HashMap
   */

  @SuppressWarnings("unchecked")
public static <K, V> HashMap<K, V> convertMapKeyLetterCase(HashMap<K, V> map, boolean isLetterUpperCase) {
    HashMap<K, V> tmp = new HashMap<K, V>();
    map.entrySet().stream().forEach(entry -> {
      if (isLetterUpperCase == true) {
        tmp.put((K) StringUtils.upperCase(StringUtils.getNull(entry.getKey())), entry.getValue());
      } else {
        tmp.put((K) StringUtils.lowerCase(StringUtils.getNull(entry.getKey())), entry.getValue());
      }
    });
    return tmp;
  }

  /**
   * HASHMAP KEY에 대한 대,소문자 변환
   *
   * @param list              변환대상 List<HashMap<K, V>>
   * @param isLetterUpperCase 대,소문자 처리 구분 (true:대문자, false:소문자)
   * @return ArrayList<HashMap<K, V>>
   */
  public static <K, V> ArrayList<HashMap<K, V>> convertListMapKeyLetterCase(List<HashMap<K, V>> list,
      boolean isLetterUpperCase) {
    ArrayList<HashMap<K, V>> list2 = new ArrayList<HashMap<K, V>>();
    list.stream().forEach(s -> {
      list2.add(convertMapKeyLetterCase(s, isLetterUpperCase));
    });

    return list2;
  }

  public static String GetXmlMsgId() {
      
      Random random = new Random();
      return DateUtil.getNow("yyyyMMddHHmmssSSS") + String.format("%04d",random.nextInt(10000));
  }

  /**
   * (2021.04.07. jkkim) Object가 null일 경우 ""를 반환, null이 아닐 경우 String 형으로 변환해서 반환
   * @param  Object 
   * @return String
   */
  public static String getEmptyValue(Object obj) {
      return obj == null ? "" : obj.toString();
  }
  
  public static String getErrorMsg(Exception e) {
      StringWriter sw = new StringWriter(); 
      e.printStackTrace(new PrintWriter(sw)); 
      return sw.toString(); 
  }

  

  
  public static List<ComMap> changeHashMapListToComMapList(List<HashMap<String,String>> tempList) {
      List<ComMap> returnList = new ArrayList<ComMap>();
      for(HashMap<String,String> temp : tempList) {
          ComMap cahngeMap = new ComMap();
          cahngeMap.addAll(temp);
          returnList.add(cahngeMap);
      }
      
      return returnList;
  }
  
  public static ComMap changeHashMapToComMap(HashMap<String,String> temp) {
      ComMap returnComMap = new ComMap();
      returnComMap.addAll(temp);
      return returnComMap;
  }
}