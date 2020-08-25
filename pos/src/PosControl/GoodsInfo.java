package PosControl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

class GoodsInfo implements Serializable {
   private static final long serialVersionUID = 1L;
   private String goodsCode; // ��ǰ�ڵ�
   private String goodsName; // ��ǰ��
   private int price; // ��ǰ�ǸŰ���
   private int inventory; // ���
   private int sell;// �Ǹ�
   private String date; // �ֹ���ȣ�ð�

   public GoodsInfo(String goodsCode, String goodsName, int price, int inventory) {

      this.goodsCode = goodsCode;
      this.goodsName = goodsName;
      this.price = price;
      this.inventory = inventory;
      this.sell = sell;
      this.date = date;

   }

   public String getDate() {
       long systemTime = System.currentTimeMillis();

         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

         date = formatter.format(systemTime);
      return date;
   }

   public Object getName() {
      // TODO Auto-generated method stub
      return null;
   }

   public int getSell() {
      return sell;
   }

   public void setSell(int sell) {

      this.sell = sell;
   }

   public String getGoodsName() {
      return goodsName;
   }

   public void setGoodsName(String goodsName) {
      this.goodsName = goodsName;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      price = price;
   }

   public int getInventory() {
      return inventory;
   }

   public void setInventory(int inventory) {
      this.inventory = inventory;
   }

   public String getGoodsCode() {
      return goodsCode;
   }

   public void setGoodsCode(String goodsCode) {
      this.goodsCode = goodsCode;
   }

   public void showGoodsInfo() {
      System.out.println(
            "[��ǰ�ڵ�: " + goodsCode + ", ��ǰ��: " + goodsName + ", ��ǰ�ǸŰ���: " + price + ", ���: " + inventory + "]");

   }

   public void showsell() {
      System.out
            .println("[�ֹ���ȣ: " + date + ", ��ǰ��: " + goodsName + ", ��ǰ�ǸŰ���: " + price + ", ����: " + sell + "��]");
   }

   @Override
   public int hashCode() {
      // TODO Auto-generated method stub
      return 1;
   }

   @Override
   public boolean equals(Object obj) {
      GoodsInfo n = (GoodsInfo) obj;
      if (n.goodsCode.equals(goodsCode) || n.goodsCode.equals(goodsCode))
         return true;
      else
         return false;
   }

}