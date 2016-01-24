package GameLogic;

public class Player {

	public int cash;//
	public boolean active;
	public int round_bet;
	public int[] hand=new int[2];//
	public boolean lost;
	public boolean big_blind;
	public boolean small_blind;
	public boolean all_in;
	public boolean leader;
	public boolean fold;
	public int bet;
	public int id;

	Player(int id){
		this.active=true;
		this.id=id;
		this.round_bet=0;
		this.lost=false;
		this.big_blind=false;
		this.small_blind=false;
		this.all_in=false;
		this.leader=false;
		this.fold=false;
		this.bet=0;
		
	}
	public int[] getHand() {
		return hand;
	}
	public void setBet(int bet) {
		this.bet=bet;
		
	}
	public void setCash(int initial_cash) {
		this.cash=initial_cash;
		
	}

	public void setHand(int[] startingHand) {
		this.hand=startingHand;
		
	}

	public boolean checkActive() {
		return active;
	}

	public int getCash() {
		
		return cash;
	}

	public void bet(int what) {
		bet+=what;
		cash=cash-what;
		round_bet+=what;
		
	}

}
