package com.TcpServer;
import java.io.*;
import java.net.*;
//线程
public class TcpServerThread extends Thread{
	 
	    private Socket socket;  
	    private DataInputStream in;  
	    private PrintWriter out;  
	    private int PORT_NUMBER=3247;
	    private ServerSocket sktserver;
	    private DataOutputStream dos;
	    private DataInputStream dis;
	public TcpServerThread(String s,Socket sktclient,ServerSocket sktServer)
	{
		super(s);
		this.socket=sktclient;
		this.sktserver=sktServer;
	
	}
	 public  byte[] hexStringToByte(String hex) {
	        
	        int nsLength =hex.length();
	        byte[] result = new byte[nsLength/2];
	        if(0!=nsLength%2) return null;
	        int c =0;
	        for(int i=0;i<nsLength;i+=2)
	        {
	        	String stmp=hex.substring(i,i+2);
	        	stmp.trim();
	        	result[c++]=(byte)Integer.parseInt(stmp, 16);
	        }
	        //result[c++]='\n';
	        return result;
	    }
	@Override
	public void run() {
		// TODO Auto-generated method stub	
		try   
        {  
           
			System.out.println("The server is waiting your connect...");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			in  =new DataInputStream(System.in);
				/*
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader sendbuff = new BufferedReader(
						new InputStreamReader(System.in));
				*/
			socket.setSoTimeout(30000);
			while (true) {
				
				/*
				int len = dis.available();
				int nflag = 0;
				while (len <= 0) {
					len = dis.available();
					System.out.println("无发送的信息");
					Thread.sleep(2000);
					//if(nflag++==10)break;
				}
				if(nflag==10)break;
				*/
				byte[] recv = new byte[256];
				try {
					dis.read(recv);
					int len =sizeof(recv);
					if(len<=0)continue;
					String stmp = "";
					for (int i = 0; i < len; i++) {
						stmp += Integer.toHexString(recv[i]) + " ";
					}
					System.out.println(getName() + "recv message is : " + stmp);
					String s = "fefefefe6812042608112068910833333433333333337716";
					System.out.println("输入发送的信息" + s);
					// s=in.readLine();
					byte[] sendbyte;
					sendbyte=null;
					sendbyte = hexStringToByte(s);
					dos.write(sendbyte,0,sendbyte.length);
					stmp = "";
					for (int i = 0; i < sendbyte.length; i++) {
						stmp += sendbyte[i] + " ";
					}
					System.out.println("server-send message is : " + stmp);
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
					//dis.close();dos.close();socket.close();
					break;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					//dis.close();dos.close();socket.close();
					break;
				}
			}
			//sktserver.close();
              
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
	private int sizeof(byte[] recv) {
		// TODO Auto-generated method stub
		int nlength=0;
		if(recv[0]==0x00)return nlength;
		for(int i=0;i<recv.length;i++)
		{
			nlength=i;
			if(recv[i]==0x16)
				break;
		}
		
		return nlength+1;
	}
	public String ByteConversionString(byte []pchBuffer, int nLength)
    {
    	String m_strSwapBuff = "";
    	String m_strTempBuff="";	
 
    	for(int i=0; i<nLength; i++)
    	{
    		m_strTempBuff="";
    		m_strTempBuff=String.format("%lx", (pchBuffer[i] & 0xF0) >> 4);
    		m_strSwapBuff += m_strTempBuff;
    		//
    		m_strTempBuff="";
    		m_strTempBuff=String.format("%lx", (pchBuffer[i] & 0x0F));
    		m_strSwapBuff += m_strTempBuff;
    	}	
    	return m_strSwapBuff;
    }
	
}
