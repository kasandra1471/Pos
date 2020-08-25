package PosControl;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;

class PosManager {

	HashSet<GoodsInfo> goodsList = new HashSet<GoodsInfo>();
	HashSet<SellInfo> sellList = new HashSet<SellInfo>();
	String goods = "D:/DBFILE/goods.txt";
	String orders = "D:/DBFILE/orders.txt";
	int aa = 0;

	private PosManager() {

	}

	static PosManager inst = null;

	public static PosManager creatManagerInst() {
		if (inst == null)
			inst = new PosManager();

		return inst;
	}

	DecimalFormat formatter = new DecimalFormat("###,###");

	void sales() { // ��ǰ �Ǹ�

		System.out.println("1. ��ǰ �Ǹ�  \n2. ���� ����\n3. ���� ����\n4. ��ǰ�� ����\n5. �� ���� \n6. ���� �ܰ�");
		int choice = Pos.sc.nextInt();

		switch (choice) {

		case 1: // �Ǹ��ϸ� ��� �����ǰ� ������ ���.
			Pos.sc.nextLine();
			System.out.println("������ ����մϴ�.");
			System.out.println("��ǰ �ڵ�: ");
			String goodsCode = Pos.sc.nextLine();

			System.out.println("�Ǹ� ����: ");
			int inventory = Pos.sc.nextInt();
			Pos.sc.nextLine();

			Iterator<GoodsInfo> itr = goodsList.iterator();

			while (itr.hasNext()) {
				GoodsInfo g = itr.next();

				if (goodsCode.equals(g.getGoodsCode())) {
					if (g.getInventory() < inventory) {
						System.out.println("��� �����մϴ�.");

						break;
					}
					aa = 1;
					 SellInfo si = new SellInfo(g.getDate(), g.getGoodsName(), g.getPrice(), g.getSell(), g.getGoodsCode());


					int a = g.getInventory() - inventory;

					si.setSell(g.getPrice() * inventory); // ���� * ���� = �Ǹ�
					g.setInventory(a);

					sellList.add(si);
					System.out.println("�ֹ���ȣ: " + g.getDate() + " ��ǰ��: " + g.getGoodsName() + " ��ǰ�ǸŰ���: " + g.getPrice()
							+ " ����: " + inventory + "�� �Դϴ�.");
					System.out.println("������ " + g.getPrice() * inventory + "�� �Դϴ�.");

					break;
				}
			}
			if (aa != 1) {
				System.out.println("ã�� ��ǰ�� �����ϴ�.");
			}

			break;

		case 2: // ���� ����
			System.out.println("���� ���ڸ� �Է��ϼ���. ��)20200101");
			Pos.sc.nextLine();
			String day = Pos.sc.nextLine();
			int daysum = 0;
			Iterator<SellInfo> itr3 = sellList.iterator();
			while (itr3.hasNext()) {
				SellInfo s = itr3.next();
				if (s.getDate1().contains(day)) {
					daysum += s.getSell();
				}
			}
			System.out.println(day + "�� �� ������ " + formatter.format(daysum) + "�� �Դϴ�.");
			break;
		case 3: // ���� ����
			System.out.println("���� ���� �Է��ϼ���. ��)202001");
			Pos.sc.nextLine();
			String mon = Pos.sc.nextLine();
			int monsum = 0;
			Iterator<SellInfo> itr4 = sellList.iterator();
			while (itr4.hasNext()) {
				SellInfo s = itr4.next();
				if (s.getDate1().contains(mon)) {
					monsum += s.getSell();
				}
			}
			System.out.println(mon + "�� �� ������ " + monsum + "�� �Դϴ�.");
			break;
		case 4:
			System.out.println("��ǰ�� �� �����Դϴ�.");
			System.out.print("��ǰ �ڵ带 �Է��ϼ���: ");
			Pos.sc.nextLine();
			String goodsCode1 = Pos.sc.nextLine();
			int gsum = 0;
			Iterator<SellInfo> itr5 = sellList.iterator();
			while (itr5.hasNext()) {
				SellInfo s = itr5.next();
				if (goodsCode1.equals(s.getGoodsCode())) {
					gsum += s.getSell();
					}
			}
			System.out.println("��ǰ�ڵ� " + goodsCode1 + "�� �� ������ " + formatter.format(gsum) + "�� �Դϴ�.");
			break;

		case 5: // �� ����
			int sum = 0;
			Iterator<SellInfo> itr2 = sellList.iterator();
			while (itr2.hasNext()) {
				SellInfo s = itr2.next();

				s.showsell();
				sum += s.getSell();

			}
			System.out.println("�� �������: " + formatter.format(sum) + " �� �Դϴ�.");
			break;
		case 6: // ���� �ܰ�
			break;
		}
	}

