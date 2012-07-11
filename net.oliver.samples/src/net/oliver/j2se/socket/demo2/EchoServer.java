package net.oliver.j2se.socket.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private int port = 8000;
	private ServerSocket serverSocket;

	public EchoServer() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server Start");
	}

	public String echo(String msg) {
		return "echo:" + msg;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept(); // 等待客户连接
				System.out.println("New connection accepted "	+ socket.getInetAddress() + ":" + socket.getPort());
				// Reader
				BufferedReader br = getReader(socket);
				// Writer
				PrintWriter pw = getWriter(socket);
				String msg = null;
				while ((msg = br.readLine()) != null) {
					System.out.println(msg);
					// 返回给客户端
					pw.println(echo(msg));
					if (msg.equals("bye")) // 如果客户发送的消息为“bye”，就结束通信
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null)
						socket.close(); // 断开连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {
		new EchoServer().service();
	}
}
