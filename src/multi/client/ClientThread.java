//키 입력을 해야 서버의 메세지를 받는 현재의 기능을 보완한다. 
//무한루프를 돌면서 서버의 메세지를 받을 존재가 필요하며 무한루프를 실행햐야하므로 쓰레드로 정의한다. 
package multi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	ClientMain main;//클라이언트 프라임 자체를 보유
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
	
	public void listen(){ //서버가 보낸 메세지를 듣고 area에 뿌림
		String msg=null;
		try {
			msg=buffr.readLine();
			main.area.append(msg+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//구현 목적상 듣기만해도 되지만, 듣기와 말하기가 한 객체에 있으면 편하니까 말하기 까지 구현하도록 하자
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
