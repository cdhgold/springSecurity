/*=========================================================
* Copyright(c) 2020 남성해운
*@FileName :  ComMap.java
*@Create Data :2020. 10. 5. 
*@History :
* 1.0, 2020.10.05최초작성
* ehddh,   2020. 10. 5. 
===========================================================*/
package com.mang.example.security.comm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
 
 

/**
 * 클래스설명: 공통맵
 * 
 * <pre>
 * 프로그램관리
 * </pre>
 * 
 * @author
 */

@SuppressWarnings({ "rawtypes" })
public class ComMap extends java.util.HashMap implements Externalizable {

  private boolean isSet = false;

  public ComMap() {
    super();
  }

  public ComMap(int arg0) {
    super(arg0);
  }

  public ComMap(int arg0, float arg1) {
    super(arg0, arg1);
  }

  @SuppressWarnings("unchecked")
  public ComMap(Map arg0) {
    super(arg0);
  }

  public void set() {
    this.isSet = true;
  }

  public boolean isSet() {
    return this.isSet;
  }

  public void addAll(Map map) {
    if (map == null) {
      return;
    }
    Iterator iterator = map.entrySet().iterator();
    do {
      if (!iterator.hasNext()) {
        break;
      }
      Entry entry = (Entry) iterator.next();
      Object value = entry.getValue();
      if (value != null) {
        Object toadd;
        if (value instanceof String[]) {
          String values[] = (String[]) (String[]) value;
          if (values.length > 1) {
            toadd = new ArrayList<String>(Arrays.asList(values));
          } else if (values.length == 1) {
            toadd = values[0];
          } else {
            toadd = "";
          }
        } else {
          toadd = value;
        }
        put(entry.getKey().toString(), toadd);
      }
    } while (true);
  }

  public String getEscape4Js(String key) {
    return getEscape4Js(key, "");
  }

  public String getEscape4Js(String key, String def) {
    String str;

    if (get(key) == null) {
      str = "";
    } else {
      str = String.valueOf(get(key));
    }

    str = StringUtils.replace(str, "\\", "\\\\");
    str = StringUtils.replace(str, "\r\n", "\\n");
    str = StringUtils.replace(str, "\r", "\\n");
    str = StringUtils.replace(str, "\n", "\\n");
    str = StringUtils.replace(str, "'", "\\'");
    str = StringUtils.replace(str, "\"", "\\\"");

    return str.equals("") ? def : str;
  }

  public String getToHTML(String key) {
    String str = String.valueOf(getString(key));

    str = StringUtils.replace(str, "&", "&amp;");
    str = StringUtils.replace(str, "<", "&lt;");
    str = StringUtils.replace(str, ">", "&gt;");
    str = StringUtils.replace(str, "\r\n", "<br>");
    str = StringUtils.replace(str, "\"", "&quot;");
    str = StringUtils.replace(str, " ", "&nbsp;");

    return str;
  }

  public String getToINPUT(String key, String def) {
    String str = String.valueOf(getString(key));

    if (str.length() == 0) {
      str = def;
    }
    return str;
  }

  public String getToINPUT(String key) {
    return getToINPUT(key, "");
  }

  public String getString(String key) {
    return getString(key, "");
  }

  public String getString(String key, String def) {

    if (this.get(key) == null)
      return def;
    else {
      String str = String.valueOf(this.get(key));
      if (str.equals(""))
        str = def;

      return StringUtils.getNull(str);
    }
  }

  public int getInt(String key) {
    Object obj = super.get(key);
    int ret = 0;
    if (obj instanceof Number) {
      ret = ((Number) obj).intValue();
    } else {
      try {
        ret = Integer.parseInt(obj.toString());
      } catch (Exception ex) {
        ret = 0;
      }
    }
    return ret;
  }

