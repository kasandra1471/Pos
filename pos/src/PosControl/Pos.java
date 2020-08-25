package PosControl;

import java.io.IOException;
import java.util.Scanner;

// 제너릭, 싱글톤, hashSet, 
public class Pos {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		PosManager p = PosManager.creatManagerInst();
		p.readFromFileGoods();
		p.readFromFileOrders();
		while (true) {
			Menu.showMenu();
			System.out.print("선택:");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				p.sales(); // 상품판매
				break;
			case 2:
				p.goods(); // 재고관리
				break;
			
			case 3: // 프로그램 종료
				p.storeToFileGoods();
				p.storeToFileOrders();
				System.out.println("파일을 저장하고 종료합니다.");
				sc.close();
				System.exit(0);
				
				
				break;
			default:
				try {
					throw new MyExcep(choice);
				} catch (MyExcep e) {
					// e.printStackTrace();
					// e.getMessage();
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
