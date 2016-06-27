package CountMostImport;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * ��ȡsrc�ļ���������Java�ļ���·��
 */

public class JavaFilesPath
{

       List<String> m_JavaFilesPathList = new LinkedList<>();     //srcĿ¼������Java�ļ�·��
       LinkedList<File> m_ScanFileList = new LinkedList<>();    //Ҫ�����Ŀ¼,��ջ�洢���б�ջ�ã�
       
       
       //�������java�ļ�·��
       public  List<String> GetJavaFliesPath(String srcPath){
    	       RecordFilesPath(srcPath);     //��¼����Java�ļ�·��
    	   return m_JavaFilesPathList;
       }
       
   	   /* ɨ�貢����ָ��Ŀ¼srcPath�£�����java�ļ�·�� */
       public void RecordFilesPath(String srcPath)
       {
    	  File srcFile = new File(srcPath);
    	  m_ScanFileList.push(srcFile);
    	  
    	  //��ʼ����Ŀ¼
    	 while(m_ScanFileList.size() != 0)
    	  {
    		  File p_RootFile =  m_ScanFileList.pop();    //����ջ��Ԫ�ز�����
    		  File[] files = p_RootFile.listFiles();     //��ǰĿ¼���������ļ�����
    		  for(File file:files){
    			  if(file.isDirectory()){    //��Ŀ¼
    				  m_ScanFileList.push(file);     //ѹ��Ŀ¼ջ��ջ��
    			  }
    			  else{     //����Ŀ¼�����ļ�
    				String p_FilePath = file.toString();
  	                if (p_FilePath.endsWith(".java"))     //��java�ļ�
  	                {
  	                	m_JavaFilesPathList.add(p_FilePath);    //��java�ļ�·����ӵ�·���б�
  	                }
    			  }				  
    		  }
    	  }  	   
       }
}