	void goods() { // ��ǰ ���� , ����� ���� �ǸŽ� �ȳ� �޼��� ǥ���ϰ� �޴��� ���ư�
		System.out.println("�޴� ��ȣ�� ���� �ϼ���  ");
		System.out.println("1. ��ǰ���");
		System.out.println("2. ��ǰ�˻�");
		System.out.println("3. ��ǰ����");
		System.out.println("4. ��ǰ����");
		System.out.println("5. ��ǰ��ü���");
		System.out.println("6. ���� �޴�");
		int choice = Pos.sc.nextInt();
		switch (choice) {

		case 1: // ��ǰ���

			System.out.println("��ǰ �Է��� �����մϴ�.");
			System.out.print("��ǰ�ڵ�: ");
			Pos.sc.nextLine();
			String goodsCode = Pos.sc.nextLine();
			boolean has = searchIndex(goodsCode, "search");
			if (has) {
				Iterator<GoodsInfo> itr = goodsList.iterator();
				while (true) {
					GoodsInfo g = itr.next();
					if (goodsCode.equals(g.getGoodsCode())) {
						System.out.println("�̹� ��ϵ� ��ǰ�Դϴ�.");
						System.out.println("��� �߰� �մϴ�.");
						System.out.println("�߰� �Է�: ");
						int b = Pos.sc.nextInt();
						int c = g.getInventory() + b;
						g.setInventory(c);
						System.out.println("��ǰ�ڵ�: " + goodsCode + " ��ǰ��: " + g.getGoodsName() + " ����: "
								+ g.getInventory() + "�� �Դϴ�.");
						break;
					}
				}

			} else {

				System.out.print("��ǰ��: ");
				String goodsName = Pos.sc.nextLine();
				System.out.print("��ǰ�ǸŰ���: ");
				int price = Pos.sc.nextInt();
				System.out.println("���: ");
				int Inventory = Pos.sc.nextInt();
				GoodsInfo gi = new GoodsInfo(goodsCode, goodsName, price, Inventory);
				goodsList.add(gi);
				System.out.println("[��ǰ�ڵ�: " + goodsCode + ", ��ǰ��: " + goodsName + ", ��ǰ�ǸŰ���: " + price + ", ���: "
						+ Inventory + "]");
				System.out.println("��� �Է��� �Ϸ� �Ǿ����ϴ�.");
				break;
			}
			break;
		case 2: // ��ǰ �˻�
			System.out.println("��ǰ �˻��� �����մϴ�.");
			System.out.print("��ǰ�ڵ� : ");
			String goodsCode3 = Pos.sc.nextLine();
			boolean has2 = searchIndex(goodsCode3, "search");

			if (!has2) {
				System.out.println("ã�� ���� �������� �ʽ��ϴ�. �����ܰ�� �̵� �մϴ�.");
			}
			break;

		case 3: // ��ǰ ����
			System.out.println("�����Ͻ� ��ǰ�� �ڵ带 �Է��� �ּ���");
			Pos.sc.nextLine();
			String goodsCode1 = Pos.sc.nextLine();
			boolean has1 = searchIndex(goodsCode1, "search");
			if (has1) {
				Iterator<GoodsInfo> itr1 = goodsList.iterator();
				while (itr1.hasNext()) {
					GoodsInfo g = itr1.next();
					if (goodsCode1.equals(g.getGoodsCode())) {
						System.out.println("�����Ͻ� �ڵ带 �Է��� �ּ���.");
						String goodsCode2 = Pos.sc.nextLine();
						g.setGoodsCode(goodsCode2);
						System.out.println("�����Ͻ� ��ǰ���� �Է��� �ּ���.");
						String goodsName = Pos.sc.nextLine();
						g.setGoodsName(goodsName);
						System.out.println("�����Ͻ� ��ǰ�ǸŰ����� �Է��� �ּ���.");
						int price = Pos.sc.nextInt();
						g.setPrice(price);
						System.out.println("�����Ͻ� ����� �Է��� �ּ���.");
						int inventory = Pos.sc.nextInt();
						g.setInventory(inventory);

						System.out.println("��ǰ�ڵ�: " + goodsCode2 + " ��ǰ��: " + g.getGoodsName() + "��ǰ�ǸŰ���: "
								+ g.getPrice() + ", ���: " + g.getInventory() + "]");
						break;
					}
				}
			} else {

				System.out.println("�ڵ带 Ȯ���Ͽ� �ֽʽÿ�.");
				break;
			}

			break;
		case 4: // ��ǰ ����
			System.out.println("��ǰ ������ �����մϴ�");
			System.out.print("��ǰ�ڵ� : ");
			Pos.sc.nextLine();
			String goodsCode4 = Pos.sc.nextLine();
			boolean has3 = searchIndex(goodsCode4, "search");

			if (!has3) {
				System.out.println("ã�� ���� �������� �ʽ��ϴ�. ���ܰ�� �̵� �մϴ�.");
			} else {
				String b = "y";
				String c = "n";
				System.out.println("���� �Ͻðڽ��ϱ�? (�����ҹ���) y / n");
				String a = Pos.sc.nextLine();

				if (a.equals(b)) {
					has3 = searchIndex(goodsCode4, "del");
					System.out.println("��ǰ�ڵ� " + goodsCode4 + "�� ��ǰ�� �����߽��ϴ�.");
				} else if (a.equals(c)) {
					System.out.println("���� ���� �ʽ��ϴ�. �����ܰ�� �̵� �մϴ�.");
				} else {
					System.out.println("�����ܰ�� �̵��մϴ�.");
					break;
				}
			}

			break;
		case 5: // �����
			Iterator<GoodsInfo> itr = goodsList.iterator();
			while (itr.hasNext()) {
				GoodsInfo p = itr.next();
				p.showGoodsInfo();
			}
			break;
		case 6: {
			System.out.println("�����޴��� ���ư��ϴ�.");
		}

		default:

			try {
				throw new MyExcep(choice);
			} catch (MyExcep e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
			}

		}
	}

