package net.oliver.j2se.stream.dataput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


//��ʱû�б�Ҫ�洢�����������Ϣ����ֻ��Ҫ�洢һ������ĳ�Ա���ݣ�
//��Ա���ݵ����ͼ��趼��Java�Ļ����������ͣ����������󲻱�ʹ�õ���Object���롢�����ص�������
//����ʹ��DataInputStream��DataOutputStream��д����������

//�ڴ��ļ���������ʱ�����÷��ĵ������ж϶����ַ���ʱ�����int����ʱ��ʱ��ֹͣ��
//ʹ�ö�Ӧ��readUTF()��readInt()�����Ϳ�����ȷ�ض����������������ݡ�
public class DataStreamDemo {

	public static void main(String[] args) {
		Member[] members = { new Member("Justin", 90), new Member("momor", 95),
				new Member("Bush", 88) };
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("D:\\test.txt"));

			// д��
			for (Member member : members) {
				// д��UTF�ַ���
				dataOutputStream.writeUTF(member.getName());
				// д��int����
				dataOutputStream.writeInt(member.getAge());
			}

			// ����������Ŀ�ĵ�
			dataOutputStream.flush();
			// �ر���
			dataOutputStream.close();

			// ��ȡ
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(args[0]));

			// �������ݲ���ԭΪ����
			for (int i = 0; i < members.length; i++) {
				
				// ����UTF�ַ���
				String name = dataInputStream.readUTF();
				// ����int����
				int score = dataInputStream.readInt();
				
				members[i] = new Member(name, score);
			}

			// �ر���
			dataInputStream.close();

			// ��ʾ��ԭ�������
			for (Member member : members) {
				System.out
						.printf("%s\t%d%n", member.getName(), member.getAge());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