  public int[] getInts(String key) {
    int[] ints = null;
    try {
      String[] obj = (String[]) super.get(key);
      ints = new int[obj.length];
      for (int i = 0; i < obj.length; i++) {
        ints[i] = getInt(obj[i]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ints;
  }

  public double getDouble(String key) {
    double ret = 0;
    Object obj = super.get(key);
    if (obj == null)
      return 0;
    try {
      ret = Double.parseDouble(obj.toString());
    } catch (Exception ex) {
      return 0;
    }
    return ret;
  }

  public double getDouble(String key, double defaultVal) {
    double ret = 0;
    Object obj = super.get(key);
    if (obj == null)
      return defaultVal;
    try {
      ret = Double.parseDouble(obj.toString());
    } catch (Exception ex) {
      return defaultVal;
    }
    return ret;
  }

  public String getPersent(String key) {
    String str = "";
    NumberFormat format = NumberFormat.getPercentInstance();
    double value = this.getDouble(key);
    str = format.format(value);
    return str;
  }

  public String getDateFormat(String key, String type) {
    String str = "";
    Object o = null;
    SimpleDateFormat in = new SimpleDateFormat("yyyyMMddkkmmss");
    SimpleDateFormat out = new SimpleDateFormat(type);

    o = this.get(key);
    if (o == null || o.toString().trim().equals(""))
      return "-";
    try {
      str = o instanceof String ? out.format(in.parse((String) o)) : out.format((Date) o);
    } catch (ParseException e) {
      return "-";
    }

    return str;
  }

  public String getDateFormat(String key, String fType, String tType) {
    String str = "";
    String s = null;
    SimpleDateFormat in = new SimpleDateFormat(fType);
    SimpleDateFormat out = new SimpleDateFormat(tType);

    // s = this.getString(key).trim();
    s = key;
    if (s.equals(""))
      return "-";
    try {
      str = out.format(in.parse(s));
    } catch (ParseException e) {
      return "-";
    }

    return str;
  }

  public String getNumberFormat(String key) {
    String str = "";
    str = NumberFormat.getInstance().format(this.getDouble(key));
    return str;
  }

  public String getCurrencyFormat(String key) {
    String str = "";
    double d = this.getDouble(key);
    if (d == 0)
      return "-";
    str = NumberFormat.getCurrencyInstance(Locale.KOREA).format(d);
    return str;
  }

  @SuppressWarnings("unchecked")
  public Object put(Object arg0, Object arg1) {
    arg0 = arg0.toString();
    return super.put(arg0, arg1);
  }

  @SuppressWarnings("unchecked")
  public Object putOrg(Object arg0, Object arg1) {
    return super.put(arg0, arg1);
  }

  @Override
  public Object get(Object arg0) {
    arg0 = arg0.toString();
    String content = null;
    if (super.get(arg0) instanceof Clob) {
      try {
        Clob lobData = ClobTransport.wrap(super.get(arg0));
        Reader in = lobData.getCharacterStream();
        java.io.Writer sw = new StringWriter();
        char buffer[] = new char[4096];
        int n;
        while ((n = in.read(buffer)) != -1)
          sw.write(buffer, 0, n);
        content = sw.toString();

      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return content;
    } else if (super.get(arg0) instanceof Integer) {    // 스크립트에서 숫자형으로 넘어올 경우 mybatis 에서 @org.apache.commons.lang3.StringUtils@isNotEmpty 함수 사용이 불가하여 Integer 를 강제로 String 으로 return 하도록 변경 
        return StringUtils.getNull(super.get(arg0).toString());
    } else if (super.get(arg0) instanceof String) {
        return StringUtils.getNull(super.get(arg0).toString());
    }else if (super.get(arg0) instanceof HashMap) {
        ComMap returnMap = new ComMap();
        returnMap.addAll((HashMap)super.get(arg0));
        return returnMap;
    }else if (super.get(arg0) instanceof List) {
        
        List<HashMap<String, String>> tempList = (List) super.get(arg0);
        List returnList = new ArrayList<ComMap>();
        for(HashMap<String, String> tempMap : tempList) {
            ComMap cahngeMap = new ComMap();
            cahngeMap.addAll(tempMap);
            returnList.add(cahngeMap);
        }
        return returnList;
    } else {
      return super.get(arg0);
    }
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    // TODO Auto-generated method stub

  }

}
