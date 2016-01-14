package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;

import GameLogic.Table;

public class Server {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static int maxClientsCount = 1;
	public static Table table;
	public static int bill;
	static String response;
	public static void main(String args[]) {
		Client[] threads = null;

		try {
			int portNumber = Integer.valueOf(args[0]).intValue();
			if (Integer.parseInt(args[1]) <= 10
					&& Integer.parseInt(args[1]) >= 2) {
				maxClientsCount = Integer.parseInt(args[1]);
				table=new Table(maxClientsCount,Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
				response=table.getResponse();
				System.out.println("Maksymalna ilość klientów: "
						+ maxClientsCount + "\nport:" + portNumber);
				bill=Integer.parseInt(args[3]);
			} else {
				System.out
						.println("Podano nieprawidłowe ustawienia początkowe");
				System.exit(0);
			}
			threads = new Client[maxClientsCount];
			serverSocket = new ServerSocket(portNumber);
		}  catch (Exception z) {
			System.out.println("Nieprawidlowe parametry uruchomieniowe gry");
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();

				int i = 0;
				for (i = 0; i < maxClientsCount; i++) {
					if (threads[i] == null) {
						threads[i] = new Client(clientSocket, threads,table.getHand(i), table,  bill);						
						threads[i].start();
						PrintStream os = new PrintStream(
								clientSocket.getOutputStream());
						os.println(response);
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(
							clientSocket.getOutputStream());
					os.println("Zbyt wielu graczy");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
