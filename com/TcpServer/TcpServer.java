package com.TcpServer;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import com.TcpServer.TcpServerThread;

public class TcpServer {

	//监听端口
	public static final int PORT_NUMBER=3247;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		System.out.println("-------------服务器准备就绪--------------\n");
		ServerSocket sktServer = null;
		Socket sktclient = null;
		File f =new File("run.lock");
		String spath =f.getPath();
		RandomAccessFile fis =new RandomAccessFile(spath, "rw");
		FileChannel lockfc =fis.getChannel();
		FileLock flock =lockfc.tryLock();
			try {
				sktServer =new ServerSocket(PORT_NUMBER);
				while(true)
				{
					sktclient =sktServer.accept();
					TcpServerThread tst =new TcpServerThread(sktclient.toString(),sktclient,sktServer);
					tst.start();
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("监听错误:"+e);
				
			}
			finally
			{
				lockfc.close();
			}
		
	}

}
