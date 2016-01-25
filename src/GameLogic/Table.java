package GameLogic;

import java.util.Random;

public class Table {
	Player[] gracze;
	Cards[] common = new Cards[5];
	Deck talia;
	Strategy krupier;
	Random los;
	int playerscount, i, temp, foo, bar, small_blind, big_blind, initial_cash,
			best_result, dealer;
	public int active_players;
	public int round;
	int max_bet;
	int pot = 0;
	int[] ttab;
	public int current;
	String response;
	private boolean alive;
	private boolean new_game;

	public Table(int maxClientsCount, int cash, int small_blind, int big_blind) {
		gracze = new Player[maxClientsCount];
		this.small_blind = small_blind;
		this.big_blind = big_blind;
		this.initial_cash = cash;
		this.max_bet = 0;
		talia = new Deck();
		los = new Random();
		response = "";
		round = 1;
		pot = 0;
		active_players = maxClientsCount;
		dealer = los.nextInt() % maxClientsCount;
		for (int x = 0; x < gracze.length; x++) {
			gracze[x] = new Player(x);
			gracze[x].setCash(initial_cash);
			gracze[x].setHand(startingHand());
			gracze[x].setBet(0);
		}
		response += ("|Rozpoczyna sie nowa partia...|");
		betBlind(dealer + 1, small_blind);
		betBlind(temp + 1, big_blind);
		current = nextPlayer(temp + 1);
		response += ("Akcja gracza " + current + "|");

	}

	// Obsługa graczy
	private void betBlind(int who, int what) {
		if (active_players == 1 && what == big_blind) {
			endgame();
			return;
		}
		if (active_players == 0 && what == small_blind) {
			alive = false;
			return;
		}
		if (who == gracze.length) {
			who = 0;
		}
		if (!gracze[who].checkActive()) {
			who = nextPlayer(who + 1);
			betBlind(who, what);
			return;
		}
		if (gracze[who].getCash() < what) {
			gracze[who].lost = true;
			gracze[who].active = false;
			for (int i = 0; i < 2; i++) {
				talia.dumpCard(gracze[who].hand[i]);
			}
			active_players--;
			who = nextPlayer(who + 1);
			betBlind(who, what);
		} else {
			gracze[who].bet(what);
			pot = pot + what;
			if (what == big_blind) {
				max_bet = big_blind;
				response += ("Gracz " + who + " stawia duza ciemna|");
				gracze[who].big_blind = true;
			} else if (what == small_blind) {
				response += ("Gracz " + who + " stawia mala ciemna|");
				gracze[who].small_blind = true;
			}
			temp = who;
		}
	}

	private void bet(int who, int amount) {
		if (gracze[who].getCash() < amount) {
			return;
		} else {
			if (gracze[who].getCash() == amount) {
				gracze[who].all_in = true;
				gracze[who].active = false;

			}

			gracze[who].bet(amount);
			pot = pot + amount;

			response += ("Gracz " + who + " stawia " + amount + ", lacznie: "
					+ gracze[who].bet + "|");
			if (gracze[who].round_bet > max_bet) {
				max_bet = gracze[who].round_bet;
				for (Player p : gracze) {
					p.leader = false;
				}
				gracze[who].leader = true;
			}
		}
	}

	private void fold(int who) {
		response += ("Gracz " + who + " folduje|");
		gracze[who].fold = true;
		gracze[who].active = false;
		active_players--;
	}

	private void check(int who) {
		if (round != 1)
			response += ("Gracz " + who + " checkuje|");

	}

	// Obsługa gry
	private int nextPlayer(int try_id) {
		if (active_players == 1) {
			return try_id;
		}
		if (try_id >= gracze.length) {
			try_id = 0;
		}
		if (!gracze[try_id].checkActive()) {
			try_id = nextPlayer(1 + try_id);
		}
		return try_id;
	}

	private boolean checkIfEnd() {
		boolean ret = false;
		temp = current; // (gracze[temp].big_blind==false || round>1))
		if (active_players > 1) {
			for (int i = 0; i < gracze.length; i++) {

				if (gracze[temp].round_bet == gracze[nextPlayer(temp + 1)].round_bet) {
					ret = true;
					temp = nextPlayer(temp + 1);
				} else
					return false;
			}
		} else {
			return true;
		}
		return ret;
	}

	private void newRound() {
		round++;
		if (round == 2) {
			common[0] = Cards.values()[talia.takeCard()];
			response = "setc" + common[0].toString();
			common[1] = Cards.values()[talia.takeCard()];
			response += "setc" + common[1].toString();
			common[2] = Cards.values()[talia.takeCard()];
			response += "setc" + common[2].toString() + "setc";
			current = small_blind;
			nextPlayer(dealer + 1);
		} else if (round == 3) {
			common[3] = Cards.values()[talia.takeCard()];
			response = "setc" + common[3].toString() + "setc";
			current = small_blind;
			nextPlayer(dealer + 1);
		} else if (round == 4) {
			common[4] = Cards.values()[talia.takeCard()];
			response = "setc" + common[4].toString() + "setc";
			current = small_blind;
			nextPlayer(dealer + 1);
		} else if (round == 5) {
			response += ("Minela tura 4|");
			endgame();
			return;
		}

		response += ("|Rozpoczyna sie " + round + " tura...|");
		max_bet = 0;
		for (Player p : gracze) {
			p.leader = false;
			p.round_bet = 0;
		}
		for (int i = 0; i < gracze.length; i++) {
			if (gracze[i].big_blind == true) {
				current = nextPlayer(i + 1);
				gracze[current].leader = true;
				response += ("Akcja gracza " + current + "|");
			}
		}
	}

