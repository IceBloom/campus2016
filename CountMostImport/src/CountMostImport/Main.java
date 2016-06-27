package CountMostImport;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 * ����ָ����ĿĿ¼�£�������Ϊ��javaԴ�ļ�Ŀ¼���У�ͳ�Ʊ�import�����࣬ǰʮ����ʲô
 * ˼·���Ȱ�Դ�ļ�Ŀ¼������java�ļ��ҳ���Ȼ��ͳ����Щ�ļ��У�import������ǰ10����
 */

public class Main {

	 private static List<Map.Entry<String, Integer>> m_MaxImportList;     //�洢impot������
	 private static List<String> m_JavaFilesPath;      //�洢Դ�ļ�Ŀ¼������java�ļ�·��
	
	public static void main(String[] args) {
		String srcPath = Paths.get("src").toAbsolutePath().toString(); // ԴĿ¼src��·��
		m_JavaFilesPath =  new JavaFilesPath().GetJavaFliesPath(srcPath); // ��ȡĿ¼src������JAVA�ļ���·��
	
		m_MaxImportList = new CountImportNum().getMaxImportClass(m_JavaFilesPath);    //ͳ��import����
		
		//���import����ǰ10�Ľ��
		  for(int i=0;i < 10 && i<m_MaxImportList.size();i++)
	        {
	            System.out.println("class: "+m_MaxImportList.get(i).getKey()
	            		+ "   value:"+m_MaxImportList.get(i).getValue());
	        
	        }

	}

}
