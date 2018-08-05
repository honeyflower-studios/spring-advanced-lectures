package net.honeyflower.lecturing.spel.annotated;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Wizard {
   @Value("#{systemProperties['WIZARD_NAME']}")
   private String name;

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
