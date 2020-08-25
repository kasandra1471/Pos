package PosControl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

class GoodsInfo implements Serializable {
   private static final long serialVersionUID = 1L;
   private String goodsCode; // 상품코드
   private String goodsName; // 상품명
   private int price; // 상품판매가격
   private int inventory; // 재고량
   private int sell;// 판매
   private String date; // 주문번호시간

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
            "[상품코드: " + goodsCode + ", 상품명: " + goodsName + ", 상품판매가격: " + price + ", 재고량: " + inventory + "]");

   }

   public void showsell() {
      System.out
            .println("[주문번호: " + date + ", 상품명: " + goodsName + ", 상품판매가격: " + price + ", 매출: " + sell + "원]");
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