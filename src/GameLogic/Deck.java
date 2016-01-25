package GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	ArrayList<Integer> talia, dumped;
	int temp;
	Random random;

	public Deck() {
		talia = new ArrayList<Integer>(52) {
			{
				add(1);
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
				add(7);
				add(8);
				add(9);
				add(10);
				add(11);
				add(12);
				add(13);
				add(14);
				add(15);
				add(16);
				add(17);
				add(18);
				add(19);
				add(20);
				add(21);
				add(22);
				add(23);
				add(24);
				add(25);
				add(26);
				add(27);
				add(28);
				add(29);
				add(30);
				add(31);
				add(32);
				add(33);
				add(34);
				add(35);
				add(36);
				add(37);
				add(38);
				add(39);
				add(40);
				add(41);
				add(42);
				add(43);
				add(44);
				add(45);
				add(46);
				add(47);
				add(48);
				add(49);
				add(50);
				add(51);
				add(0);
			}
		};
		dumped = new ArrayList<Integer>();
		random = new Random();
		Collections.shuffle(talia, random);
	}

	public int takeCard() {
		if (talia.size() == 0) {
			talia = dumped;
			dumped = new ArrayList<Integer>();
			Collections.shuffle(talia, random);
		}
		temp = talia.get(0);
		talia.remove(0);
		talia.trimToSize();
		return temp;
	}

	public void dumpCard(int id) {
		dumped.add(id);
		dumped.trimToSize();
	}

	public static int val(int id) {
		return (Cards.values()[id]).wartosc;
	}

	public static int col(int id) {
		return (Cards.values()[id]).kolor;
	}

}
