package net.honeyflower.lecturing.spel;

import static java.util.Arrays.asList;

import java.util.List;

public class WizardHolder {
   private final Wizard wizard;
   private final Wizard[] wizards;

   public WizardHolder(Wizard wizard) {
      this.wizard = wizard;
      this.wizards = new Wizard[0];
   }

   public WizardHolder(Wizard... wizards) {
      this.wizard = null;
      this.wizards = wizards;
   }

   public Wizard getWizard() {
      return wizard;
   }

   public Wizard[] getWizards() {
      return wizards;
   }

   public List<Wizard> getWizardsList() {
      return asList(wizards);
   }
}
