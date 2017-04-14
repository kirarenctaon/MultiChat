package multi.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements ActionListener, Runnable{
	JPanel p_north; 
	JTextField t_port;
	JButton bt_start;
	JTextArea area;
	JScrollPane scroll;
	int port=7777;
	Thread thread; //서버 가동용 쓰레드
	ServerSocket server;
	
	Socket socket;
	
	//멀티케스팅을 위해서는 현재 서버에 몇명이 들어오고 나가는지를 
	//체크할 저장소가 필요하며, 유연해야하므로 컬렉션 계열로 선언하자
	Vector<ServerThread> list = new Vector<ServerThread>();//접속자 정보를 담을 벡터
	
	public ServerMain() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port) ,10);
		bt_start = new JButton("가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		bt_start.addActionListener(this);
		
		setBounds(600,100,300,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//서버가동
	public void startServer(){
		try {
			//서버생성
			port=Integer.parseInt(t_port.getText());
			server=new ServerSocket(port);
			area.append("서버생성 \n");
			
			while(true){//앞으로 서버는 접속 감지에만 집중하여 지속적으로 감시함 
				//접속감지
				socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				area.append(ip+"접속자 발견 \n");
				
				
				
				//접속자마다 쓰레드를 하나씩 할당해서 대화를 나누게 해준다
				ServerThread st= new ServerThread(this);
				st.start();
				
				//접속자가 발견되면 이 접속자가 대화를 나눌 쓰레드를 Vector에 담는다. 
				list.add(st);
				area.append("현재 접속자는"+list.size()+"\n");
				
				if(server.isClosed()){
					area.append("현재 접속자는"+list.size()+"\n");
				}
			}
			
    /* 	//서버생성
			port=Integer.parseInt(t_port.getText());
			server=new ServerSocket(port);
			area.append("서버생성 \n");
			
			//접속감지
			socket=server.accept();
			String ip=socket.getInetAddress().getHostAddress();
			area.append(ip+"접속자 발견 \n");
			
			//대화감지
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String msg=null;
			msg=buffr.readLine(); //클라인언트의 말을 청취함
			
			buffw.write(msg+"\n");
			buffw.flush(); //클라이언트의 말을 다시 전달함 
			  
			 현재코드에서 try-catch문이 끝나버리면 채팅창에서 말을 한번만 전달하고 끝남
			 따라서 while문으로 코드를 지속시킬 필요가 있는데  접속과 대화를 분리하지 않을 경우
			 한사람 감지후 한마디만 -> 한사람 감지후 한마다만 이런식으로 프로그램이 운영됨
			 
			 따라서 각 클라이언트마다 독립적인 대화를 운영할 수 있도록 
			 대화감지코드는 별도의 쓰레드 객체로 분리하여야한다.  */
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		startServer();
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}

}