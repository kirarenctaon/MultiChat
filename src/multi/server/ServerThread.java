//접속한 클라이언트와 1:1로 대화를 나눌 쓰레드
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
	
	//서버로부터 소켓을 받아오자
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
	
	//클라이언트의 메세지 받기
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			main.area.append(msg+"\n");
			send(msg);//들은 말을 다시 클라이언트에게 보내기
		} catch (IOException e) {
			flag=false;//run을 돌리는 쓰레드를 죽임
			
			//벡터에서 이 쓰레드를 제거
			main.list.remove(this);
			
			main.area.append("1명퇴장후 현재 접속자"+main.list.size()+"\n");
			System.out.println("읽기불가");
			//e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try {
			/* buffw.write(msg+"\n");
				buffw.flush();
			이렇게 하면 한명하고만 대화가 형성되니까 현재 접속한 자에게 반복문으로 메세지를 보내자 
			총 접속자 수를 알수 없으므로 컬렉션 계열로 가되, 
			ArrayList는 동시성문제를 보장해주지 못하므로 Vector로 하자  */

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
