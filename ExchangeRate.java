/**
* �����������⣺�����ӽ��쿪ʼ��ȥ30��ʱ����й��������й���������һ����м�ۣ��õ�����Ҷ���Ԫ��ŷԪ���۱ҵĻ��ʣ��γ�excel�ļ������
* ������Դ��http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action

Created by liwen on 2016/6/21.
*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExchangeRate 
{
    /**
     * ������Դ������ѯ��ַ
     */
    private static final String EXCHANGERATE_WEBSITE = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
	
   /**
    * �õ�����������ݲ�����ΪExcel����
    * @param queryParameter Ϊ��ȡ�����趨��URL
    */
    public void getExchangeRateData(String queryParameter)
    {
    	  //����վ�������ӣ���ȡ��վ����
    	  Connection connection =  Jsoup.connect(EXCHANGERATE_WEBSITE+queryParameter);//�����վ��Connection�ӿ�
    	  Document document = null;  //�洢��ҳHTML���ݵ��ļ�
    	    try{
    	    	document = connection.timeout(10000).get(); //�õ���ҳHTML����
    	    }
    	    catch(IOException e){
    	    	e.printStackTrace();
    	    	System.out.println("Connection error!");
    	    }
    	    
    	  //��ȡ���ڣ���Ԫ��ŷԪ���۱Ҷ�����һ�������
    	    Elements elements = document.getElementsByClass("first");//��ȡ30�������һ����м������
            String exchangeRateData[][] = new String[4][30];
            for(int i = 0; i < 30; i++){
                exchangeRateData[0][i] = elements.get(i).child(0).text();
                exchangeRateData[1][i] = elements.get(i).child(1).text();
                exchangeRateData[2][i] = elements.get(i).child(2).text();
                exchangeRateData[3][i] = elements.get(i).child(4).text();
            }
           
            
          //�����ݴ洢��Excel�ļ���
            WritableWorkbook exchangeRateExcel = null;
            FileOutputStream outputStream = null;
            try
    		{
            	outputStream = new FileOutputStream(new File("ExchangeRateExcel.xls"));
    			exchangeRateExcel = Workbook.createWorkbook(outputStream);
    			//��������һ��ʱ������õ�Ԫ���ʽ
    	        WritableSheet sheetOfExchangeRateExcel = exchangeRateExcel.createSheet("����һ����м���б�", 1);
    	        WritableFont writableFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, true);
                WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont);
                writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);

    	        //���ñ�ͷ
                sheetOfExchangeRateExcel.addCell(new Label(0,0,"����", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(1,0,"��Ԫ", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(2,0,"ŷԪ", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(3,0,"�۱�", writableCellFormat));
                
                //����ָ��λ��д������
                for(int i = 0; i < 30; i++){
                    for(int j = 0; j < 4; j++){
                    	sheetOfExchangeRateExcel.addCell(new Label(j, 1+i,exchangeRateData[j][i]));
                    }
                }
    	        
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
            finally {
            	try {
                    if(exchangeRateExcel != null) {
                    	exchangeRateExcel.write();
                    	exchangeRateExcel.close();
                    }
                    if(outputStream != null) {
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 	    
    }
    
   
    
    public static void main(String[] args)
     {
    	 //�������ڸ�ʽ
	     SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd");
	     Date date = new Date();
	     //��õ������ڣ���Ϊ��ҳ�в��ҽ�������
	     String endtimeOfQuery = simpleDateFormat.format(date);
	     //��ò�����ʼ����
	     String starttimeOfQuery = simpleDateFormat.format(date.getTime()-50*24*60*60*1000L);
	     //Ϊ��ȡ�����趨��URL
	     String queryParameter = EXCHANGERATE_WEBSITE+"?"+"projectBean.startDate=" + starttimeOfQuery + "&projectBean.endDate=" + endtimeOfQuery;
	    
	     ExchangeRate exchangeRate  = new ExchangeRate();
	     //����ҳ��ȡ�������ݣ����ݻ�ȡ�����������	    
	     exchangeRate.getExchangeRateData(queryParameter);
	     
     }
}
