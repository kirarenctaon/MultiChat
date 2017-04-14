//������ Ŭ���̾�Ʈ�� 1:1�� ��ȭ�� ���� ������
package multi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	ServerMain main;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag=true;
	
	//�����κ��� ������ �޾ƿ���
	public ServerThread(ServerMain main) {
		this.main=main;
		Socket socket;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(main.socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(main.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Ŭ���̾�Ʈ�� �޼��� �ޱ�
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			main.area.append(msg+"\n");
			send(msg);//���� ���� �ٽ� Ŭ���̾�Ʈ���� ������
		} catch (IOException e) {
			flag=false;//run�� ������ �����带 ����
			
			//���Ϳ��� �� �����带 ����
			main.list.remove(this);
			
			main.area.append("1�������� ���� ������"+main.list.size()+"\n");
			System.out.println("�б�Ұ�");
			//e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try {
			/* buffw.write(msg+"\n");
				buffw.flush();
			�̷��� �ϸ� �Ѹ��ϰ� ��ȭ�� �����Ǵϱ� ���� ������ �ڿ��� �ݺ������� �޼����� ������ 
			�� ������ ���� �˼� �����Ƿ� �÷��� �迭�� ����, 
			ArrayList�� ���ü������� ���������� ���ϹǷ� Vector�� ����  */

			for(int i=0;i<main.list.size();i++){
			 	ServerThread st= main.list.elementAt(i);
				st.buffw.write(msg+"\n");
				st.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(flag){
			listen();
		}
	}
}
