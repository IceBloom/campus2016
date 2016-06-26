package nowcoder;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * ���⣺����ָ����ĿĿ¼�£�������Ϊ��javaԴ�ļ�Ŀ¼�У���ͳ�Ʊ�import�����࣬ǰʮ����ʲô��
 *
 Created by liwen on 2016/6/16.
 *
 */


public class MyCountMostImport {

    static HashMap<String,Integer> importCount;
    //���import����ǰNUMBER����
    public static final int NUMBER = 10;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("������ָ����ĿĿ¼�ľ���·��:");
        while (scan.hasNext()) {
            importCount = new HashMap<String,Integer>();
            countMostImport(scan.nextLine());
            System.out.println("������ָ����ĿĿ¼�ľ���·��:");
        }
    }

    public static void countMostImport(String path) {
        File file = new File(path);
        
        if (!file.isDirectory()) 
        { //Ŀ¼������ 
        	System.out.println("��ĿĿ¼·�������ڣ�");
        	return;
        } 
        //���������ļ�������ͳ��
        filesList(file, importCount);
        
        //��ͳ�ƽ���������򲢴�ӡ
        printImportClass(importCount);
        
}
    public static void printImportClass(HashMap<String, Integer> importCount) {
    	//�Զ�HashMap��ֵ��������
    	 List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(importCount.entrySet());  
         Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {  
             //��������  
             @Override  
             public int compare(Entry<String, Integer> o1, Entry<String,Integer> o2) {
            	 if(o2.getValue().compareTo(o1.getValue())==0){
            		 //���ֵ��ȣ������ֵ����н�������
            		 String str1 = o1.getKey().toLowerCase();//ת����Сд����Ϊ��ASICC�д�д�ַ�һ����Сд��ĸǰ�棬���ֵ����в���
            		 String str2 = o2.getKey().toLowerCase();
            		 return str1.compareTo(str2);
            	 }
            	 else
                 return o2.getValue().compareTo(o1.getValue());  
             }
         });  
         
         //���������import���ǰNUMBER������
         int count = 0;
         for (Entry<String, Integer> mapping : list) { 
        	 if( count<NUMBER ){
        		 System.out.println(mapping.getKey() + "------��import�Ĵ���Ϊ------" + mapping.getValue());  
        		 count++;
        	 }
        	 else  return;	 
         }  
    }
    public static void filesList(File file, HashMap<String, Integer> importCount) {
        File[] files = file.listFiles();
        for(File fileName : files) {
        	//������ļ��У�����ÿ���ļ����ļ���
            if(fileName.isDirectory()) {
                filesList(fileName, importCount);
            } 
            else {
                String suffix = fileName.getName().substring(fileName.getName().lastIndexOf(".") + 1);
                //�ж��Ƿ���java�ļ�
                if(suffix.equals("java")) {
                    BufferedReader bufferedReader = null;
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                        String sentence = null;
                        while((sentence = bufferedReader.readLine()) != null){
                        	String trimSentence = sentence.trim();
                            //�ж��Ƿ�Ϊ���ð����
                            if(trimSentence.indexOf("import ") == -1 || !trimSentence.trim().startsWith("import "))
                                continue;

                            String importClassName = trimSentence.substring(  trimSentence.indexOf("import ")+"import ".length()  ).trim();
                            if(importCount.containsKey(importClassName))
                                importCount.put(importClassName, importCount.get(importClassName)+1);
                            else
                                importCount.put(importClassName, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}