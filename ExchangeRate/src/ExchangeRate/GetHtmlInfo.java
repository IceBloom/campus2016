package ExchangeRate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class GetHtmlInfo {

	/**
	 *   ��ȡ���ʱ������excel
	 *    htmlUrl����ҳ����             excelPath�������excel·��
	 */
	public void GetHtmlInfoToExcel(String htmlUrl, String excelPath) {
		GetAllData(htmlUrl,excelPath);    //��ȡ���л�������
		try {
			ProcessExcel(excelPath);    //�����л��������У���ȡ��Ԫ��ŷԪ����Ԫ�Ļ���
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	/**
	 *   ��ȡ���л�������
	 *   htmlUrl����ҳ����             excelPath�������excel·��
	 */
	private void GetAllData(String htmlUrl,String excelPath) {
		try {
			// ������
			URLConnection con = new URL(htmlUrl).openConnection();
			// ��ȡ������
			InputStream in = (InputStream) con.getContent();
			// ����Ϊ����ļ�
			FileOutputStream out = new FileOutputStream(excelPath);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   ��ȡ��Ԫ��ŷԪ����Ԫ�Ļ���
	 *   excelPath�������excel·��
	 */
	private static void ProcessExcel(String excelPath) throws BiffException,
			IOException, WriteException {

		InputStream instream = new FileInputStream(excelPath);
		Workbook workBook = Workbook.getWorkbook(instream);
		// ��ȡ��һ��Sheet��
		Sheet readsheet = workBook.getSheet(0);
		// ��ȡSheet������������������
		int rsColumns = readsheet.getColumns();
		// ��ȡSheet������������������
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File(
				excelPath), workBook);
		WritableSheet writeSheet = writableWorkbook.getSheet(0);

		List<Integer> removeCol = new LinkedList<>();      //�洢Ҫɾ������id

		
		for (int j = 0; j < rsColumns; j++) {     //������һ�е�ÿһ��
			Cell cell = readsheet.getCell(j, 0);
			String content = cell.getContents();   

			if (!content.equals("����") && !content.equals("��Ԫ")
					&& !content.equals("ŷԪ") && !content.equals("��Ԫ"))
				removeCol.add(j);    //��÷����ڡ���Ԫ��ŷԪ����Ԫ����id��Ҳ�ǽ�Ҫɾ������
		}

		int removeNum = 0;    //����ɾ��һ�����ݣ�excel���Զ��ϲ�����������ôα�����Ϊƫ�Ʊ���
		for (int i = 0; i < removeCol.size(); ++i)
		{
			int col = removeCol.get(i);
			writeSheet.removeColumn(col - removeNum);       //ɾ����Ч���ݣ��Ӷ��������ں���Ԫ��ŷԪ����Ԫ�Ļ���
			removeNum++;
		}

		writableWorkbook.write();
		writableWorkbook.close();

        
		workBook.close();
	}
}
