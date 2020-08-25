package PosControl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SellInfo implements Serializable {
   private String goodsCode; // 상품코드
   private String date;
   private String goodsName;
   private int price;
   private int sell;

   public SellInfo(String date, String goodsName, int price, int sell, String goodsCode) {
      super();
      this.goodsCode = goodsCode;
      this.date = date;
      this.goodsName = goodsName;
      this.price = price;
      this.sell = sell;
   }

   public String getDate() {
      long systemTime = System.currentTimeMillis();

      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

      date = formatter.format(systemTime);
      return date;

   }

   public String getDate1() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
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
      this.price = price;
   }

   public int getSell() {
      return sell;
   }

   public void setSell(int sell) {
      this.sell = sell;
   }

   public void showsell() {
      System.out.println("[주문번호: " + date + ", 상품명: " + goodsName + ", 상품판매가격: " + price + ", 매출: " + sell + "원]");
   }

   public String getGoodsCode() {
      return goodsCode;
   }

   public void setGoodsCode(String goodsCode) {
      this.goodsCode = goodsCode;
   }

}