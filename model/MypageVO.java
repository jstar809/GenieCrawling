package model;

public class MypageVO {
   private int mynum;
   private int gnum;
   private int membernum;
   public int getMynum() {
      return mynum;
   }
   public void setMynum(int mynum) {
      this.mynum = mynum;
   }
   public int getGnum() {
      return gnum;
   }
   public void setGnum(int gnum) {
      this.gnum = gnum;
   }
   public int getMembernum() {
      return membernum;
   }
   public void setMembernum(int membernum) {
      this.membernum = membernum;
   }
   @Override
   public String toString() {
      return "MypageVO [mynum=" + mynum + ", gnum=" + gnum + ", membernum=" + membernum + "]";
   }
   
   

}