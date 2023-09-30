/*=========================================================
* Copyright(c) 2020 남성해운
*@FileName :  MakeExcel.java
*@Create Data :2020. 10. 5. 
*@History :
* 1.0, 2020.10.05최초작성
* ehddh,   2020. 10. 5. 
===========================================================*/
package com.mang.example.security.comm;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import net.sf.jxls.transformer.XLSTransformer; 

   
/**
 * @author ehddh
 *
 */
public class MakeExcel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(MakeExcel.class);

    private static final String EXCEL_PATH = "static/excel";


    public MakeExcel() {}

    public String get_Filename() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddmmmm");
        return ft.format(new Date());
    }

    public String get_Filename(String pre) {
        return pre + get_Filename();
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param filename
     * @param templateFile
     * @throws Exception
     */
    public void download(
                  HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String filename, String templateFile 
                , boolean serverType , String staticResourceUploadDir) throws Exception {
        
        
        InputStream is = new ClassPathResource(EXCEL_PATH + "/" + templateFile).getInputStream();

        XLSTransformer transformer = new XLSTransformer();
        Workbook resultWorkbook = transformer.transformXLS(is, model);
        
        StringBuffer contentDisposition = new StringBuffer();
        contentDisposition.append("attachment;fileName=\"");
        contentDisposition.append(encodeFileName(filename) + ".xlsx");
        contentDisposition.append("\";");
        response.setHeader("Content-Disposition", contentDisposition.toString());
        response.setContentType("application/x-msexcel");
        
        if(model.get("sheetNameList") != null){
            @SuppressWarnings("unchecked")
            List<String> sheetNameList = (List<String>)model.get("sheetNameList");
            for(int i = 0; i < sheetNameList.size() ; i++){
                resultWorkbook.setSheetName(i,sheetNameList.get(i));
            }
        }
        
        logger.debug("===========================================================excel file create start===========================");
        logger.debug("===========================================================excel file create end==========================="+staticResourceUploadDir+"/data4/MAIL/"+filename+".xlsx");
        
        //서버에 파일 저장
        BufferedOutputStream bufferedOutputStream = null;            
        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(staticResourceUploadDir+"/data4/MAIL/"+filename+".xlsx"));
        resultWorkbook.write(bufferedOutputStream);

        logger.debug("===========================================================excel file create end===========================");
        
        //엑셀 다운로드
        resultWorkbook.write(response.getOutputStream());
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param filename
     * @param templateFile
     * @throws Exception
     */
    public void download2(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String filename, String templateFile , boolean serverType , String staticResourceUploadDir) throws Exception {
        
        
        InputStream is = new ClassPathResource(EXCEL_PATH + "/" + templateFile).getInputStream();

        XLSTransformer transformer = new XLSTransformer();
        Workbook resultWorkbook = transformer.transformXLS(is, model);
        
        StringBuffer contentDisposition = new StringBuffer();
        contentDisposition.append("attachment;fileName=\"");
        contentDisposition.append(encodeFileName(filename) + ".xlsx");
        contentDisposition.append("\";");
        response.setHeader("Content-Disposition", contentDisposition.toString());
        response.setContentType("application/x-msexcel");
        
        if(model.get("sheetNameList") != null){
            @SuppressWarnings("unchecked")
            List<String> sheetNameList = (List<String>)model.get("sheetNameList");
            for(int i = 0; i < sheetNameList.size() ; i++){
                resultWorkbook.setSheetName(i,sheetNameList.get(i));
            }
        }
        
        //엑셀 다운로드
        resultWorkbook.write(response.getOutputStream());
    }


    /**
     * @param request
     * @param response
     * @param model
     * @param filename
     * @param templateFile
     * @throws Exception
     */
    public void download3(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String filename, String templateFile , boolean serverType , String staticResourceUploadDir) throws Exception {
        
        
        InputStream is = new ClassPathResource(EXCEL_PATH + "/" + templateFile).getInputStream();

        XLSTransformer transformer = new XLSTransformer();
        Workbook resultWorkbook = transformer.transformXLS(is, model);
        
        StringBuffer contentDisposition = new StringBuffer();
        contentDisposition.append("attachment;fileName=\"");
        contentDisposition.append(encodeFileName(filename) + ".xls");
        contentDisposition.append("\";");
        response.setHeader("Content-Disposition", contentDisposition.toString());
        response.setContentType("application/x-msexcel");
        
        if(model.get("sheetNameList") != null){
            @SuppressWarnings("unchecked")
            List<String> sheetNameList = (List<String>)model.get("sheetNameList");
            for(int i = 0; i < sheetNameList.size() ; i++){
                resultWorkbook.setSheetName(i,sheetNameList.get(i));
            }
        }
        
        //엑셀 다운로드
        resultWorkbook.write(response.getOutputStream());
    }

    private String encodeFileName(String filename) throws UnsupportedEncodingException {
        return URLEncoder.encode(filename, "UTF-8");
    }
}
