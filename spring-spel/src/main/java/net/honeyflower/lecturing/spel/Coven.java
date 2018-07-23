package net.honeyflower.lecturing.spel;

import java.util.Arrays;
import java.util.List;

public class Coven {
   private final Witch witch;
   private final Witch[] witches;

   public Coven(Witch witch) {
      this.witch = witch;
      this.witches = new Witch[0];
   }

   public Coven(Witch... witches) {
      this.witch = null;
      this.witches = witches;
   }

   public Witch getWitch() {
      return witch;
   }

   public Witch[] getWitches() {
      return witches;
   }

   public List<Witch> getWitchesList() {
      return Arrays.asList(witches);
   }

}
