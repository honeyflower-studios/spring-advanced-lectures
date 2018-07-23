package net.honeyflower.lecturing.spel;

public class Wizard {
   private String name;
   private boolean good;

   public Wizard(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setGood(boolean good) {
      this.good = good;
   }

   public boolean isGood() {
      return good;
   }
}
