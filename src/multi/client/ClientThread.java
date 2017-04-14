//Ű �Է��� �ؾ� ������ �޼����� �޴� ������ ����� �����Ѵ�. 
//���ѷ����� ���鼭 ������ �޼����� ���� ���簡 �ʿ��ϸ� ���ѷ����� ��������ϹǷ� ������� �����Ѵ�. 
package multi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	ClientMain main;//Ŭ���̾�Ʈ ������ ��ü�� ����
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ClientThread(ClientMain main) {
		this.main=main;
		Socket socket;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(main.socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(main.socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen(){ //������ ���� �޼����� ��� area�� �Ѹ�
		String msg=null;
		try {
			msg=buffr.readLine();
			main.area.append(msg+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���� ������ ��⸸�ص� ������, ���� ���ϱⰡ �� ��ü�� ������ ���ϴϱ� ���ϱ� ���� �����ϵ��� ����
	public void send(String msg){
		try {
			buffw.write(main.nickName+" : "+msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			listen();
		}
	}
}
