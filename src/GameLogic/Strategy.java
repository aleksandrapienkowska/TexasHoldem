package GameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Strategy {
	private int playerscount;
	private int playersCards[]; // [id gracza]
	private Cards pCards[][]; // , [karta 1], [karta 2]
	private int common[]; // wspólne karty
	private Cards comm[];
	private int besthands[][]; // [id gracza] , [1,[2],[3],[4],[5]
	private Cards temporary[];
	boolean street = false;
	boolean poker = false;
	boolean four = false;
	boolean three = false;
	boolean full = false;
	boolean doublepairs = false;
	boolean colour = false;
	boolean pair = false;
	boolean highcard = false;
	int priorities[];
	int pritemp = 0;

	public Strategy(int count, Cards pCards[][], Cards comm[]) {
		this.playerscount = count;
		this.pCards = pCards;
		this.comm = comm;
		this.priorities = new int[count];
		besthands = new int[count][7];
	}

	private boolean IfPoker(int[] temp) {
		boolean poker = true;
		Arrays.sort(temp);

		for (int z = 0; z < temp.length - 1; z++) {

			if (temp[z] == temp[z + 1] - 4 && poker) {
				poker = true;
			} else
				poker = false;
		}
		return poker;
	}

	private boolean IfStreet(int[] temp) {
		boolean street = false;
		Arrays.sort(temp);

		for (int a = 1; a < temp.length; a++) {
			if (temp[a] == temp[a - 1] + 1) {
				street = true;
			} else {
				return false;
			}

		}
		return street;
	}

	private boolean If4(int[] temp) {

		boolean four = false;
		Arrays.sort(temp);
		int a = 0;
		while (a <= 1) {
			if (temp[a] == temp[a + 1] && temp[a + 1] == temp[a + 2]
					&& temp[a + 2] == temp[a + 3]) {
				four = true;
			}
			a++;
		}

		return four;

	}

	private boolean IfFull(int[] temp) {
		boolean full = false;
		boolean three = false;
		Arrays.sort(temp);
		int a = 0;
		int b = 8;
		while (a <= 2) {
			if (temp[a] == temp[a + 1] && temp[a + 1] == temp[a + 2]) {
				three = true;
				b = a;
			}
			a++;
		}
		if (b == 0 && three) {
			if (temp[3] == temp[4]) {
				full = true;
			}
		} else {
			if (temp[0] == temp[1] && three) {
				full = true;
			}
		}

		return full;
	}

	private boolean ifColour(int[] temp) {
		boolean colour = false;
		for (int a = 0; a < temp.length - 1; a++) {
			if (temp[a] == temp[a + 1]) {
				colour = true;
			} else
				return false;

		}
		return colour;
	}

	private boolean ifThree(int[] temp) {
		boolean three = false;

		Arrays.sort(temp);

		int a = 0;
		while (a <= 2) {
			if (temp[a] == temp[a + 1] && temp[a + 1] == temp[a + 2]) {
				three = true;
			}
			a++;
		}

		return three;
	}

	private boolean IfDouble(int[] temp) {
		boolean doublepairs = false;
		int a = 0, b = 0, c = 0, d = 0, e = 0;
		Arrays.sort(temp);
//		for(int i=0;i<temp.length;i++){
//			System.out.print(temp[i]+"  ");
//		}System.out.println("rozwazam podwojne pary");
//		
		if (temp[0] == temp[1]){
			b++;
			}

		if (temp[1] == temp[2]){
			c++;
			}

		if (temp[2] == temp[3]){
			d++;
		}

		if (temp[3] == temp[4]){
			e++;
			}

		if (b == 1 && d == 1)
			doublepairs = true;
		if (b==1 &&e==1)
			doublepairs=true;
		if (c == 1 && e == 1)
			doublepairs = true;

		return doublepairs;
	}

	private boolean IfPair(int[] temp) {

		boolean pair = false;
		Arrays.sort(temp);
		int a = 0;
		while (a < temp.length - 1) {
			if (temp[a] == temp[a + 1])
				pair = true;
			a++;
		}

		return pair;
	}

	public int[][] BestHands() {
		int k = 0;

		while (k < playerscount) {
			//System.out.println("Gracz  " + k);
			besthands[k][0] = k;
			temporary = new Cards[5];
			temporary[0] = pCards[k][0];
			temporary[1] = pCards[k][1];
			pritemp = 40;

			for (int a = 2; a < besthands[k].length; a++) {
				besthands[k][a] = 40;
			}
			besthands[k][6] = 40;
			int[] temp1 = new int[comm.length];
			for (int a = 0; a < comm.length; a++) {
				temp1[a] = comm[a].id;
			}
			Arrays.sort(temp1);
			for (int h = besthands[k].length - 2; h >= 1; h--) {
				besthands[k][h] = temp1[h - 1];

			}
			for (int i = 0; i < 5; i++) {

				for (int j = i + 1; j < 5; j++) {

					for (int l = j + 1; l < 5; l++) {

						poker = false;
						colour = false;
						four = false;
						full = false;
						street = false;
						three = false;
						doublepairs = false;
						pair = false;
						highcard = false;

						temporary[2] = comm[i];
						temporary[3] = comm[j];
						temporary[4] = comm[l];
						/*
						 * for(int h=0;h<temporary.length;h++){
						 * System.out.print(temporary[h].id+"  "); }
						 * System.out.println(" rozważana");
						 */
						int temp[] = new int[5];
						for (int w = 0; w < temp.length; w++) {
							temp[w] = temporary[w].id;

						}
//						for (int w = 0; w < temp.length; w++) {
//							System.out.print(temp[w] + "  ");
//
//						}
//						System.out.println("id");
						poker = IfPoker(temp);
						temp[0] = temporary[0].wartosc;
						temp[1] = temporary[1].wartosc;
						for (int a = 2; a < temp.length; a++) {
							temp[a] = temporary[a].wartosc;
						}
						/*
						 * for(int w=0;w<temp.length;w++){
						 * System.out.print(temp[w]+ "  ");
						 * 
						 * } System.out.println("wartosc");
						 */
						street = IfStreet(temp);
						four = If4(temp);
						full = IfFull(temp);
						three = ifThree(temp);
						doublepairs = IfDouble(temp);
						pair = IfPair(temp);
						temp[0] = temporary[0].kolor;
						temp[1] = temporary[1].kolor;
						for (int a = 2; a < temp.length; a++) {
							temp[a] = temporary[a].kolor;
						}
						colour = ifColour(temp);
						
//						  for(int w=0;w<temp.length;w++){
//						  System.out.print(temp[w]+ "  ");
//						  
//						  } System.out.println("kolor");
//						  
//						  System.out.print(" "+poker);
//						  System.out.print(" "+four);
//						  System.out.print(" "+full);
//						  System.out.print(" "+colour);
//						  System.out.print(" "+street);
//						  System.out.print(" "+three);
//						  System.out.print(" "+doublepairs);
//						  System.out.println(" "+pair);
//						 
						if (pritemp > 1 && poker)
							pritemp = 1;
						if (pritemp > 2 && four)
							pritemp = 2;
						if (pritemp > 3 && full)
							pritemp = 3;
						if (pritemp > 4 && colour)
							pritemp = 4;
						if (pritemp > 5 && street)
							pritemp = 5;
						if (pritemp > 6 && three)
							pritemp = 6;
						if (pritemp > 7 && doublepairs)
							pritemp = 7;
						if (pritemp > 8 && pair)
							pritemp = 8;

						//System.out.println("pritemp " + pritemp);
						if (pritemp < besthands[k][6]) {
							besthands[k][6] = pritemp;

							for (int h = 1; h < besthands[k].length - 1; h++) {
								besthands[k][h] = temporary[h - 1].id;
								//System.out.println(besthands[k][h] + " ");
							}
							//System.out.println();
						}
					}

				}

			}
//			for (int h = 0; h < besthands.length; h++) {
//				for (int l = 0; l < besthands[0].length; l++)
//					System.out.print(besthands[h][l] + "  ");
//
//				System.out.println();
//			}
			k = k + 1;
			pritemp = 0;
		}

		return besthands;
	}

	// winner[0] - id gracza który wygrywa,
	// winner[1]...[winner.length-2] - id gracza, który ma remis lub 0,
	// winner[winner.length-1] - ilość graczy wygrywających - 1 lub 2
	public int[] Winner(int besthands[][], Cards[] common, Cards pCards[][]) {
		int[] winner = new int[pCards.length + 1];
		int[] temp = new int[besthands.length];
		for (int a = 0; a < temp.length; a++) {
			temp[a] = besthands[a][6];
		}
		Arrays.sort(temp);
//		for (int b = 0; b < temp.length; b++)
//			System.out.print(temp[b] + "  ");
//		System.out.println();

		int[] priorities = new int[9];
		for (int a = 0; a < priorities.length; a++)
			priorities[a] = 0;
		for (int a = 0; a < besthands.length; a++) {
			if (besthands[a][6] == 1) {
				priorities[0]++;
			}
			if (besthands[a][6] == 2) {
				priorities[1]++;
			}
			if (besthands[a][6] == 3) {
				priorities[2]++;
			}
			if (besthands[a][6] == 4) {
				priorities[3]++;
			}
			if (besthands[a][6] == 5) {
				priorities[4]++;
			}
			if (besthands[a][6] == 6) {
				priorities[5]++;
			}
			if (besthands[a][6] == 7) {
				priorities[6]++;
			}
			if (besthands[a][6] == 8) {
				priorities[7]++;
			}
			if (besthands[a][6] == 40) {
				priorities[8]++;
			}
		}
		boolean remis = false;
		int licz = 0;
		while (licz < temp.length - 1 && temp[licz] == temp[licz + 1]) {
			remis = true;
			licz++;
		}

		if (remis) {
			
			int remistab[] = new int[licz + 1];
			int l = 0;
			for (int a = 0; a < besthands.length; a++) {
				if (temp[0] == besthands[a][6]) {
					remistab[l] = besthands[a][0];
					//System.out.println(remistab[l]);
					l++;
				}
				
			}
			
		//	System.out.println(" Remis pomiędzy");
			Cards[][] best = new Cards[licz + 1][5];
			for (int a = 0; a < best.length; a++) {
				best[a][0] = pCards[remistab[a]][0];
				best[a][1] = pCards[remistab[a]][1];
				for (int b = 2; b < best[0].length; b++) {
					for (int j = 0; j < comm.length; j++) {
						if (comm[j].id == besthands[remistab[a]][b + 1]) {
							best[a][b] = comm[j];
						}
					}
				}

			}
			for (int a = 0; a < best.length; a++) {
				Arrays.sort(best[a]);
//				for (int i = 0; i < best[a].length; i++) {
//					System.out.print(best[a][i] + "  ");
//				}
//				System.out.println();
			}

			if (besthands[remistab[0]][6] == 2) {
				// KARETA
				int a = 0;
				List<Integer> idkareta = new ArrayList<Integer>();
				for (int b = 0; b < best.length; b++) {
					a = 0;
					while (a <= 1) {
						if (best[b][a].wartosc == best[b][a + 1].wartosc
								&& best[b][a + 1].wartosc == best[b][a + 2].wartosc
								&& best[b][a + 2].wartosc == best[b][a + 3].wartosc) {
							idkareta.add(a);
						}
						a++;
						// System.out.println(idkareta+"  "+b);
					}
				}
				int win = 0;
				for (int b = 0; b < best.length - 1; b++) {
					if (best[b][idkareta.get(b)].wartosc > best[b + 1][idkareta
							.get(b + 1)].wartosc) {
						win = remistab[b];

					} else {
						if (best[b][idkareta.get(b)].wartosc < best[b + 1][idkareta
								.get(b + 1)].wartosc) {
							win = remistab[b + 1];
						}
						if (best[b][idkareta.get(b)].wartosc == best[b + 1][idkareta
								.get(b + 1)].wartosc) {
							if (idkareta.get(b) == 0) {
								if (idkareta.get(b + 1) == 0) {
									if (best[b][4].wartosc > best[b + 1][4].wartosc) {
										win = remistab[b];
									} else {
										win = remistab[b + 1];
									}
									if (best[b][4].wartosc == best[b + 1][4].wartosc) {
										win = 100;
									}
								} else {
									if (best[b][4].wartosc > best[b + 1][0].wartosc) {
										win = remistab[b];
									} else
										win = remistab[b + 1];
									if (best[b][4].wartosc == best[b + 1][0].wartosc) {
										win = 100;
									}
								}
							} else {

								if (idkareta.get(b + 1) == 0) {
									if (best[b][0].wartosc > best[b + 1][4].wartosc) {
										win = remistab[b];
									} else {
										win = remistab[b + 1];
									}
									if (best[b][0].wartosc == best[b + 1][4].wartosc) {
										win = 100;
									}
								} else {
									if (best[b][0].wartosc > best[b + 1][0].wartosc) {
										win = remistab[b];
									} else
										win = remistab[b + 1];
									if (best[b][0].wartosc == best[b + 1][0].wartosc) {
										win = 100;
									}
								}

							}
						}
					}
				}
				if (win != 100) {
					for (int i = 0; i < winner.length; i++) {
						winner[i] = 0;
					}
					winner[0] = win;
					winner[winner.length - 1] = 1;
				} else {
					for (int i = 0; i < winner.length; i++) {
						winner[i] = 0;
					}
					for (int i = 0; i < remistab.length; i++) {
						winner[i] = remistab[i];
					}
					winner[winner.length - 1] = remistab.length;
				}
			}
			if (besthands[remistab[0]][6] == 3) {
				// FULL
				int a = 0;
				List<Integer> idthree = new ArrayList<Integer>();
				for (int b = 0; b < best.length; b++) {
					a = 0;
					while (a <= 2) {
						if (best[b][a].wartosc == best[b][a + 1].wartosc
								&& best[b][a + 1].wartosc == best[b][a + 2].wartosc) {
							idthree.add(a);
						}
						a++;
					}
				}
				int win = remistab[0];
				List<Integer> winid = new ArrayList<Integer>();
				winid.add(win);
				for (int b = 1; b < best.length; b++) {
					if (best[win][idthree.get(win)].wartosc == best[b][idthree
							.get(b)].wartosc) {
						winid.add(b);
						win = b;
					}
					if (best[win][idthree.get(win)].wartosc < best[b][idthree
							.get(b)].wartosc) {
						winid.clear();
						winid.add(b);
						win = b;
					}
					if (best[win][idthree.get(win)].wartosc < best[b][idthree
							.get(b)].wartosc) {
						winid.clear();
						winid.add(win);
					}
				}
				int remisplayer = 100;
				List<Integer> winlist = new ArrayList<Integer>();
				if (winid.size() > 1) {
					win = winid.get(0);
					for (int i = 1; i < winid.size(); i++) {
						remisplayer = 100;
						if (idthree.get(win) == 0) {
							if (idthree.get(i) == 0) {
								if (best[win][3].wartosc < best[i][3].wartosc) {
									// i wygrywa i usuwam win z winlist
									win = i;
									winlist.remove(0);
								}
								if (best[win][3].wartosc == best[i][3].wartosc) {
									// remis i idzie do winlist
									remisplayer = i;
								}
							} else {
							}
						} else {
							if (idthree.get(i) == 0) {
								win = i;
								winlist.remove(0);
							} else {
								if (best[win][0].wartosc < best[i][0].wartosc) {
									// i wygrywa i usuwam win z winlist
									win = i;
									winlist.remove(0);
								}
								if (best[win][0].wartosc == best[i][0].wartosc) {
									// remis i i idzie do winlist
									remisplayer = i;
								}
							}
						}

						if (remisplayer < 100)
							winlist.add(remisplayer);
						else if (winlist.get(0) != win)
							winlist.add(win);

					}
					for (int j = 0; j < winner.length; j++) {
						winner[j] = 0;
					}
					for (int j = 0; j < winlist.size(); j++) {
						winner[j] = winlist.get(j);
					}
					winner[winner.length - 1] = winlist.size();
				} else {
					for (int i = 0; i < winner.length; i++) {
						winner[i] = 0;
					}
					winner[0] = winid.get(0);
					winner[winner.length - 1] = 1;
				}
			}
			if (besthands[remistab[0]][6] == 4||besthands[remistab[0]][6]==5) {
				// KOLOR + street
				int a=1;
				int win=0;
				List<Integer> winlist=new ArrayList<Integer>();
				
				
				int b;
				boolean colourremis=false;
				for(a=1;a<best.length;a++){
					for( b=best[0].length-1;b>=0&&a<best.length;b--){
						//System.out.println(a+" "+b+" "+win+" "+best[win][b].wartosc+ " "+best[a][b].wartosc);
						
						colourremis=false;
						if(best[win][b].wartosc<best[a][b].wartosc){
							win=a;
							
							//System.out.println("Wpisuje a "+remistab[win]);
							winner[0]=win;
							winner[winner.length-1]=1;
							break;
							
						}else
						if(best[win][b].wartosc>best[a][b].wartosc){
							
							//System.out.println("Wpisuje b "+remistab[win]);
							winner[0]=win;
							winner[winner.length-1]=1;
							break;
							
						}else
						if(best[win][b].wartosc==best[a][b].wartosc){
						  if(winlist.indexOf(win)==-1)
							winlist.add(win);
						  colourremis=true;
						  if(winlist.indexOf(a)==-1)
						    winlist.add(a);
						  }
						
						if(colourremis){
							//System.out.println("Zeruje"+winlist);
							for (int i = 0; i < winner.length; i++) {
								winner[i] = 0;
								
							}
							winner[0]=remistab[winlist.get(0)];
							int k=1;
							for(int j=1;j<winlist.size();j++){
								if(winlist.get(j)!=winlist.get(j-1)){
									winner[k]=remistab[winlist.get(j)];
									k++;
								}
							}
							winner[winner.length-1]=k;
						}else{
							//System.out.println("Wpisuje "+remistab[win]);
							for (int i = 0; i < winner.length; i++) {
								winner[i] = 0;
							}
							winner[0]=remistab[win];
							winner[winner.length-1]=1;
						}
						
					}
					
				}

			}
			
			if (besthands[remistab[0]][6] == 6) {
				//TROJKA
				int a = 0;
				List<Integer> idthree = new ArrayList<Integer>();
				for (int b = 0; b < best.length; b++) {
					
					a = 0;
					while (a <= 2) {
						if (best[b][a].wartosc == best[b][a + 1].wartosc
								&& best[b][a + 1].wartosc == best[b][a + 2].wartosc) {
							idthree.add(a);
						}
						a++;
					}
				}
				//System.out.println(idthree+" " + best.length);
				int win = remistab[0];
				List<Integer> winid = new ArrayList<Integer>();
				List<Integer> winlist = new ArrayList<Integer>();
				winid.add(win);
				for (int b = 1; b < best.length; b++) {
					if (best[win][idthree.get(win)].wartosc == best[b][idthree
							.get(b)].wartosc) {
						winid.add(b);
						win = b;
					}
					if (best[win][idthree.get(win)].wartosc < best[b][idthree
							.get(b)].wartosc) {
						winid.clear();
						winid.add(b);
						win = b;
					}
					if (best[win][idthree.get(win)].wartosc < best[b][idthree
							.get(b)].wartosc) {
						winid.clear();
						winid.add(win);
					}
				}
			//	System.out.println(winid);
				if(winid.size()==1){
					winner[0]=winid.get(0);
					winner[winner.length-1]=1;
				}else{
				  win=0;
				 
				  //System.out.println(winid+" wiinid");
				  for(int j=1;j<winid.size();j++){
					  //System.out.println(winlist+" PRzed win"+winid.get(win)+ " j "+winid.get(j));
					if(idthree.get(winid.get(win))>idthree.get(winid.get(j))){
						winlist.remove(winid.get(win));
						winner[0]=winid.get(j);
						winner[winner.length-1]=1;
						win=j;
						winlist.add(winid.get(win));
					}else{
						if(idthree.get(winid.get(win))==idthree.get(winid.get(j))){
							if(idthree.get(winid.get(win))==2){
								if(best[winid.get(win)][1].wartosc>best[winid.get(j)][1].wartosc){
									//win
									//System.out.println("win=2 win1>j1");
									winlist.add(winid.get(win));
									winner[0]=winid.get(win);
									winner[winner.length-1]=1;
								}else
								if(best[winid.get(win)][1].wartosc<best[winid.get(j)][1].wartosc){
									//j
									//System.out.println("win=2 win1<j1");
									winlist.remove(winid.get(win));
									winner[0]=winid.get(j);
									winner[winner.length-1]=1;
									win=j;winlist.add(winid.get(win));
								}else
								if(best[winid.get(win)][1].wartosc==best[winid.get(j)][1].wartosc){
									//System.out.println("win=2 win1=j1");
									if(best[winid.get(win)][0].wartosc>best[winid.get(j)][0].wartosc){
										//win
										//System.out.println("win=2  win0>j0");
										winner[0]=winid.get(win);
										winner[winner.length-1]=1;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][0].wartosc<best[winid.get(j)][0].wartosc){
										//j
										//System.out.println("win=2 win0<j0");
										winlist.remove(winid.get(win));
										winner[0]=winid.get(j);
										winner[winner.length-1]=1;
										win=j;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][0].wartosc==best[winid.get(j)][0].wartosc){
										//remis
										//System.out.println("win=2 remis");
										winner[0]=win;
										winner[1]=j;
										winner[winner.length-1]=2;
										winlist.add(winid.get(win));
										winlist.add(winid.get(j));
									}
								}
							}else
							if(idthree.get(winid.get(win))==1){
								//System.out.println("win=1 ");
								if(best[winid.get(win)][4].wartosc>best[winid.get(j)][4].wartosc){
									//win
									//System.out.println(" win4>j4");
									winner[0]=winid.get(win);
									winner[winner.length-1]=1;
									winlist.add(winid.get(win));
								}else
								if(best[winid.get(win)][4].wartosc<best[winid.get(j)][4].wartosc){
									//j
									//System.out.println("win4<j4");
									winlist.remove(winid.get(win));
									winner[0]=winid.get(j);
									winner[winner.length-1]=1;
									win=j;
									winlist.add(winid.get(win));
								}else
								if(best[winid.get(win)][4].wartosc==best[winid.get(j)][4].wartosc){
									//System.out.println(" win4==j4");
									if(best[winid.get(win)][0].wartosc>best[winid.get(j)][0].wartosc){
										//win
										//System.out.println("win0>j0");
										winner[0]=winid.get(win);
										winner[winner.length-1]=1;
										winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][0].wartosc<best[winid.get(j)][0].wartosc){
										//j
										//System.out.println("win0<j0");
										winlist.remove(winid.get(win));
										winner[0]=winid.get(j);
										winner[winner.length-1]=1;
										win=j;
										winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][0].wartosc==best[winid.get(j)][0].wartosc){
										//remis
										//System.out.println("win0=j0 remis");
										winner[0]=win;
										winner[1]=j;
										winner[winner.length-1]=2;
										winlist.add(winid.get(win));winlist.add(winid.get(j));
									}
								}
							}else
							if(idthree.get(winid.get(win))==0){
								//System.out.println("win=0");
								if(best[winid.get(win)][4].wartosc>best[winid.get(j)][4].wartosc){
									//win wygral
									//System.out.println("win4>j4");
									winner[0]=winid.get(win);
									winner[winner.length-1]=1;winlist.add(winid.get(win));
								}else
								if(best[winid.get(win)][4].wartosc<best[winid.get(j)][4].wartosc){
									//j wygral
									//System.out.println("win4<j4");
									winlist.remove(winid.get(win));
									winner[0]=winid.get(j);
									winner[winner.length-1]=1;
									win=j;winlist.add(winid.get(win));
								}else
								if(best[winid.get(win)][4].wartosc==best[winid.get(j)][4].wartosc){
									//System.out.println("win4=j4");
									if(best[winid.get(win)][3].wartosc>best[winid.get(j)][3].wartosc){
										//win wygral
										//System.out.println("win3>j3");
										winner[0]=winid.get(win);
										winner[winner.length-1]=1;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][3].wartosc<best[winid.get(j)][3].wartosc){
										//j wygral
										//System.out.println("win3<j3");
										winlist.remove(winid.get(win));
										winner[0]=winid.get(j);
										winner[winner.length-1]=1;
										win=j;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][3].wartosc==best[winid.get(j)][3].wartosc){
										//remis
									//	System.out.println("win3==j3 remis" );
										winlist.add(winid.get(win));
										winlist.add(winid.get(j));
										winner[0]=win;
										winner[1]=j;
										winner[winner.length-1]=2;
									}
								}
							}
						}else
						if(idthree.get(winid.get(win))>idthree.get(winid.get(j))){
							//System.out.println("pozycjawin>pozycj");
							if(idthree.get(winid.get(win))==1){
								//System.out.println("win=1 win wygrywa");
								//win wygral
								winner[0]=winid.get(win);
								winner[winner.length-1]=1;winlist.add(winid.get(win));
							}else
							if(idthree.get(winid.get(win))==0){
								//System.out.println("win=0");
								if(idthree.get(winid.get(j))==2){
									//win wygral
									//System.out.println("j=2 win wygrywa");
									winner[0]=winid.get(win);
									winner[winner.length-1]=1;winlist.add(winid.get(win));
								}
								else{
									if(best[winid.get(win)][4].wartosc==best[winid.get(j)][4].wartosc){
										//win wygral
										//System.out.println("win4>j4");
										winner[0]=winid.get(win);
										winner[winner.length-1]=1;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][4].wartosc<best[winid.get(j)][4].wartosc){
										//j wygral
										//System.out.println("win4<j4");
										winlist.remove(winid.get(win));
										winner[0]=winid.get(j);
										winner[winner.length-1]=1;
										win=j;winlist.add(winid.get(win));
									}else
									if(best[winid.get(win)][4].wartosc==best[winid.get(j)][4].wartosc){
										//win wygral
										//System.out.println("win4==j4 remis");
										winlist.add(winid.get(win));
										winlist.add(winid.get(j));
										winner[0]=win;
										winner[1]=j;
										winner[winner.length-1]=2;
									}
								}
							}
							
						}
					
					}
					//System.out.println(winlist+" Po");
				  }
				}
				for (int i = 0; i < winner.length; i++) {
					winner[i] = 0;
					
				}
				winner[0]=remistab[winlist.get(0)];
				int k=1;
				for(int j=1;j<winlist.size();j++){
					if(winlist.get(j)!=winlist.get(j-1)){
						winner[k]=remistab[winlist.get(j)];
						k++;
					}
				}
				winner[winner.length-1]=k;
				
			}
			if (besthands[remistab[0]][6] == 7) {
				//Dwie pary
				int[][] idtab=new int[remistab.length][4];
				int b=0,c=0,d=0,e=0;
				for(int i=0;i<best.length;i++){
					b=0;
					c=0;
					d=0;
					e=0;


					if (best[i][0].wartosc == best[i][1].wartosc){
						b++;
						}

					if (best[i][1].wartosc == best[i][2].wartosc){
						c++;
						}

					if (best[i][2].wartosc == best[i][3].wartosc){
						d++;
					}

					if (best[i][3].wartosc == best[i][4].wartosc){
						e++;
						}

					if (b == 1 && d == 1){
						idtab[i][0]=2;
						idtab[i][1]=0;
						idtab[i][2]=4;
						
						}
					if (b==1 &&e==1){
						idtab[i][0]=3;
						idtab[i][1]=0;
						idtab[i][2]=2;
					}
						
					if (c == 1 && e == 1){
						idtab[i][0]=3;
						idtab[i][1]=1;
						idtab[i][2]=0;
					}
					idtab[i][3]=i;
//					for(int j=0;j<idtab[0].length;j++){
//						System.out.print(idtab[i][j]+" ");
//					}	System.out.println(i);
				}
				int win=0;
				List<Integer> winlist = new ArrayList<Integer>();
				for(int i=1;i<best.length;i++){
					//System.out.println(winlist+" Przed");
					if(best[win][idtab[win][0]].wartosc>best[i][idtab[i][0]].wartosc){
						//win wygral
						if(winlist.indexOf(win)==-1)
						winlist.add(win);
					}else if(best[win][idtab[win][0]].wartosc<best[i][idtab[i][0]].wartosc){
						//j wygrał
						winlist.remove(win);
						win=i;
						if(winlist.indexOf(win)==-1)
						winlist.add(win);
					}else if(best[win][idtab[win][0]].wartosc==best[i][idtab[i][0]].wartosc){
						if(best[win][idtab[win][1]].wartosc>best[i][idtab[i][1]].wartosc){
							//win wygrał
							if(winlist.indexOf(win)==-1)
							winlist.add(win);
						}else if(best[win][idtab[win][1]].wartosc<best[i][idtab[i][1]].wartosc){
							//j wygrał
							winlist.remove(win);
							win=i;
							if(winlist.indexOf(win)==-1)
							winlist.add(win);
						}else if(best[win][idtab[win][1]].wartosc==best[i][idtab[i][1]].wartosc){
							if(best[win][idtab[win][2]].wartosc>best[i][idtab[i][2]].wartosc){
								//win wygrał
								if(winlist.indexOf(win)==-1)
								winlist.add(win);
							}else if(best[win][idtab[win][2]].wartosc<best[i][idtab[i][2]].wartosc){
								//j wygrał
								winlist.remove(win);
								win=i;
								if(winlist.indexOf(win)==-1)
								winlist.add(win);
							}else if(best[win][idtab[win][2]].wartosc==best[i][idtab[i][2]].wartosc){
								//remis
								if(winlist.indexOf(win)==-1)
								winlist.add(win);
								if(winlist.indexOf(i)==-1)
								winlist.add(i);
							}
						}
					}
					//System.out.println(winlist+" Po");
				}
				for(int i=0;i<winlist.size();i++){
					winner[i]=remistab[winlist.get(i)];
				}
				winner[winner.length-1]=winlist.size();
				
				
			}
			if (besthands[remistab[0]][6] == 8) {
				//Para
				int[][] idtab=new int[remistab.length][2];
				int b=0,c=0,d=0,e=0;
				for(int i=0;i<best.length;i++){
					b=0;
					c=0;
					d=0;
					e=0;


					if (best[i][0].wartosc == best[i][1].wartosc){
						b++;
						}

					if (best[i][1].wartosc == best[i][2].wartosc){
						c++;
						}

					if (best[i][2].wartosc == best[i][3].wartosc){
						d++;
					}

					if (best[i][3].wartosc == best[i][4].wartosc){
						e++;
						}

					if (b == 1 ){
						idtab[i][0]=0;
						
						
						}
					if (c==1){
						idtab[i][0]=1;
					}
						
					if (d==1){
						idtab[i][0]=2;
					}
					if (e==1){
						idtab[i][0]=3;
					}
					idtab[i][1]=i;
//					for(int j=0;j<idtab[0].length;j++){
//						System.out.print(idtab[i][j]+" ");
//					}	System.out.println(i);
				}
				int win=0;
				List<Integer> winlist = new ArrayList<Integer>();
				for(int i=1;i<idtab.length;i++){
					//System.out.println(winlist+" Przed");
					if(best[idtab[i][1]][idtab[i][0]].wartosc>best[idtab[win][1]][idtab[win][0]].wartosc){
						//i wygrywa
						if(winlist.indexOf(win)!=-1)
						winlist.remove(win);
						win=i;
						if(winlist.indexOf(win)==-1)
						winlist.add(win);
					}else if(best[idtab[i][1]][idtab[i][0]].wartosc<best[idtab[win][1]][idtab[win][0]].wartosc){
						//win wygrywa
						if(winlist.indexOf(win)==-1)
							winlist.add(win);
					}else if(best[idtab[i][1]][idtab[i][0]].wartosc==best[idtab[win][1]][idtab[win][0]].wartosc){
						if(idtab[i][0]>idtab[win][0]){
							if(winlist.indexOf(win)==-1)
								winlist.add(win);
						}else if(idtab[i][0]<idtab[win][0]){
							if(winlist.indexOf(win)!=-1)
							winlist.remove(win);
							win=i;
							if(winlist.indexOf(win)==-1)
							winlist.add(win);
						}else if(idtab[i][0]==idtab[win][0]){
							
							if(idtab[i][0]==3){
								
								for(int a=2;a>=0;a--){
									
									System.out.println(best[idtab[i][1]][a]+"  "+ best[idtab[win][1]][a]);
									System.out.println(winlist);
									if(best[idtab[i][1]][a].wartosc>best[idtab[win][1]][a].wartosc){
										//i wygrywa
										if(winlist.indexOf(win)!=-1)
										winlist.remove(win);
										win=i;
										if(winlist.indexOf(win)==-1)
										winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc<best[idtab[win][1]][a].wartosc){
										//win wygrywa
										if(winlist.indexOf(new Integer(i))!=-1)
											winlist.remove(new Integer(i));										
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
										
									}else if(best[idtab[i][1]][a].wartosc==best[idtab[win][1]][a].wartosc){
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
											if(winlist.indexOf(i)==-1)
											winlist.add(i);
									}
								}
							}else if(idtab[i][0]==2){
								for(int a=4;a>=0;a--){
									
									if(a!=2 && a!=3){
									if(best[idtab[i][1]][a].wartosc>best[idtab[win][1]][a].wartosc){
										//i wygrywa
										if(winlist.indexOf(win)!=-1)
										winlist.remove(win);
										win=i;
										if(winlist.indexOf(win)==-1)
										winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc<best[idtab[win][1]][a].wartosc){
										//win wygrywa
										if(winlist.indexOf(new Integer(i))!=-1)
											winlist.remove(new Integer(i));
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc==best[idtab[win][1]][a].wartosc){
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
											if(winlist.indexOf(i)==-1)
											winlist.add(i);
									}
									}
								}
							}else if(idtab[i][0]==1){
								for(int a=4;a>=0;a--){
									
									if(a!=1 && a!=2){
									if(best[idtab[i][1]][a].wartosc>best[idtab[win][1]][a].wartosc){
										//i wygrywa
										if(winlist.indexOf(win)!=-1)
										winlist.remove(win);
										win=i;
										if(winlist.indexOf(win)==-1)
										winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc<best[idtab[win][1]][a].wartosc){
										//win wygrywa
										if(winlist.indexOf(new Integer(i))!=-1)
											winlist.remove(new Integer(i));
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc==best[idtab[win][1]][a].wartosc){
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
											if(winlist.indexOf(i)==-1)
											winlist.add(i);
									}
									}
								}
							}else if(idtab[i][0]==0){
								for(int a=4;a>=0;a--){
									
									if(a!=0 && a!=1){
									if(best[idtab[i][1]][a].wartosc>best[idtab[win][1]][a].wartosc){
										//i wygrywa
										if(winlist.indexOf(win)!=-1)
										winlist.remove(win);
										win=i;
										if(winlist.indexOf(win)==-1)
										winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc<best[idtab[win][1]][a].wartosc){
										//win wygrywa
										if(winlist.indexOf(new Integer(i))!=-1)
											winlist.remove(new Integer(i));
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
									}else if(best[idtab[i][1]][a].wartosc==best[idtab[win][1]][a].wartosc){
										if(winlist.indexOf(win)==-1)
											winlist.add(win);
											if(winlist.indexOf(i)==-1)
											winlist.add(i);
									}
									}
								}
							}
						}
					}
				//	System.out.println(winlist+" Po");
				}
				for(int i=0;i<winlist.size();i++){
					winner[i]=remistab[winlist.get(i)];
				}
				winner[winner.length-1]=winlist.size();

			}
			if (besthands[remistab[0]][6] == 40) {
				int win=0;
				List<Integer> winlist = new ArrayList<Integer>();
				for(int i=1;i<remistab.length;i++){
					if(best[remistab[i]][1].wartosc>best[remistab[win]][1].wartosc){
						//i wygrywa;
						if(winlist.indexOf(win)!=-1)
							winlist.remove(win);
							win=i;
							if(winlist.indexOf(win)==-1)
							winlist.add(win);
					}else if(best[remistab[i]][1].wartosc<best[remistab[win]][1].wartosc){
						//win wygrywa
						if(winlist.indexOf(new Integer(i))!=-1)
							winlist.remove(new Integer(i));
						if(winlist.indexOf(win)==-1)
							winlist.add(win);
					}else if(best[remistab[i]][1].wartosc==best[remistab[win]][1].wartosc){
						if(best[remistab[i]][0].wartosc>best[remistab[win]][0].wartosc){
							//i wygrywa;
							if(winlist.indexOf(win)!=-1)
								winlist.remove(win);
								win=i;
								if(winlist.indexOf(win)==-1)
								winlist.add(win);
						}else if(best[remistab[i]][0].wartosc<best[remistab[win]][0].wartosc){
							//win wygrywa
							if(winlist.indexOf(new Integer(i))!=-1)
								winlist.remove(new Integer(i));
							if(winlist.indexOf(win)==-1)
								winlist.add(win);
						}else if(best[remistab[i]][0].wartosc==best[remistab[win]][0].wartosc){
							if(winlist.indexOf(win)==-1)
								winlist.add(win);
								if(winlist.indexOf(i)==-1)
								winlist.add(i);
						}
					}
				}
				for(int i=0;i<winlist.size();i++){
					winner[i]=remistab[winlist.get(i)];
				}
				winner[winner.length-1]=winlist.size();

			}

		} else {
			for (int b = 0; b < besthands.length; b++) {
				if (temp[0] == besthands[b][6]) {
					winner[0] = besthands[b][0];
					for (int x = 1; x < winner.length - 1; x++)
						winner[x] = 0;
					winner[winner.length - 1] = 1;
				}
			}
		}

