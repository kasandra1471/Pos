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

	void sales() { // 상품 판매

		System.out.println("1. 상품 판매  \n2. 일일 매출\n3. 월별 매출\n4. 상품별 매출\n5. 총 매출 \n6. 이전 단계");
		int choice = Pos.sc.nextInt();

		switch (choice) {

		case 1: // 판매하면 재고가 차감되고 매출이 등록.
			Pos.sc.nextLine();
			System.out.println("매출을 등록합니다.");
			System.out.println("상품 코드: ");
			String goodsCode = Pos.sc.nextLine();

			System.out.println("판매 수량: ");
			int inventory = Pos.sc.nextInt();
			Pos.sc.nextLine();

			Iterator<GoodsInfo> itr = goodsList.iterator();

			while (itr.hasNext()) {
				GoodsInfo g = itr.next();

				if (goodsCode.equals(g.getGoodsCode())) {
					if (g.getInventory() < inventory) {
						System.out.println("재고가 부족합니다.");

						break;
					}
					aa = 1;
					 SellInfo si = new SellInfo(g.getDate(), g.getGoodsName(), g.getPrice(), g.getSell(), g.getGoodsCode());


					int a = g.getInventory() - inventory;

					si.setSell(g.getPrice() * inventory); // 가격 * 갯수 = 판매
					g.setInventory(a);

					sellList.add(si);
					System.out.println("주문번호: " + g.getDate() + " 상품명: " + g.getGoodsName() + " 상품판매가격: " + g.getPrice()
							+ " 수량: " + inventory + "개 입니다.");
					System.out.println("가격은 " + g.getPrice() * inventory + "원 입니다.");

					break;
				}
			}
			if (aa != 1) {
				System.out.println("찾는 물품이 없습니다.");
			}

			break;

		case 2: // 일일 매출
			System.out.println("매출 일자를 입력하세요. 예)20200101");
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
			System.out.println(day + "일 의 매출은 " + formatter.format(daysum) + "원 입니다.");
			break;
		case 3: // 월별 매출
			System.out.println("매출 월을 입력하세요. 예)202001");
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
			System.out.println(mon + "월 의 매출은 " + monsum + "원 입니다.");
			break;
		case 4:
			System.out.println("상품별 총 매출입니다.");
			System.out.print("상품 코드를 입력하세요: ");
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
			System.out.println("상품코드 " + goodsCode1 + "의 총 매출은 " + formatter.format(gsum) + "원 입니다.");
			break;

		case 5: // 총 매출
			int sum = 0;
			Iterator<SellInfo> itr2 = sellList.iterator();
			while (itr2.hasNext()) {
				SellInfo s = itr2.next();

				s.showsell();
				sum += s.getSell();

			}
			System.out.println("총 매출액은: " + formatter.format(sum) + " 원 입니다.");
			break;
		case 6: // 이전 단계
			break;
		}
	}

	void goods() { // 상품 관리 , 재고보다 많이 판매시 안내 메세지 표시하고 메뉴로 돌아감
		System.out.println("메뉴 번호를 선택 하세요  ");
		System.out.println("1. 상품등록");
		System.out.println("2. 상품검색");
		System.out.println("3. 상품수정");
		System.out.println("4. 상품삭제");
		System.out.println("5. 상품전체목록");
		System.out.println("6. 이전 메뉴");
		int choice = Pos.sc.nextInt();
		switch (choice) {

		case 1: // 상품등록

			System.out.println("상품 입력을 시작합니다.");
			System.out.print("상품코드: ");
			Pos.sc.nextLine();
			String goodsCode = Pos.sc.nextLine();
			boolean has = searchIndex(goodsCode, "search");
			if (has) {
				Iterator<GoodsInfo> itr = goodsList.iterator();
				while (true) {
					GoodsInfo g = itr.next();
					if (goodsCode.equals(g.getGoodsCode())) {
						System.out.println("이미 등록된 상품입니다.");
						System.out.println("재고를 추가 합니다.");
						System.out.println("추가 입력: ");
						int b = Pos.sc.nextInt();
						int c = g.getInventory() + b;
						g.setInventory(c);
						System.out.println("상품코드: " + goodsCode + " 상품명: " + g.getGoodsName() + " 수량: "
								+ g.getInventory() + "개 입니다.");
						break;
					}
				}

			} else {

				System.out.print("상품명: ");
				String goodsName = Pos.sc.nextLine();
				System.out.print("상품판매가격: ");
				int price = Pos.sc.nextInt();
				System.out.println("재고량: ");
				int Inventory = Pos.sc.nextInt();
				GoodsInfo gi = new GoodsInfo(goodsCode, goodsName, price, Inventory);
				goodsList.add(gi);
				System.out.println("[상품코드: " + goodsCode + ", 상품명: " + goodsName + ", 상품판매가격: " + price + ", 재고량: "
						+ Inventory + "]");
				System.out.println("재고 입력이 완료 되었습니다.");
				break;
			}
			break;
		case 2: // 상품 검색
			System.out.println("상품 검색을 시작합니다.");
			System.out.print("상품코드 : ");
			String goodsCode3 = Pos.sc.nextLine();
			boolean has2 = searchIndex(goodsCode3, "search");

			if (!has2) {
				System.out.println("찾는 값이 존재하지 않습니다. 이전단계로 이동 합니다.");
			}
			break;

		case 3: // 상품 수정
			System.out.println("변경하실 상품의 코드를 입력해 주세요");
			Pos.sc.nextLine();
			String goodsCode1 = Pos.sc.nextLine();
			boolean has1 = searchIndex(goodsCode1, "search");
			if (has1) {
				Iterator<GoodsInfo> itr1 = goodsList.iterator();
				while (itr1.hasNext()) {
					GoodsInfo g = itr1.next();
					if (goodsCode1.equals(g.getGoodsCode())) {
						System.out.println("변경하실 코드를 입력해 주세요.");
						String goodsCode2 = Pos.sc.nextLine();
						g.setGoodsCode(goodsCode2);
						System.out.println("변경하실 상품명을 입력해 주세요.");
						String goodsName = Pos.sc.nextLine();
						g.setGoodsName(goodsName);
						System.out.println("변경하실 상품판매가격을 입력해 주세요.");
						int price = Pos.sc.nextInt();
						g.setPrice(price);
						System.out.println("변경하실 재고량을 입력해 주세요.");
						int inventory = Pos.sc.nextInt();
						g.setInventory(inventory);

						System.out.println("상품코드: " + goodsCode2 + " 상품명: " + g.getGoodsName() + "상품판매가격: "
								+ g.getPrice() + ", 재고량: " + g.getInventory() + "]");
						break;
					}
				}
			} else {

				System.out.println("코드를 확인하여 주십시오.");
				break;
			}

			break;
		case 4: // 상품 삭제
			System.out.println("상품 삭제를 시작합니다");
			System.out.print("상품코드 : ");
			Pos.sc.nextLine();
			String goodsCode4 = Pos.sc.nextLine();
			boolean has3 = searchIndex(goodsCode4, "search");

			if (!has3) {
				System.out.println("찾는 값이 존재하지 않습니다. 전단계로 이동 합니다.");
			} else {
				String b = "y";
				String c = "n";
				System.out.println("삭제 하시겠습니까? (영문소문자) y / n");
				String a = Pos.sc.nextLine();

				if (a.equals(b)) {
					has3 = searchIndex(goodsCode4, "del");
					System.out.println("상품코드 " + goodsCode4 + "번 상품을 삭제했습니다.");
				} else if (a.equals(c)) {
					System.out.println("삭제 하지 않습니다. 이전단계로 이동 합니다.");
				} else {
					System.out.println("이전단계로 이동합니다.");
					break;
				}
			}

			break;
		case 5: // 재고목록
			Iterator<GoodsInfo> itr = goodsList.iterator();
			while (itr.hasNext()) {
				GoodsInfo p = itr.next();
				p.showGoodsInfo();
			}
			break;
		case 6: {
			System.out.println("이전메뉴로 돌아갑니다.");
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
		if (goodsList != null) {// 헤시셋 객체가 값이 있다면,
			Iterator<GoodsInfo> itr = goodsList.iterator();

			// 해시셋에서 읽어오는 반복자 객체 선언과 할당.
			ObjectOutputStream objOut = null;
			FileOutputStream fileOut = null;// 파일 출력 처리를 위한 객체 선언.

			try {
				fileOut = new FileOutputStream(goods);
				objOut = new ObjectOutputStream(fileOut);

				while (itr.hasNext()) {
					GoodsInfo p = itr.next();
					// 헤시셋에 저장된 객체를 하나씩 읽어서
					objOut.writeObject(p);
					// 직렬화후 파일로 저장.
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					objOut.close();// 사용한 입출력 자원 닫기.
					fileOut.close();// 왜 익셉션 처리를 하는가?
					// 자바의 입출력은 운영체제의 자원을 빌려쓰는 입장
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void storeToFileOrders() {

		if (sellList != null) {// 헤시셋 객체가 값이 있다면,
			Iterator<SellInfo> itr1 = sellList.iterator();

			// 해시셋에서 읽어오는 반복자 객체 선언과 할당.
			ObjectOutputStream objOut = null;
			FileOutputStream fileOut = null;// 파일 출력 처리를 위한 객체 선언

			try {
				fileOut = new FileOutputStream(orders);
				objOut = new ObjectOutputStream(fileOut);

				while (itr1.hasNext()) {
					SellInfo p = itr1.next();
					// 헤시셋에 저장된 객체를 하나씩 읽어서
					objOut.writeObject(p);
					// 직렬화후 파일로 저장.
				}
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					objOut.close();// 사용한 입출력 자원 닫기.
					fileOut.close();// 왜 익셉션 처리를 하는가?
					// 자바의 입출력은 운영체제의 자원을 빌려쓰는 입장.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	void readFromFileGoods() {
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;// 파일 출력에 필요한 객체 선언.

		// 폴더가 없다면 생성.
		File dir = new File("D:/DBFILE");
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		// 파일이 없다면 파일 생성.
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
		// 파일에서 데이터를 읽어올거지만, 최초 생성된 파일은 아무 내용이 없음.
		try {
			if (inputStream.available() != -1) {
				// 파일이 비어있지 않다면,
				objInputStream = new ObjectInputStream(inputStream);
				// 역직렬화 준비

				while (inputStream.available() > 0) {// 파일의 내용이 끝이 아니라면,
					// eof : end of file
					try {
						goodsList.add((GoodsInfo) objInputStream.readObject());
						// 파일에서 객체를 읽어오고 명시적 형변환을 거쳐서
						// 헤시셋에 하나씩 담고 있음.
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				objInputStream.close();

			} else {
				System.out.println("등록된 상품목록이 없습니다. 등록을 해주세요.");
			}
		} catch (EOFException e) {
			System.out.println("등록된 상품목록이 없습니다. 등록을 해주세요.");
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
		FileInputStream inputStream = null;// 파일 출력에 필요한 객체 선언.

		// 폴더가 없다면 생성.
		File dir = new File("D:/DBFILE");
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		// 파일이 없다면 파일 생성.
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
		// 파일에서 데이터를 읽어올거지만, 최초 생성된 파일은 아무 내용이 없음.
		try {
			if (inputStream.available() != -1) {
				// 파일이 비어있지 않다면,
				objInputStream = new ObjectInputStream(inputStream);
				// 역직렬화 준비

				while (inputStream.available() > 0) {// 파일의 내용이 끝이 아니라면,
					// eof : end of file
					try {
						sellList.add((SellInfo) objInputStream.readObject());
						// 파일에서 객체를 읽어오고 명시적 형변환을 거쳐서
						// 헤시셋에 하나씩 담고 있음.
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				objInputStream.close();

			} else {
				System.out.println("등록된 매출 내역이 없습니다. 등록을 해주세요.");
			}
		} catch (EOFException e) {
			System.out.println("등록된 매출 내역이 없습니다. 등록을 해주세요.");
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