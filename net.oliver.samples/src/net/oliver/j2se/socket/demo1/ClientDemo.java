package net.oliver.j2se.socket.demo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientDemo {

	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			int count = 0;

			while ((tempbyte = in.read()) != -1) {

				System.out.write(tempbyte);
				count++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket socket = null;

		try {
			socket = new Socket("10.220.129.50", 9007);

//			String content2 = "00000525{H        01   ABS                          UPBSUPCHPX              102071            201206011             000001100000000<?xml version=\"1.0\" encoding=\"GB18030\"?>"+"\n"
//+"<Document><ReqHdr><channelcode>001</channelcode><channeldate>20120601</channeldate><channeltime>175742</channeltime><channelserno>0000011</channelserno><servicelanguage>ZH-CN</servicelanguage><brno>904040000</brno><tellerno>Z001</tellerno><terminalno>WSH10</terminalno></ReqHdr><Content><nowpagenum>1</nowpagenum><pagerownum>15</pagerownum></Content></Document>";
			
//			System.out.println(content2.getBytes().length);
//			System.out.println(content2);
			String content = "00000569{H:01       ABS      UPBSUPCHPX                        10213220120601134910             0000011                    311                    }"
					+ "\r\n"
					+ "00000000<?xml version=\"1.0\" encoding=\"GB18030\"?>"
					+ "\n"
					+ "<Document><ReqHdr><channelcode>001</channelcode><channeldate>20120601</channeldate><channeltime>134910</channeltime><channelserno>0000011</channelserno><servicelanguage>ZH-CN</servicelanguage><brno>904040000</brno><tellerno>Z001</tellerno><terminalno>WSH10</terminalno></ReqHdr><Content><status>1</status><nowpagenum>1</nowpagenum><pagerownum>15</pagerownum></Content></Document>";

			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			out.write(content.getBytes());
			out.flush();
			
			// 读取8位长度
			byte[] lengthbuffer = new byte[8];
			in.read(lengthbuffer);
			String a = new String(lengthbuffer);
			int datalength = Integer.parseInt(a);
			
			// 建立内容长度的数组
			byte[] responsebuffer = new byte[datalength];
			in.read(responsebuffer);
			
			
			System.out.println(new String(responsebuffer));
			
			
			// 关闭
			socket.close();
			//            
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
