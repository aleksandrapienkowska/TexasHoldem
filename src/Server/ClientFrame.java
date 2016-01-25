package Server;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import GameLogic.Table;
import GameLogic.Cards;

public class ClientFrame {
	private static Socket clientSocket = null;
	private static Socket socket = null;
	public static PrintWriter out = null;
	public static BufferedReader in = null;
	public String Status;
	public boolean smallblind = false;
	public boolean bigblind = false;
	public boolean dealerbuton = false;
	public boolean admin = false;
	int[] cards = new int[2];
	int[] common = new int[5];
	static int Id;
	private Table table;
	public static JLabel[] CardImages = new JLabel[52];
	public static String[] Card = new String[52];
	static JPanel PanelInfo = new JPanel();
	static JLabel Balance = new JLabel("Stan konta: ");
	static JLabel BalanceAmount = new JLabel();
	static JLabel RoundOfBetting = new JLabel("Runda licytacji: ");
	static JLabel RoundOfBettingAmount = new JLabel();
	static JLabel TotalAmount = new JLabel();
	static JLabel Total = new JLabel("Stawka w grze: ");
	static JLabel MaxBet = new JLabel("Maksymalny Bet");
	static JLabel MaxBetAmount = new JLabel();
	static JLabel Bets = new JLabel("Zakłady graczy");
	static JLabel BetsAmount = new JLabel();
	static JPanel PanelAuction = new JPanel();
	static JButton Check = new JButton("Check");
	static JButton Bet = new JButton("Bet");
	static JButton Raise = new JButton("Raise");
	static JButton Call = new JButton("Call");
	static JButton Fold = new JButton("Fold");
	static JButton AllIn = new JButton("All in");
	static JLabel Amount = new JLabel("Kwota: ");
	static JTextField Payment = new JTextField();
	static JPanel PanelCards = new JPanel();
	static JLabel[] card = new JLabel[2];
	static JLabel[] commoncard = new JLabel[5];
	static JPanel PanelMessage = new JPanel();
	static JTextArea message = new JTextArea(10, 70);
	static JFrame window = new JFrame("Poker Texas Holdem");
	private static int bill;
	private static int maxbet;
	

