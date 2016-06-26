import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//ͳ��һ��Java�ļ�����Ч������
//��Ч����������
//�����Ǵ�����ж���ע�͵����, Ĭ��UTF-8����

public class EffectiveLines {
public static int countEffectiveLines(String filepath){
	File infile = new File(filepath);
	FileInputStream instream = null;
	InputStreamReader instreamreader = null;
	BufferedReader br = null;
	String str = null;
	int count = 0;
	try {
		instream = new FileInputStream(infile);
	} catch (FileNotFoundException e) {
		System.out.println("�ļ�"+filepath+"������");
		e.printStackTrace();
	}
	try{
		instreamreader = new InputStreamReader(instream,"UTF-8");
		br = new BufferedReader(instreamreader);
		while((str = br.readLine()) != null){
			str.trim();
			if(!str.isEmpty() && !str.startsWith("//"))
				count++;
			}
		}catch(IOException e){
			System.out.println("��ȡʱ����I/O����");
			e.printStackTrace();
		}finally{
				try {
					br.close();
					instreamreader.close();
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	return count;
	}

	public static void main(String args[]){
		Scanner s = new Scanner(System.in);
		String filepath = null;
		System.out.println("�������ļ��ľ���·��:");
		filepath = s.next();
		s.close();
		int n = countEffectiveLines(filepath);
		System.out.println("����" + n + "����Ч�Ĵ���");
	}
}
