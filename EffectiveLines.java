import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * ˼·���ṩһ���������ڣ��û��ɶ�Ρ�������ѡ��Ҫͳ�Ƶ�java�ļ�������������Ч���� 
 * 1.��Ч�������������� 
 * 2.�����Ǵ�����ж���ע�͵����
 */

public class EffectiveLines {
	Frame m_jf = new Frame();
	FileDialog m_OpenDilog = new FileDialog(m_jf, "Ҫͳ�Ƶ�JAVA�ļ�", FileDialog.LOAD);
	Button m_Button = new Button("���ѡ���ļ�");

	/**
	 * ���ڳ�ʼ��
	 */
	public void init() throws IOException {
		m_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_OpenDilog.setVisible(true);    //�����ļ�ѡ��Ի���

				String fileName = m_OpenDilog.getFile();   //��ȡ�ļ���
				String path = m_OpenDilog.getDirectory() + fileName; // ��ȡ�ļ�·��

				if (!path.equals("nullnull") && fileName.endsWith(".java")) // ѡ����һ��JAVA�ļ���δѡ����java��������ͳ��
				{
					System.out.println("�ļ�����" + fileName); // ��ӡ��ǰѡ����ļ���
					int lineNum; // ��Ч����
					try {
						lineNum = CountLines(path); // ͳ��JAVA�ļ���Ч����
						System.out.println("������" + lineNum); // ��ӡ��Ч����
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
			}
		});

		m_jf.add(m_Button);
		m_jf.pack();
		m_jf.setVisible(true);
		m_jf.addWindowListener(new WindowAdapter() // �ر�Frame
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	/**
	 * ͳ��Java�ļ���Ч����
	 * 
	 * @param path
	 *            Java�ļ���·��
	 * @return ��Ч����
	 */
	public int CountLines(String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));// ѡ��·����Ϊ�ļ�������
		int lineNum = 0;
		while (br.ready()) {
			String str = br.readLine().trim(); // ��ȡһ���ַ�

			if (str.isEmpty() || str.startsWith("//")
					|| (str.startsWith("/*") && str.endsWith("*/")))
				continue;

			++lineNum; // ��Ч������1
		}
		br.close(); // �ر�
		return lineNum;
	}

	/**
	 * ������
	 */
	public static void main(String[] args) throws IOException {
		new EffectiveLines().init();
	}
}
