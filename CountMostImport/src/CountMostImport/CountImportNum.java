package CountMostImport;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ͳ��java�ļ��У���ͬ���import���������õ���������10��
 */

public class CountImportNum {
	Map<String, Integer> m_CountImportMap = new HashMap<>();

	public List<Map.Entry<String, Integer>> getMaxImportClass(List<String> javaFilesPath) {
		CountNum(javaFilesPath);     //������import������ȡ�������ŵ�m_CountImportMap
		
		 /* ��ÿ���ļ���import������������ */
        List<Map.Entry<String, Integer>> list
                = new ArrayList<>(m_CountImportMap.entrySet());
        // �����Ƚ���
                list.sort(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                        if(obj1.getValue()<obj2.getValue())
                            return 1;
                        else if(obj1.getValue()> obj2.getValue())
                            return -1;
                        return 0;
                    }
                });
return list;
	}

	/**
	 * ͳ��Java�ļ���Ч����
	 * @param javaFilesPath  ����Java�ļ���·��
	 */
	public void CountNum(List<String> javaFilesPath) {
		//����ÿ��java�ļ�
		for (String path : javaFilesPath) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(path));     //��ȡ��ǰjava�ļ�����
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				while (br.ready()) {
					String line = br.readLine().trim(); // ��ȡһ���ַ�

					if (line.startsWith("public") || line.startsWith("class")) // �����ֹ࣬ͣ����
						break;

					if (line.startsWith("import") && line.charAt(6) != '*'
							&& !line.startsWith("import static")) // ����import���ų�import *��import static
					{
						String className = line.substring(7, line.length() - 1);    //��ȡ����
						if (m_CountImportMap.containsKey(className)) { // �����Ѵ���
							Integer val = m_CountImportMap.get(className);
							m_CountImportMap.put(className, val + 1);    //����+1
						} else
							// ����������
							m_CountImportMap.put(className, 1);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