	boolean searchIndex(String goodsCode, String state) {
		Iterator<GoodsInfo> itr = goodsList.iterator();
		boolean has = false;

		while (itr.hasNext()) {
			GoodsInfo g = itr.next();
			if (goodsCode.equals(g.getGoodsCode())) {
				if (state.equals("search")) {
					g.showGoodsInfo();
					has = true;
				} else if (state.equals("del")) {
					itr.remove();
					has = true;
				}
				break;
			}
		}
		return has;
	}

	void storeToFileGoods() {
		if (goodsList != null) {// ��ü� ��ü�� ���� �ִٸ�,
			Iterator<GoodsInfo> itr = goodsList.iterator();

			// �ؽü¿��� �о���� �ݺ��� ��ü ����� �Ҵ�.
			ObjectOutputStream objOut = null;
			FileOutputStream fileOut = null;// ���� ��� ó���� ���� ��ü ����.

			try {
				fileOut = new FileOutputStream(goods);
				objOut = new ObjectOutputStream(fileOut);

				while (itr.hasNext()) {
					GoodsInfo p = itr.next();
					// ��ü¿� ����� ��ü�� �ϳ��� �о
					objOut.writeObject(p);
					// ����ȭ�� ���Ϸ� ����.
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					objOut.close();// ����� ����� �ڿ� �ݱ�.
					fileOut.close();// �� �ͼ��� ó���� �ϴ°�?
					// �ڹ��� ������� �ü���� �ڿ��� �������� ����
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void storeToFileOrders() {

		if (sellList != null) {// ��ü� ��ü�� ���� �ִٸ�,
			Iterator<SellInfo> itr1 = sellList.iterator();

			// �ؽü¿��� �о���� �ݺ��� ��ü ����� �Ҵ�.
			ObjectOutputStream objOut = null;
			FileOutputStream fileOut = null;// ���� ��� ó���� ���� ��ü ����

			try {
				fileOut = new FileOutputStream(orders);
				objOut = new ObjectOutputStream(fileOut);

				while (itr1.hasNext()) {
					SellInfo p = itr1.next();
					// ��ü¿� ����� ��ü�� �ϳ��� �о
					objOut.writeObject(p);
					// ����ȭ�� ���Ϸ� ����.
				}
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					objOut.close();// ����� ����� �ڿ� �ݱ�.
					fileOut.close();// �� �ͼ��� ó���� �ϴ°�?
					// �ڹ��� ������� �ü���� �ڿ��� �������� ����.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	void readFromFileGoods() {
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;// ���� ��¿� �ʿ��� ��ü ����.

		// ������ ���ٸ� ����.
		File dir = new File("D:/DBFILE");
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		// ������ ���ٸ� ���� ����.
		File file1 = new File(goods);
		if (!file1.isFile()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			inputStream = new FileInputStream(goods);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// ���Ͽ��� �����͸� �о�ð�����, ���� ������ ������ �ƹ� ������ ����.
		try {
			if (inputStream.available() != -1) {
				// ������ ������� �ʴٸ�,
				objInputStream = new ObjectInputStream(inputStream);
				// ������ȭ �غ�

				while (inputStream.available() > 0) {// ������ ������ ���� �ƴ϶��,
					// eof : end of file
					try {
						goodsList.add((GoodsInfo) objInputStream.readObject());
						// ���Ͽ��� ��ü�� �о���� ����� ����ȯ�� ���ļ�
						// ��ü¿� �ϳ��� ��� ����.
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				objInputStream.close();

			} else {
				System.out.println("��ϵ� ��ǰ����� �����ϴ�. ����� ���ּ���.");
			}
		} catch (EOFException e) {
			System.out.println("��ϵ� ��ǰ����� �����ϴ�. ����� ���ּ���.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	void readFromFileOrders() {
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;// ���� ��¿� �ʿ��� ��ü ����.

		// ������ ���ٸ� ����.
		File dir = new File("D:/DBFILE");
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		// ������ ���ٸ� ���� ����.
		File file1 = new File(orders);
		if (!file1.isFile()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			inputStream = new FileInputStream(orders);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// ���Ͽ��� �����͸� �о�ð�����, ���� ������ ������ �ƹ� ������ ����.
		try {
			if (inputStream.available() != -1) {
				// ������ ������� �ʴٸ�,
				objInputStream = new ObjectInputStream(inputStream);
				// ������ȭ �غ�

				while (inputStream.available() > 0) {// ������ ������ ���� �ƴ϶��,
					// eof : end of file
					try {
						sellList.add((SellInfo) objInputStream.readObject());
						// ���Ͽ��� ��ü�� �о���� ����� ����ȯ�� ���ļ�
						// ��ü¿� �ϳ��� ��� ����.
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				objInputStream.close();

			} else {
				System.out.println("��ϵ� ���� ������ �����ϴ�. ����� ���ּ���.");
			}
		} catch (EOFException e) {
			System.out.println("��ϵ� ���� ������ �����ϴ�. ����� ���ּ���.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}// end class