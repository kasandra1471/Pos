package PosControl;

import java.io.IOException;
import java.util.Scanner;

// ���ʸ�, �̱���, hashSet, 
public class Pos {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		PosManager p = PosManager.creatManagerInst();
		p.readFromFileGoods();
		p.readFromFileOrders();
		while (true) {
			Menu.showMenu();
			System.out.print("����:");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				p.sales(); // ��ǰ�Ǹ�
				break;
			case 2:
				p.goods(); // ������
				break;
			
			case 3: // ���α׷� ����
				p.storeToFileGoods();
				p.storeToFileOrders();
				System.out.println("������ �����ϰ� �����մϴ�.");
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
