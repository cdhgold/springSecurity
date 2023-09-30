package com.mang.example.security.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import kr.co.namsung.icon.css.bkg.management.service.CssBkg3301Service;
import kr.co.namsung.icon.syscom.vo.ComMap;
import kr.co.namsung.icon.vms.opr.service.VmsOpr6205Service;
import kr.co.namsung.icon.vns.vnf.vo.RequestInfoVo;

public final class RESTfulConnUtil {
    
    /** Logger **/
    static Logger logger = LoggerFactory.getLogger(RESTfulConnUtil.class);
    
    @Autowired
    static CssBkg3301Service bkg3301Service;

    @Autowired
    VmsOpr6205Service vmsOpr6205Service;
    
    @SuppressWarnings("rawtypes")
	public static String HttpURLConnection(String target, String serviceUrl, String servicekey01, String servicekey02, String servicekey03, String servicekey04, String servicekey05, String servicekey06) throws Exception {

		RequestInfoVo requestInfoVo = new RequestInfoVo();
		
//		String url = "";
		// 접속 Live url
//		url = "http://15.164.31.94:8070/tradelens/service/" + serviceUrl;
        // 접속 Test url
//		url = "http://localhost:8070/tradelens/service/" + serviceUrl;
		
		requestInfoVo.setServiceinfo("N");
		requestInfoVo.setServicekey01(servicekey01);
		requestInfoVo.setServicekey02(servicekey02);
		requestInfoVo.setServicekey03(servicekey03);
		requestInfoVo.setServicekey04(servicekey04);
		requestInfoVo.setServicekey05(servicekey05);
		requestInfoVo.setServicekey06(servicekey06);
		
		String result = "0";
		Gson gson = new Gson ();
		String jsonString = gson.toJson(requestInfoVo);
		
		try {

			HashMap<String, String> mQry = new HashMap<String, String>();
			mQry.put("RESTSEQID", GetXmlMsgId());
			mQry.put("SOURCEID", "NSSL");
			mQry.put("TARGETID", target);
			mQry.put("SERVICEURL", "/tradelens/service/" + serviceUrl);
			mQry.put("INDATA", jsonString.toString());
			mQry.put("CURSTATUS", "R");

			logger.error("========================== >>>>> HttpURLConnection serviceUrl ::::: {}", serviceUrl);
			
			// 저장
            bkg3301Service.insertSYSRESTC01RestMap(mQry);
			
		} catch (Exception e) {
			
			result = "-1";
			

		} finally {

		}

		return result;
	}

	@SuppressWarnings("rawtypes")
    public static String HttpURLConnectionFBO(String target, String serviceUrl, String servicekey01, String servicekey02, String servicekey03, String servicekey04, String servicekey05, String servicekey06, String bukrs) throws Exception {

        RequestInfoVo requestInfoVo = new RequestInfoVo();

        //String url = "http://192.168.220.104:8070/fbo/service/" + serviceUrl;
        String url = "http://15.164.31.94:8070/fbo/service/" + serviceUrl;

        requestInfoVo.setServiceinfo("N");
        requestInfoVo.setServicekey01(servicekey01);
        requestInfoVo.setServicekey02(servicekey02);
        requestInfoVo.setServicekey03(servicekey03);
        requestInfoVo.setServicekey04(servicekey04);
        requestInfoVo.setServicekey05(servicekey05);
        requestInfoVo.setServicekey06(servicekey06);

        String result = "";
        Gson gson = new Gson ();
        String jsonString = gson.toJson(requestInfoVo);

        try {
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            con.setRequestProperty("Accept", "*/*");
            con.setRequestMethod("POST");
            con.setConnectTimeout(60000);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(jsonString.toString());
            wr.flush();

            int HttpResult = con.getResponseCode();
            
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                line = br.readLine();
                result = line;
                while ((line = br.readLine()) != null) {
                    result = result + line + "\n";
                    System.out.println(line + "\n");
                }
                br.close();
            } else {

                HashMap<String, String> mQry = new HashMap<String, String>();
                mQry.put("RESTSEQID", GetXmlMsgId());
                mQry.put("SOURCEID", "NSSL");
                mQry.put("TARGETID", target);
                mQry.put("SERVICEURL", "fbo/service/" + serviceUrl);
                mQry.put("INDATA", jsonString.toString());
                mQry.put("CURSTATUS", "R");
                mQry.put("BUKRS", bukrs);

                bkg3301Service.insertSYSRESTC01RestMap(mQry);

            }
        } catch (Exception e) {
            
            HashMap<String, String> mQry = new HashMap<String, String>();
            mQry.put("RESTSEQID", GetXmlMsgId());
            mQry.put("SOURCEID", "NSSL");
            mQry.put("TARGETID", target);
            mQry.put("SERVICEURL", "fbo/service/" + serviceUrl);
            mQry.put("INDATA", jsonString.toString());
            mQry.put("BUKRS", bukrs);

            bkg3301Service.insertSYSRESTC01RestMap(mQry);

        } finally {

        }

        return result;
    }
    
    public static void requestHttpURLConnection6205(ComMap reqMap, String serviceUrl) throws Exception {

        String urlStr = "http://" + serviceUrl;

logger.debug("================================ >>>>> url ::::: " + urlStr);
        
        Gson gson = new Gson ();
        String jsonString = gson.toJson(reqMap);

logger.debug("================================ >>>>> jsonString ::::: " + jsonString);

        try {
            URL urlObj = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            con.setRequestProperty("Accept", "*/*");
            con.setRequestMethod("POST");
            con.setConnectTimeout(60000);
            
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(jsonString); //json 형식의 message 전달
            wr.flush();
            
            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
logger.debug("================================ Response sb.toString :::::: >>>>> " + sb.toString());
            } else {
logger.debug("================================ Response con.getResponseMessage() :::::: >>>>> " + con.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetXmlMsgId() {
        
        Random random = new Random();
        return DateUtil.getNow("yyyyMMddHHmmssSSS") + String.format("%04d",random.nextInt(10000));
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String returnStr = "";
        try {
            returnStr = HttpURLConnection("GTD", "gtde468","INC19E00002","","","","","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnStr);
	}
}
