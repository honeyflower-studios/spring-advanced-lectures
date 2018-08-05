package net.honeyflower.lecturing.spel;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.honeyflower.lecturing.spel.annotated.Witch;
import net.honeyflower.lecturing.spel.annotated.Wizard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:annotation-based.xml")
public class ValueAnnotationSpelTest {
   @Autowired
   private Wizard wizard;

   @Autowired
   private Witch witch;

   @BeforeClass
   public static void setup() {
      System.setProperty("WITCH_NAME", "Glynda");
      System.setProperty("WIZARD_NAME", "Gandalf");
   }

   @Test
   public void shouldWireWizardNameFromSystemEnvironment() {
      // This test requires that the environment has a variable named
      // WIZARD_NAME with a value of 'Gandalf'
      assertEquals("Gandalf", wizard.getName());
   }

   @Test
   public void shouldWireWitchNameFromSystemProperties() {
      assertEquals("Glynda", witch.getName());
   }

}