	private void newGame() {
		new_game = true;
		active_players = 0;
		for (int i = 0; i < gracze.length; i++) {
			if (gracze[i].cash == 0) {
				response += ("Gracz " + i + " przegrywa");
				gracze[i].lost = true;
			}
			if (gracze[i].lost == false) {
				active_players++;
				gracze[i].active = true;
				gracze[i].setHand(startingHand());
			}
			gracze[i].fold = false;
			gracze[i].all_in = false;
			gracze[i].small_blind = false;
			gracze[i].big_blind = false;
			gracze[i].bet = 0;
			gracze[i].round_bet = 0;

		}
		response += ("|Rozpoczyna sie nowa partia...|");
		max_bet = 0;
		pot = 0;
		round = 1;
		dealer = nextPlayer(dealer + 1);
		betBlind(dealer + 1, small_blind);
		betBlind(temp + 1, big_blind);
		current = nextPlayer(temp + 1);
		response += ("Akcja gracza " + current + "|");
	}

	private void endgame() {

		if (active_players == 1) {
			for (Player p : gracze) {
				if (p.active == true) {
					response += ("Gracz " + p.id + " zgarnia pule!|");
					p.cash += pot;
				}
			}
		} else {
			ttab = new int[active_players];
			i = 0;
			for (Player p : gracze) {
				if (p.active == true || p.all_in == true) {
					ttab[i++] = p.id;
				}
			}
			showdown(ttab);
		}
		if (active_players > 1)
			newGame();

	}

	private void showdown(int[] who) {
		Cards[][] pCards = new Cards[who.length][2];
		for (int i = 0; i < who.length; i++) {
			pCards[i][0] = Cards.values()[gracze[i].hand[0]];
			pCards[i][1] = Cards.values()[gracze[i].hand[1]];
		}
		krupier = new Strategy(who.length, pCards, common);
		int[][] best = krupier.BestHands();
		response += ("Showdown!|");

		for (int i = 0; i < who.length; i++) {

			response += ("Gracz " + who[i] + " ma "
					+ Cards.values()[best[i][1]] + " "
					+ Cards.values()[best[i][2]] + " "
					+ Cards.values()[best[i][3]] + " "
					+ Cards.values()[best[i][4]] + " "
					+ Cards.values()[best[i][5]] + "|");

		}
		int winner[] = krupier.Winner(best, common, pCards);
		payment(winner);
		pot = 0;
	}

	private void payment(int[] winner) {
		int n = winner[winner.length - 1];

		for (int i = 0; i < n; i++) {
			if (gracze[i].id == i) {

				gracze[i].cash += pot / n;
				response += ("Gracz " + i + " wypłaca " + pot / n + "|");

			}
		}

	}

	private int[] startingHand() {
		ttab = new int[2];
		for (i = 0; i < 2; i++) {
			ttab[i] = talia.takeCard();
		}
		return ttab;
	}

	public int[] getHand(int player_id) {
		return gracze[player_id].getHand();
	}

	public int getCash(int player_id) {
		return gracze[player_id].cash;
	}

	public boolean checkActive(int player_id) {
		return gracze[player_id].active;
	}

	public int getCurrent() {
		return current;
	}

	public int getMyBet(int player_id) {
		return gracze[player_id].round_bet;
	}

	public int getMaxBet() {
		return max_bet;
	}

	public int getPot() {
		return pot;
	}

	public String getResponse() {
		return response;
	}

	public boolean quit(int player_id) {
		gracze[player_id].lost = true;
		gracze[player_id].active = false;
		active_players--;
		temp = 0;
		for (Player p : gracze) {
			if (p.lost == false) {
				temp++;
			}
		}
		if (temp <= 1) {
			alive = false;
		}
		return alive;
	}

	public void listen(Integer[] output) {
		System.out.println("Odebrano polecenie od serwera");
		for (int k = 0; k < output.length; k++)
			System.out.println(k + " " + output[k]);
		response = "";
		if (output[1] == 1) {
			// ### AKCJA BANKOWA ###
			switch ((int) output[2]) {
			case 1:
				check((int) output[0]);
				break;
			case 2: {
				bet((int) output[0], (int) output[3]);
				if (active_players == 1) {
					newRound();
				}
				break;
			}
			case 3:
				fold((int) output[0]);
				break;
			case 4:
				quit((int) output[0]);
				break;
			}

		}
		if ((int) output[1] == 1 && checkIfEnd()) {
			if (active_players == 1) {
				endgame();
			}
			if (new_game == false) {
				newRound();
			}
		} else {
			current = nextPlayer(current + 1);
			response += ("Akcja gracza " + current + "|");
		}
		new_game = false;

	}

}
