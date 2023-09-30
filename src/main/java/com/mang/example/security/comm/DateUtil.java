package com.mang.example.security.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.mang.example.security.comm.StringUtils;

/**
 * <pre>
 * kr.co.namsung.util 
 * DateUtil.java
 *
 * 설명 : 날짜 관련 함수를 정의한다.
 * </pre>
 * 
 * @since : 2015. 10. 23.
 * @author : Peter
 * @version : v1.0
 */
public class DateUtil {

    /**
     * <pre>
     * 설명 : 10자리 문자열로 되어 있는 시간 값을 1주일이 지나지 않았다면, 몇초전/몇분전/몇일전으로 표시해 주고 1주일이 지났다면 yyyy.MM.dd hh:mm 포맷으로 리턴.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param writtenTime 입력시간
     * @return String
     */
    public static String elapsedTime(String writtenTime) {
        if (writtenTime == null || StringUtils.isEmpty(writtenTime.trim())) {
            return "";
        }

        long writtenL = Long.parseLong(writtenTime) * 1000;

        Calendar writtenAdd7 = Calendar.getInstance();
        writtenAdd7.setTimeInMillis(writtenL);
        writtenAdd7.add(Calendar.DAY_OF_MONTH, 7);

        Calendar currCal = Calendar.getInstance();
        currCal.setTimeInMillis(System.currentTimeMillis());

        int compare = currCal.compareTo(writtenAdd7);
        String result = "";

        if (compare < 0) {
            // 1주일이 안 지났음.
            long currTimeL = currCal.getTimeInMillis();
            long diffL = (currTimeL - writtenL) / 1000;

            int diff = (int) diffL;

            if (diff < 60) {
                // 초로 리턴
                result = diff + "초전";
            } else if (diff < 3600) {
                // 분으로 리턴
                diff = diff / 60;
                result = diff + "분전";
            } else if (diff < 86400) {
                // 시간으로 리턴
                diff = diff / 3600;
                result = diff + "시간전";
            } else {
                diff = diff / 86400;
                // 일자로 리턴.
                result = diff + "일전";
            }
        } else {
            // 1주일이 지났음.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            result = formatter.format(new Date(writtenL));
        }

        return result;
    }

    /**
     * <pre>
     * 설명 : unix 타입의 시간을 변환해준다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param writtenTime unix타입 입력 값
     * @return String
     */
    public static String getTime(String writtenTime) {
        if (writtenTime == null || StringUtils.isEmpty(writtenTime.trim())) {
            return "";
        }

        long writtenL = Long.parseLong(writtenTime) * 1000;

        String result = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  a hh:mm");
        result = formatter.format(new Date(writtenL));

        return result;
    }

    /**
     * <pre>
     * 설명 : 현재시간을 리턴한다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param pattern 변경필요 패턴
     * @return String
     */
    public static String getNow(String pattern) {
        
        if (pattern == null || StringUtils.isEmpty(pattern.trim())) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String strDate = sdf.format(new Date());
        
        return strDate;
    }

    /**
     * <pre>
     * 설명 : 일자를 더하여 날짜를 리턴한다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param s string
     * @param day day 더할 일수
     * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기 (형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생)
     */
    public static String addDays(String s, int day) {
        return addDays(s, day, "yyyy-MM-dd");
    }

    /**
     * <pre>
     * 설명 : 
     * 변경이력 : 일자를 더하여 요청한 포멧으로 날짜를 리턴한다.
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param s string
     * @param day 더할 일수
     * @param format 날짜 포멧
     * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기(형식이 잘못 되었거나 존재하지 않는 날짜: null return)
     */
    public static String addDays(String s, int day, String format){
        try {
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
            java.util.Date date = check(s, format);

            date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));
            return formatter.format(date);
        } catch (ParseException pe) {

            return null;
        }
    }


    /**
     * <pre>
     * 설명 : 날짜 포멧을 확인 한다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param s 날짜
     * @return java.util.Date 날짜
     */
    public static java.util.Date check(String s) {
        try {
            return check(s, "yyyy-MM-dd");
        } catch (ParseException pe) {

            return null;
        }
    }

    /**
     * <pre>
     * 설명 : 날짜의 유효성을 검사하여 요청한 날짜 포멧으로 변경해서 리턴한다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param s 날짜
     * @param format 날짜 포멧
     * @return 날짜
     * @throws ParseException 날짜 변환 오류
     */
    public static java.util.Date check(String s, String format) throws ParseException {
        if (s == null) {
            throw new java.text.ParseException("date string to check is null", 0);
        }

        if (format == null) {
            throw new java.text.ParseException("format string to check date is null", 0);
        }

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
        java.util.Date date = null;
        try {
            date = formatter.parse(s);
        } catch (java.text.ParseException e) {
            throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
        }

        if (!formatter.format(date).equals(s)) {
            throw new java.text.ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"",0);
        }

        return date;
    }

    /**
     * <pre>
     * 설명 : yyyyMMdd 형태의 문자열을 yyyy-MM-dd로 변환.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param yyyyMMdd 입력문자열
     * @return String
     */
    public static String getDateFormat(final String yyyyMMdd) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        String ret = "";

        if (yyyyMMdd == null || yyyyMMdd.length() != 8) {
            return "";
        } else {
            try {
                ret = df2.format(df1.parse(yyyyMMdd));
            } catch (ParseException e) {
            }
        }

        return ret;
    }

    /**
     * <pre>
     * 설명 : HHmmss 형태의 문자열을 HH:mm:ss 형태로 변환.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param HHmmss 입력문자열
     * @return String
     */
    public static String getTimeFormat(final String HHmmss) {
        SimpleDateFormat df1 = new SimpleDateFormat("HHmmss");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");

        String ret = "";

        if (HHmmss == null || HHmmss.length() != 6) {
            return "";
        } else {
            try {
                ret = df2.format(df1.parse(HHmmss));
            } catch (ParseException e) {
            }
        }

        return ret;
    }

    /**
     * <pre>
     * 설명 : 현재 시간 및 날짜를 스트링 형태로 반환.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @return 현재날짜 및 시간
     */
    public static String getCurrDateTime() {
        return new SimpleDateFormat("yyyyMMdd_kkmm").format(new Date());
    }

    /**
     * <pre>
     * 설명 : 현재 시간 및 날짜를 스트링 형태로 반환.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @return 현재날짜 및 시간
     */
    public static String getTimeStampString() {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
        return formatter.format(new java.util.Date());
    }

    /**
     * <pre>
     * 설명 : Date 포맷에 따라 날짜를 반환해준다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param format date 포맷
     * @param date date
     * @return String 날짜
     */
    public static String getConvertDate(String format, Date date) {
        String convertDate = "";

        if (format == null || format.equals("")) {
            format = "yyyyMMddHHmmss";
        }

        SimpleDateFormat df = new SimpleDateFormat(format);
        convertDate = df.format(date);

        return convertDate;
    }

    /**
     * <pre>
     * 설명 : 현재시간에 입력받은 분을 계산하여 Date형으로 반환한다.
     * 변경이력 : 
     * -----------------------------------------------------------------
     * 변경일       작성자       변경내용  
     * ------------ ------------ ---------------------------------------
     * 2015. 10. 23. Peter          최초 작성 
     * -----------------------------------------------------------------
     * </pre> 
     * @since : 2015. 10. 23.
     * @author : Peter
     * @param minite 연산 minite
     * @return 연산된 날짜
     */
    public static Date calculateMinite(int minite) {
        Date date = null;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minite);

        date = new Date(cal.getTimeInMillis());

        return date;
    }
}