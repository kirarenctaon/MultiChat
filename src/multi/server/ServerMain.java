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
	Thread thread; //���� ������ ������
	ServerSocket server;
	
	Socket socket;
	
	//��Ƽ�ɽ����� ���ؼ��� ���� ������ ����� ������ ���������� 
	//üũ�� ����Ұ� �ʿ��ϸ�, �����ؾ��ϹǷ� �÷��� �迭�� ��������
	Vector<ServerThread> list = new Vector<ServerThread>();//������ ������ ���� ����
	
	public ServerMain() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port) ,10);
		bt_start = new JButton("����");
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
	
	//��������
	public void startServer(){
		try {
			//��������
			port=Integer.parseInt(t_port.getText());
			server=new ServerSocket(port);
			area.append("�������� \n");
			
			while(true){//������ ������ ���� �������� �����Ͽ� ���������� ������ 
				//���Ӱ���
				socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				area.append(ip+"������ �߰� \n");
				
				
				
				//�����ڸ��� �����带 �ϳ��� �Ҵ��ؼ� ��ȭ�� ������ ���ش�
				ServerThread st= new ServerThread(this);
				st.start();
				
				//�����ڰ� �߰ߵǸ� �� �����ڰ� ��ȭ�� ���� �����带 Vector�� ��´�. 
				list.add(st);
				area.append("���� �����ڴ�"+list.size()+"\n");
				
				if(server.isClosed()){
					area.append("���� �����ڴ�"+list.size()+"\n");
				}
			}
			
    /* 	//��������
			port=Integer.parseInt(t_port.getText());
			server=new ServerSocket(port);
			area.append("�������� \n");
			
			//���Ӱ���
			socket=server.accept();
			String ip=socket.getInetAddress().getHostAddress();
			area.append(ip+"������ �߰� \n");
			
			//��ȭ����
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String msg=null;
			msg=buffr.readLine(); //Ŭ���ξ�Ʈ�� ���� û����
			
			buffw.write(msg+"\n");
			buffw.flush(); //Ŭ���̾�Ʈ�� ���� �ٽ� ������ 
			  
			 �����ڵ忡�� try-catch���� ���������� ä��â���� ���� �ѹ��� �����ϰ� ����
			 ���� while������ �ڵ带 ���ӽ�ų �ʿ䰡 �ִµ�  ���Ӱ� ��ȭ�� �и����� ���� ���
			 �ѻ�� ������ �Ѹ��� -> �ѻ�� ������ �Ѹ��ٸ� �̷������� ���α׷��� ���
			 
			 ���� �� Ŭ���̾�Ʈ���� �������� ��ȭ�� ��� �� �ֵ��� 
			 ��ȭ�����ڵ�� ������ ������ ��ü�� �и��Ͽ����Ѵ�.  */
			
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