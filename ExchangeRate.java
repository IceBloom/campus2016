import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * ʹ��Post������������
 * ʹ��jsoup���н���
 * ʹ��poi����excel�ļ���д��
 * ��Ҫ����poi��jsoup����jar��
 * �������ҳ�����Ľ�����洢��������
 * ���ڽڼ�����Ϣ��û�нڼ��յĻ�����Ϣ
 */

public class ExchangeRate {
	private File crawlerResult;
	private String rateResult;
	public ExchangeRate(){
		crawlerResult = new File("C:\\Users\\Administrator\\Desktop\\crawlerResult.html");
		rateResult = "C:\\Users\\Administrator\\Desktop\\rateResult.xls";
	}
	public void crawler_30_days(){
		String address = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
		Date date = new Date();
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//��ȡϵͳ������
		String endDate = df.format(gc.getTime());
		gc.add(5, -29);
		String beginDate = df.format(gc.getTime());
		System.out.println("�ӣ�"+beginDate);
		System.out.println("����"+endDate);
		String param = "projectBean.startDate=" + beginDate + "&projectBean.endDate=" + endDate + "&queryYN=true";
		PrintWriter out = null;
	    BufferedReader in = null;
	    OutputStreamWriter outWriter = null;
		try{
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
	            // ����ͨ�õ���������
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        out = new PrintWriter(conn.getOutputStream());
	        out.print(param);
	        out.flush();
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        outWriter = new OutputStreamWriter(new FileOutputStream(crawlerResult),"UTF-8");
	        String str = null;
	        while((str = in.readLine()) != null){
	        	outWriter.write(str + "\n");
	        }
		}catch(Exception e){
			System.out.println("�ڷ���Post����ʱ��������");
			e.printStackTrace();
		}finally{
			try{
				if(outWriter != null)
					outWriter.close();
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void writeExcel(){ 
		Document doc = null;
		try {
			doc = Jsoup.parse(crawlerResult,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		Elements links = doc.getElementsByAttributeValue("class", "first");  
        HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("���ʱ�");  
        HSSFRow row = sheet.createRow((int) 0);   
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
  
        HSSFCell cell = row.createCell((int)0);  
        cell.setCellValue("����");  
        cell.setCellStyle(style);  
        cell = row.createCell((int) 1);  
        cell.setCellValue("��Ԫ");  
        cell.setCellStyle(style);  
        cell = row.createCell((int) 2);  
        cell.setCellValue("ŷԪ");  
        cell.setCellStyle(style); 
        cell = row.createCell((int) 3);  
        cell.setCellValue("�۱�");  
        cell.setCellStyle(style);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
			date = df.parse(df.format(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		GregorianCalendar gc =new GregorianCalendar();
		gc.setTime(date);
		gc.add(5,1);
		int rowno = 0;
		
		for(int i = 0; i < links.size(); ++i){
			String tmp = links.get(i).text().replace(Jsoup.parse("&nbsp;").text(), " ");
			String rate[] = tmp.split("[ ]{1,}");
			String d = rate[0];
			Date tmpDate = null;
			try {
				tmpDate = df.parse(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			while((date.getTime() - tmpDate.getTime())/(24*60*60*1000) > 1){
				row = sheet.createRow((int) rowno + 1);
				gc.add(5, -1);
				row.createCell((int) 0).setCellValue(df.format(gc.getTime()));  
	            row.createCell((int) 1).setCellValue("--");  
	            row.createCell((int) 2).setCellValue("--");  
	            row.createCell((int) 3).setCellValue("--");
	            ++rowno;
	            
	            date = gc.getTime();
			}
			row = sheet.createRow((int) rowno + 1);
			rowno++;
			row.createCell((int) 0).setCellValue(rate[0]);  
            row.createCell((int) 1).setCellValue(rate[1]);  
            row.createCell((int) 2).setCellValue(rate[2]);  
            row.createCell((int) 3).setCellValue(rate[4]); 
            gc.add(5,-1);//��һ��
            date = gc.getTime();
		}
		while(rowno < 30){
			row = sheet.createRow((int) rowno + 1);
			rowno++;
			gc.add(5,-1);
			row.createCell((int) 0).setCellValue(df.format(gc.getTime()));  
            row.createCell((int) 1).setCellValue("--");  
            row.createCell((int) 2).setCellValue("--");  
            row.createCell((int) 3).setCellValue("--"); 
		}
		try  
        {  
            FileOutputStream fout = new FileOutputStream(rateResult);  
            wb.write(fout);  
            fout.close(); 
            System.out.println("Excel�ļ����ɳɹ���λ�������ϣ���ΪrateResult.xls,û�нڼ��յĻ�����Ϣ��");
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
	}
	public static void main(String args[]) throws IOException{
		ExchangeRate rate = new ExchangeRate();
		rate.crawler_30_days();
		rate.writeExcel();
	}
}