	public ClientFrame() {

		// makeCardsImages();
		window.setLayout(new GridLayout(4, 1));
		PanelInfo.setLayout(new GridLayout(5, 2));
		PanelInfo.add(Balance);
		PanelInfo.add(BalanceAmount);
		PanelInfo.add(RoundOfBetting);
		PanelInfo.add(RoundOfBettingAmount);
		PanelInfo.add(Total);
		PanelInfo.add(TotalAmount);
		PanelInfo.add(MaxBet);
		PanelInfo.add(MaxBetAmount);
		PanelInfo.add(Bet);
		PanelInfo.add(BetsAmount);
		PanelAuction.setLayout(new FlowLayout());
		PanelAuction.add(Check);
		PanelAuction.add(Bet);
		PanelAuction.add(Raise);
		PanelAuction.add(Call);
		PanelAuction.add(Fold);
		PanelAuction.add(AllIn);
		PanelAuction.add(Amount);
		Payment.setPreferredSize(new Dimension(70, 20));
		PanelAuction.add(Payment);

		PanelCards.setLayout(new GridLayout(4, 5));
		PanelCards.add(new JLabel("Karty wspólne"));
		for (int k = 0; k < 5; k++) {
			commoncard[k] = new JLabel();
			PanelCards.add(commoncard[k]);
		}
		PanelCards.add(new JLabel("Twoje karty"));
		for (int k = 0; k < 2; k++) {
			card[k] = new JLabel();
			PanelCards.add(card[k]);
		}
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setEditable(false);

		PanelMessage.add(message);
		JScrollPane obszar_przewijany1 = new JScrollPane(message,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		PanelMessage.add(obszar_przewijany1);

		window.add(PanelInfo);
		window.add(PanelAuction);
		window.add(PanelCards);
		window.add(PanelMessage);
		window.setSize(800, 650);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		Check.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				out.println(Id);
				out.println("ch");
				Payment.setText("");

			}

		});
		Bet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int k = -1;
				out.println(Id);
				
				try {
					k = Integer.parseInt(Payment.getText());
					out.println("be" + Payment.getText());
					
				} catch (Exception z) {
					message.append("Nieprawidlowa kwota");

				}
				Payment.setText("");
				

			}

		});
		Raise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int k = -1;
				out.println(Id);

				try {

					k = Integer.parseInt(Payment.getText());
					
						out.println("ra" + Payment.getText());
					
				} catch (Exception z) {
					message.append("Nieprawidlowa kwota");
				}

				Payment.setText("");

			}

		});
		Call.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int k = -1;
				out.println(Id);

				try {

					k = Integer.parseInt(Payment.getText());
					
						out.println("ca" + Payment.getText());
				} catch (Exception z) {
					message.append("Nieprawidlowa kwota");
				}

				Payment.setText("");

			}

		});
		Fold.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				out.println(Id);
				out.println("fo");
				Payment.setText("");

			}

		});
		AllIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int k = -1;
				out.println(Id);

				try {

					k = Integer.parseInt(Payment.getText());
					
						out.println("al" + Payment.getText());

					
				} catch (Exception z) {
					message.append("Nieprawidlowa kwota");
				}

				Payment.setText("");

			}

		});

	}

	public static void main(String[] args) {
		new ClientFrame();

		try {
			socket = new Socket("localhost", 4444);

			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (Exception e) {
			String mcc = "", bill = "", sb = "", bb = "";
			while (mcc.length() < 1 || bill.length() < 1 || sb.length() < 1
					|| bb.length() < 1) {
				mcc = JOptionPane.showInputDialog("Podaj ilość graczy");
				bill = JOptionPane.showInputDialog("Podaj wysokość rachunku");
				sb = JOptionPane.showInputDialog("Podaj małą czarną");
				bb = JOptionPane.showInputDialog("Podaj dużą czarną");

			}

			Server s = new Server(Integer.parseInt(mcc),
					Integer.parseInt(bill), Integer.parseInt(sb),
					Integer.parseInt(bb));

			while (socket == null) {
				try {

					socket = new Socket("localhost", 4444);
					out = new PrintWriter(socket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));

				} catch (Exception e1) {
				}
			}

		}
		String text = "";
		try {
			Id = Integer.parseInt(in.readLine());

		} catch (NumberFormatException e1) {
			message.append("Nieprawodłowe Id klienta");
		} catch (IOException e1) {
			message.append("Utracono połączenie z serverem");
		}
		while (text != null) {

			try {
				text = in.readLine();
				int l = 0;
				int k = 0;
				if(text.startsWith("setBill")) {
					BalanceAmount.setText(text.replace("setBill", ""));
					text = in.readLine();
					bill = Integer.parseInt(BalanceAmount.getText());

				}

				if(text.startsWith("setPot")) {
					TotalAmount.setText(text.replace("setPot", ""));
					text = in.readLine();

				}
				if(text.startsWith("setMaxBet")) {
					MaxBetAmount.setText(text.replace("setMaxBet", ""));
					maxbet = Integer.parseInt(MaxBetAmount.getText());
					text = in.readLine();

				}
				if(text.startsWith("setTitle")) {
					window.setTitle("Badugi" + text.replace("setTitle", ""));
					text = in.readLine();

				}
				while(text.startsWith("setBetsAmount")) {
					l++;
					if (l == 1)
						BetsAmount.setText("");
					BetsAmount.setText(BetsAmount.getText()
							+ text.replace("setBetsAmount", ""));
					text = in.readLine();

					if (l == 4) {
						l = 0;
					}
				}
				if(text.startsWith("setNewRound")) {						
					RoundOfBettingAmount.setText(text
							.replace("setNewRound", ""));
					text = in.readLine();

				}
				while (text.startsWith("setCards")) {

					Cards temp = Cards.values()[Integer.parseInt(text.replace(
							"setCards", ""))];
					card[l].setText(temp.toString());
					l++;
					if (l == 2) {
						l = 0;
					}
					text = in.readLine();
				}
				while (text.startsWith("setCommon")) {
					while(!commoncard[k].getText().equals("")){
						k++;
						if (k == 5)
							k = 0;
					}
					commoncard[k].setText(text.replace("setCommon", ""));
					k++;
					if (k == 5)
						k = 0;
					text = in.readLine();

				}
				

				broadcast(text.replace("|", "\n"));

				window.revalidate();
				window.repaint();
			} catch (IOException e) {
				message.append("Utracono połączenie z serverem");
			} catch (NullPointerException e) {
				message.append("Utracono połączenie z serverem");
			}
		}

	}

	static void broadcast(String response) {
		message.append(response);
		window.repaint();
		window.revalidate();
	}

}