//		for (int d = 0; d < winner.length; d++) {
//			System.out.print(winner[d] + "  ");
//		}

		return winner;

	}

	public static void main(String args[]) {
		
		 int players=7;
		 
		  Cards pCards[][]=new Cards[players][2];
		  
		  Cards
		  comm[]={Cards.DZIESIEC_a,Cards.DZIEWIEC_a,Cards.KING_a,Cards.SZESC_d
		  ,Cards.TRZY_b }; pCards[0][0]=Cards.AS_b;
		  pCards[0][1]=Cards.CZTERY_b; pCards[1][0]=Cards.DZIESIEC_b;
		  pCards[1][1]=Cards.PIEC_c; pCards[2][0]=Cards.CZTERY_a;
		  pCards[2][1]=Cards.TRZY_a; pCards[3][0]=Cards.QUEEN_c;
		  pCards[3][1]=Cards.JOPEK_d; pCards[4][0]=Cards.SZESC_c;
		  pCards[4][1]=Cards.SZESC_b; pCards[5][0]=Cards.DZIEWIEC_b;
		  pCards[5][1]=Cards.KING_c; pCards[6][0]=Cards.QUEEN_a;
		  pCards[6][1]=Cards.JOPEK_a; System.out.println("graczy    "+players);
		  Strategy St=new Strategy(players, pCards, comm);
		  
		  int[][] best=St.BestHands(); for(int i=0;i<best.length;i++){ for(int
		  k=0;k<best[0].length;k++){ System.out.print(best[i][k]+"  "); }
		  System.out.println(""); }
		 
		
		 int players2=2;
		 
		 Cards pCards2[][]=new Cards[players2][2];
		  
		  Cards
		  comm2[]={Cards.SIEDEM_a,Cards.SIEDEM_b,Cards.DWA_c,Cards.SZESC_d
		  ,Cards.TRZY_b }; pCards2[1][0]=Cards.SIEDEM_c;
		  pCards2[1][1]=Cards.SIEDEM_d; pCards2[0][0]=Cards.DWA_b;
		  pCards2[0][1]=Cards.DWA_d; Strategy St2=new Strategy(players2,
		  pCards2, comm2); System.out.println("graczy    "+players2); int[][]
		  best2=St2.BestHands(); for(int i=0;i<best2.length;i++){ for(int
		  k=0;k<best2[0].length;k++){ System.out.print(best2[i][k]+"  "); }
		  System.out.println(""); } System.out.println(""); St.Winner(best,
		  comm, pCards); System.out.println(""); System.out.println("");
		  St2.Winner(best2, comm2, pCards2); System.out.println("");
		  System.out.println("Remisy");
		 
		int players3 = 3;
		Cards pCards3[][] = new Cards[players3][2];
		Cards comm3[] = { Cards.KING_c, Cards.OSIEM_c, Cards.DZIEWIEC_c,
				Cards.DZIESIEC_c, Cards.AS_a };
		pCards3[2][0] = Cards.TRZY_d;
		pCards3[2][1] = Cards.DWA_a;
		pCards3[1][0] = Cards.PIEC_b;
		pCards3[1][1] = Cards.TRZY_b;
		pCards3[0][0] = Cards.PIEC_a;
		pCards3[0][1] = Cards.DWA_c;

		Strategy St3 = new Strategy(players3, pCards3, comm3);
		int[][] best3 = St3.BestHands();
		for (int i = 0; i < best3.length; i++) {
			for (int k = 0; k < best3[0].length; k++) {
				System.out.print(best3[i][k] + "  ");
			}
			System.out.println("");
		}
		System.out.println("");
		int winner[]=St3.Winner(best3, comm3, pCards3);
		for(int a=0;a<winner.length;a++){
			System.out.print(winner[a]+"  ");
		}

	}

}
