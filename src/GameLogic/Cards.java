package GameLogic;

public enum Cards {
	// Pierwsza cyfra w nawiasie pokazuje ID karty - po tym ja wolamy za pomoca
	// Cards.get(id)

	DWA_a(0, 1, 1), DWA_b(1, 1, 2), DWA_c(2, 1, 3), DWA_d(3, 1, 4), TRZY_a(4,
			2, 1), TRZY_b(5, 2, 2), TRZY_c(6, 2, 3), TRZY_d(7, 2, 4), CZTERY_a(
			8, 3, 1), CZTERY_b(9, 3, 2), CZTERY_c(10, 3, 3), CZTERY_d(11, 3, 4), PIEC_a(
			12, 4, 1), PIEC_b(13, 4, 2), PIEC_c(14, 4, 3), PIEC_d(15, 4, 4), SZESC_a(
			16, 5, 1), SZESC_b(17, 5, 2), SZESC_c(18, 5, 3), SZESC_d(19, 5, 4), SIEDEM_a(
			20, 6, 1), SIEDEM_b(21, 6, 2), SIEDEM_c(22, 6, 3), SIEDEM_d(23, 6,
			4), OSIEM_a(24, 7, 1), OSIEM_b(25, 7, 2), OSIEM_c(26, 7, 3), OSIEM_d(
			27, 7, 4), DZIEWIEC_a(28, 8, 1), DZIEWIEC_b(29, 8, 2), DZIEWIEC_c(
			30, 8, 3), DZIEWIEC_d(31, 8, 4), DZIESIEC_a(32, 9, 1), DZIESIEC_b(
			33, 9, 2), DZIESIEC_c(34, 9, 3), DZIESIEC_d(35, 9, 4), JOPEK_a(36,
			10, 1), JOPEK_b(37, 10, 2), JOPEK_c(38, 10, 3), JOPEK_d(39, 10, 4), QUEEN_a(
			40, 11, 1), QUEEN_b(41, 11, 2), QUEEN_c(42, 11, 3), QUEEN_d(43, 11,
			4), KING_a(44, 12, 1), KING_b(45, 12, 2), KING_c(46, 12, 3), KING_d(
			47, 12, 4), AS_a(48, 13, 1), AS_b(49, 13, 2), AS_c(50, 13, 3), AS_d(
			51, 13, 4);

	final int id;
	final int wartosc; // wg zasad badugi
	final int kolor; // 1-czerwo,2-wino,3-zoladz,4-dzwonki

	Cards(int id, int wartosc, int kolor) {
		this.id = id;
		this.wartosc = wartosc;
		this.kolor = kolor;
	}

}
