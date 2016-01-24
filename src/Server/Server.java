package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;

import GameLogic.Table;

public class Server extends Thread {

	private static ServerSocket serverSocket = null;
	private static Socket client = null;
	private static int maxClientsCount = 1;
	static int small_blind;
	static int big_blind;
	static int max_bet;
	public static Table table;
	public static int bill;
	static String response;
	private int count = 0;
	Wymiana threads[];
	private PrintStream os;
	private BufferedReader in;

	Server(int maxClientsCount, int bill, int small_blind, int big_blind) {
		this.maxClientsCount = maxClientsCount;
		this.bill = bill;
		this.small_blind = small_blind;
		this.big_blind = big_blind;
		this.max_bet = 0;
		table=new Table(maxClientsCount, bill, small_blind, big_blind);
		start();

		threads = new Wymiana[maxClientsCount];
	}

	public  synchronized void run()
    {
		Object[] InputData = new Object[7];
		int[] OutputData = new int[7];
		String data="";
		 ServerSocket server=null;
      try{
    	  server= new ServerSocket(4444);
          System.out.println("Czekamy na połączenie...");
      } catch (IOException e) {
          e.printStackTrace();
         }

      int i = 0;
          while (true) {
        	  synchronized(this){
  			try {

  				 Socket client = server.accept();
  				 i = 0;
  				for (i = 0; i < maxClientsCount; i++) {
  					if (threads[i] == null) {
  						threads[i] = new Wymiana(client,table,threads);						
  						threads[i].start();
  						 os = new PrintStream(client.getOutputStream());
  						 os.println(i);
  						os.println("|Zostałeś podłączony do gry poczekaj na resztę graczy.|Twoje id to "+i);
  						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
  						break;
  					}
  				}
  				System.out.println("Client został podłączony ");
  				
  				if (i >= maxClientsCount) {
  					PrintStream os = new PrintStream(
  							client.getOutputStream());
  					os.println("|Zbyt wielu graczy. Wyłącz program i spróbuj później.");
  					os.close();
  					client.close();
  				}
  				if(i==maxClientsCount-1){
  					
					for(int k=0;k<threads.length;k++){
						threads[k].out.println("|Wszyscy gracze dołączyli do gry. Rozpoczynamy rozgrywkę!!!|");						
						response=table.getResponse();
						int[] hand=table.getHand(k);
						threads[k].out.println("setBill"+table.getCash(k));
						threads[k].out.println("setPot"+table.getPot());
						threads[k].out.println("setMaxBet"+table.getMaxBet());
						threads[k].out.println("setCards"+hand[0]);
						threads[k].out.println("setCards"+hand[1]);
						threads[k].out.println(response);
						
					}
				
  			
  			}
  				
  				
  				
  				
  				
  				
  				
  				
  			} catch (IOException e) {
  				System.out.println(e);
  			}
  			
           
  		}
          }
    	}
}
class Wymiana extends Thread {
	Socket client;
	PrintWriter out;
	BufferedReader in;
	private int[] OutputData=new int[7];
	Table table;
	Wymiana threads[];

	public Wymiana(Socket client,Table t,Wymiana thread[]) {
		this.client = client;
		try {
			this.out = new PrintWriter(this.client.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(
					this.client.getInputStream()));
			this.table=t;
			this.threads=thread;
		} catch (IOException e) {
		}
	}

	public void run() {
		try {

			while (true) {
				
				String data="";
				//obsługa odboru sygnałów od klienta
  				data = in.readLine();
  				int Id=Integer.parseInt(data);
  				data=in.readLine();
  				
				if (data != null) {
					System.out.println("data:"+data+" "+Id);

					String message = "";
					if (table.getCurrent() == Id
							&& table.checkActive(Id)) {
						OutputData[0]=Id;

						switch (data.substring(0, 2)) {
						case "ch": {
							OutputData[1] = 1;
							OutputData[2] = 1;
							if (table.getMyBet(Id) != table.getMaxBet()) {
								OutputData[3] = -2;
							}
							break;
						}
						case "be": {
							OutputData[1] = 1;
							OutputData[2] = 2;
							OutputData[3] = Integer.parseInt(data.substring(2));
							if (OutputData[3] > table.getCash(Id)|| table.getMaxBet() != 0) {
								OutputData[3] = -2;
							}
							break;
						}
						case "ra": {
							OutputData[1] = 1;
							OutputData[2] = 2;
							OutputData[3] = Integer.parseInt(data.substring(2));
							if (OutputData[3] < table.getMaxBet()
									- table.getMyBet(Id)
									|| OutputData[3] > table.getCash(Id)
									|| table.getMaxBet() == 0) {
								OutputData[3] = -2;
							}
							break;
						}
						case "ca": {
							OutputData[1] = 1;
							OutputData[2] = 2;
							OutputData[3] = Integer.parseInt(data.substring(2));
							if (OutputData[3] != table.getMaxBet()
									- table.getMyBet(Id)
									|| OutputData[3] > table.getCash(Id)) {
								OutputData[3] = -2;
							}
							break;
						}
						case "fo": {
							OutputData[1] = 1;
							OutputData[2] = 3;
							break;
						}
						case "al": {
							OutputData[1] = 1;
							OutputData[2] = 2;
							OutputData[3] = Integer.parseInt(data.substring(2));
							if (OutputData[3] != table.getCash(Id)) {
								OutputData[3] = -2;
							}
							break;
						}
						
						}

						if (OutputData[3] >= -1) {
							Integer[] Output = new Integer[7];
							for (int l = 0; l < 7; l++) {

								Output[l] = Integer.valueOf(OutputData[l]);
							}

							 table.listen(Output);

							
							message = table.getResponse();
						}
						System.out.println(OutputData);
						for (int k = 0; k < OutputData.length; k++) {
							System.out.println(OutputData[k]);
						}
						
						
					
					} else {
						if (table.checkActive(Id)) {
							out.println("Poczekaj na swoją kolej|");
						}
					}
					synchronized (this) {
						

						for (int z = 0; z < threads.length; z++) {
							threads[Id].out.println("setBill" + table.getCash(Id)+"");
							threads[z].out.println("setPot" + table.getPot()+"");
							threads[z].out.println("setMaxBet"+ table.getMaxBet()+"");
							
							
							for (int k = 0; k < threads.length; k++)
								threads[z].out.println("setBetsAmount"
										+ "Gracz " + k + " "
										+ table.getMyBet(k) + "  "+"");
							threads[z].out.println("setNewRound"+table.round+"");

							message.trim();
							if(message.length()>4)
							while((message.substring(0,4)).equals(("setc"))){
								System.out.println(message);
								message=message.substring(4);
								String temp[]=message.split("setc");
								for(int b=0;b<threads.length;b++){
								for(int a=0; a<temp.length-1;a++){
									threads[b].out.println("setCommon"+temp[a]);
								}
								}
								message=temp[temp.length-1];
								
						}
							
							threads[z].out.println(message);
						}
					}

					for (int k = 1; k < 7; k++) {
						OutputData[k] = 0;
					}
					data = "";

				} else {

					break;
				}

			}

		} catch (IOException e) {
		}
	}
}