package ExchangeRate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {

	private static String excelPath = "Data.xls";      //�����excel·��
	
	public static void main(String[] args) {
		
		Calendar c = Calendar.getInstance();
		Date endDay = c.getTime();   //��ý����ʱ��
		c.add(Calendar.DATE, -30);
		Date startDay = c.getTime();   //���30��ǰ������
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //��ʽ��
		// ��ʼʱ��
		String startDayString = sdf.format(startDay);
		// ����ʱ��
		String endDayString = sdf.format(endDay);
		// ����URL�������ӿ�ֱ�ӵõ���վ������excel
		String url = "http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?projectBean.startDate="
				+ startDayString + "&projectBean.endDate=" + endDayString;

		new GetHtmlInfo().GetHtmlInfoToExcel(url,excelPath);    //��ȡ���ʱ������excel
    

	}

}
