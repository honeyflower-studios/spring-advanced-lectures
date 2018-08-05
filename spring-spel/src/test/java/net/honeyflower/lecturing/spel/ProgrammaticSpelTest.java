package net.honeyflower.lecturing.spel;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ProgrammaticSpelTest {
   @Test
   public void shouldEvaluateLiteralExpressions() throws Exception {
      assertEquals("abracadabra", evaluateSimpleExpression("'abracadabra'"));
      assertEquals(42, evaluateSimpleExpression("42"));
      assertEquals(3.1415926, evaluateSimpleExpression("3.1415926"));
      assertEquals(10000.0, evaluateSimpleExpression("1e4"));
      assertTrue(evaluateBooleanExpression("true"));
      assertFalse(evaluateBooleanExpression("false"));
      assertNull(evaluateSimpleExpression("null"));
   }

   @Test
   public void shouldEvaluateUseOfConstructorInExpression() throws Exception {
      assertEquals(
            "Gandalf",
            evaluateSimpleExpression("new net.honeyflower.lecturing.spel.Wizard('Gandalf').name"));
      assertEquals("hokus pokus",
            evaluateSimpleExpression("new String('hokus pokus')"));
   }

   @Test
   public void shouldEvaluateAccessToObjectProperty() throws Exception {
      Elf elf = new Elf("Legolas");

      assertEquals("Legolas", evaluateExpressionWithRootObject(elf,
            "#this.name"));

      // case insensitive on 1st letter
      assertEquals("Legolas", evaluateExpressionWithRootObject(elf,
            "#this.Name"));

      // through getter
      assertEquals("Legolas", evaluateExpressionWithRootObject(elf,
            "#this.getName()"));
   }

   @Test
   public void shouldEvaluateAccessToObjectMethod() throws Exception {
      KeeblerElf elf = new KeeblerElf("Ernie");

      assertEquals("Chips Deluxe", evaluateExpressionWithRootObject(elf,
            "#this.makeCookies()"));
   }

   @Test
   public void shouldEvaluateTemplatedExpression() throws Exception {
      Wizard wizard = new Wizard("Gandalf");

      assertEquals("The wizard's name is Gandalf", evaluateTemplatedExpression(
            wizard, "The wizard's name is ${name}"));
   }

   @Test
   public void shouldEvaluateArithmeticExpressions() throws Exception {
      assertEquals("Harry Potter",
            evaluateSimpleExpression("'Harry' + ' ' + 'Potter'"));
      assertEquals(77, evaluateSimpleExpression("25 + 52"));
      assertEquals(4.21, evaluateSimpleExpression("2.19 + 2.02"));
      assertEquals(52, evaluateSimpleExpression("77 - 25"));
      assertEquals(42, evaluateSimpleExpression("6 * 7"));
      assertEquals(7, evaluateSimpleExpression("42 / 6"));
      assertEquals(2, evaluateSimpleExpression("44 % 6"));
   }

   @Test
   public void shouldEvaluateRelationalOperations() throws Exception {
      assertTrue(evaluateBooleanExpression("42 > 7"));
      assertFalse(evaluateBooleanExpression("7 > 42"));
      assertTrue(evaluateBooleanExpression("7 < 42"));
      assertFalse(evaluateBooleanExpression("42 < 7"));
      assertTrue(evaluateBooleanExpression("42 == 42"));
      assertFalse(evaluateBooleanExpression("42 == 24"));

      assertTrue(evaluateBooleanExpression("'Apple' < 'Orange'"));
      assertFalse(evaluateBooleanExpression("'Orange' < 'Apple'"));
      assertTrue(evaluateBooleanExpression("'Orange' > 'Apple'"));
      assertFalse(evaluateBooleanExpression("'Apple' > 'Orange'"));
      assertTrue(evaluateBooleanExpression("'Apple' == 'Apple'"));
      assertFalse(evaluateBooleanExpression("'Apple' == 'Orange'"));
      assertFalse(evaluateBooleanExpression("'Apple' == 'apple'"));
   }

   @Test
   public void shouldEvaluateLogicalOperatorsWithPrimitives() throws Exception {
      assertTrue(evaluateBooleanExpression("true and true"));
      assertFalse(evaluateBooleanExpression("true and false"));
      assertFalse(evaluateBooleanExpression("false and true"));
      assertFalse(evaluateBooleanExpression("false and false"));

      assertTrue(evaluateBooleanExpression("true or false"));
      assertTrue(evaluateBooleanExpression("false or true"));
      assertTrue(evaluateBooleanExpression("true or true"));
      assertFalse(evaluateBooleanExpression("false or false"));

      assertTrue(evaluateBooleanExpression("!false"));
      assertFalse(evaluateBooleanExpression("!true"));
   }

   @Test
   public void shouldEvaluateLogicalOperatorsWithObjects() throws Exception {
      Witch wickedWitch = new Witch();
      wickedWitch.setName("Elfaba");
      wickedWitch.setWicked(true);

      Witch goodWitch = new Witch();
      goodWitch.setName("Tattypoo");
      goodWitch.setWicked(false);

      Coven wickedWitchHolder = new Coven(wickedWitch);
      Coven goodWitchHolder = new Coven(goodWitch);

      assertTrue(evaluateBooleanExpressionWithRootObject(wickedWitchHolder,
            "witch.isWicked() and witch.name == 'Elfaba'"));
      assertFalse(evaluateBooleanExpressionWithRootObject(wickedWitchHolder,
            "witch.isWicked() and witch.name == 'Tattypoo'"));
      assertFalse(evaluateBooleanExpressionWithRootObject(goodWitchHolder,
            "witch.isWicked() and witch.name == 'Elfaba'"));
      assertFalse(evaluateBooleanExpressionWithRootObject(goodWitchHolder,
            "witch.isWicked() and witch.name == 'Elfaba'"));

      assertFalse(evaluateBooleanExpressionWithRootObject(goodWitchHolder,
            "witch.isWicked() or witch.name == 'Elfaba'"));
      assertTrue(evaluateBooleanExpressionWithRootObject(wickedWitchHolder,
            "witch.isWicked() or witch.name == 'Elfaba'"));
      assertTrue(evaluateBooleanExpressionWithRootObject(wickedWitchHolder,
            "witch.isWicked() or witch.name == 'Tattypoo'"));
      assertTrue(evaluateBooleanExpressionWithRootObject(goodWitchHolder,
            "witch.isWicked() or witch.name == 'Tattypoo'"));

      assertTrue(evaluateBooleanExpressionWithRootObject(goodWitchHolder,
            "!witch.isWicked()"));
      assertFalse(evaluateBooleanExpressionWithRootObject(wickedWitchHolder,
            "!witch.isWicked()"));
   }

   @Test
   public void shouldEvaluateTernaryOperator() throws Exception {
      Wizard gandalf = new Wizard("Gandalf");
      gandalf.setGood(true);
      WizardHolder gandalfHolder = new WizardHolder(gandalf);

      Wizard saruman = new Wizard("Saruman");
      saruman.setGood(false);
      WizardHolder sarumanHolder = new WizardHolder(saruman);

      assertEquals("Gandalf", evaluateExpressionWithRootObject(gandalfHolder,
            "wizard.isGood() ? 'Gandalf' : 'Saruman'"));

      assertEquals("Saruman", evaluateExpressionWithRootObject(sarumanHolder,
            "wizard.isGood() ? 'Gandalf' : 'Saruman'"));
   }

   @Test
   public void shouldEvaluateElvisOperator() throws Exception {
      Wizard gandalf = new Wizard("Gandalf");
      WizardHolder gandalfHolder = new WizardHolder(gandalf);

      Wizard nullWizard = new Wizard(null);
      WizardHolder nullWizardHolder = new WizardHolder(nullWizard);

      assertEquals("Gandalf", evaluateExpressionWithRootObject(gandalfHolder,
            "wizard.name ?: 'unknown'"));
      assertEquals("unknown", evaluateExpressionWithRootObject(
            nullWizardHolder, "wizard.name ?: 'unknown'"));
   }

   @Test
   public void shouldEvaluateTypeOperator() throws Exception {
      assertEquals(String.class, evaluateSimpleExpression("T(String)"));
      assertEquals(Math.class, evaluateSimpleExpression("T(Math)"));
      assertEquals(int.class, evaluateSimpleExpression("T(int)"));
      assertEquals(Date.class, evaluateSimpleExpression("T(java.util.Date)"));
   }

   @Test
   public void shouldEvaluateAccessToClassMethod() throws Exception {
      assertEquals(42.0, evaluateSimpleExpression("T(Math).floor(42.56)"));
   }

   @Test
   public void shouldEvaluateAccessToClassConstant() throws Exception {
      assertEquals(Math.PI, evaluateSimpleExpression("T(Math).PI"));
   }

   @Test
   public void shouldEvaluateInstanceOf() throws Exception {
      assertTrue(evaluateBooleanExpression("'Sabrina' instanceof T(String)"));
      assertTrue(evaluateBooleanExpression("123 instanceof T(Integer)"));
      assertTrue(evaluateBooleanExpression("123L instanceof T(Long)"));
      assertTrue(evaluateBooleanExpression("1.23 instanceof T(Double)"));
      assertTrue(evaluateBooleanExpression("true instanceof T(Boolean)"));
   }

   @Test
   public void shouldEvaluateRegularExpressions() throws Exception {
      assertTrue(evaluateBooleanExpression("'972-555-1234' matches '\\d{3}-\\d{3}-\\d{4}'"));
      assertTrue(evaluateBooleanExpression("'(972)555-1234' matches '\\(\\d{3}\\)\\d{3}-\\d{4}'"));
      assertFalse(evaluateBooleanExpression("'972.555.1234' matches '\\(\\d{3}\\)\\d{3}-\\d{4}'"));
      assertTrue(evaluateBooleanExpression("'craig@habuma.com' matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}'"));
      assertTrue(evaluateBooleanExpression("'http://www.springinaction.com' matches 'http://www\\.[a-zA-Z0-9]*\\.(com|edu|net)'"));
      assertFalse(evaluateBooleanExpression("'https://www.springinaction.com' matches 'http://www\\.[a-zA-Z0-9]*\\.(com|edu|net)'"));
      assertFalse(evaluateBooleanExpression("'http://blog.springinaction.com' matches 'http://www\\.[a-zA-Z0-9]*\\.(com|edu|net)'"));
   }

   @Test
   public void shouldSetVariableOnRootObject() throws Exception {
      Witch witch = new Witch();
      evaluateExpressionWithRootObject(witch, "name = 'Broomhilda'");
      assertEquals("Broomhilda", witch.getName());
   }

   @Test
   public void shouldEvaluateArrayAndListMembersByIndex() throws Exception {
      WizardHolder wizardHolder = new WizardHolder(new Wizard("Harry"),
            new Wizard("Gandalf"), new Wizard("Merlin"));

      assertEquals("Harry", evaluateExpressionWithRootObject(wizardHolder,
            "wizards[0].name"));
      assertEquals("Gandalf", evaluateExpressionWithRootObject(wizardHolder,
            "wizards[1].name"));
      assertEquals("Merlin", evaluateExpressionWithRootObject(wizardHolder,
            "wizards[2].name"));

      assertEquals("Harry", evaluateExpressionWithRootObject(wizardHolder,
            "wizardsList[0].name"));
      assertEquals("Gandalf", evaluateExpressionWithRootObject(wizardHolder,
            "wizardsList[1].name"));
      assertEquals("Merlin", evaluateExpressionWithRootObject(wizardHolder,
            "wizardsList[2].name"));
   }

   @Test
   public void shouldEvaluateMapMembersByKey() throws Exception {
      Map<String, String> magicalPeople = new HashMap<String, String>();
      magicalPeople.put("Harry", "Wizard");
      magicalPeople.put("Sabrina", "Witch");
      magicalPeople.put("Merlin", "Wizard");
      magicalPeople.put("Houdini", "Magician");

      assertEquals("Wizard", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Harry']"));
      assertEquals("Wizard", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Merlin']"));
      assertEquals("Witch", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Sabrina']"));
      assertEquals("Magician", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Houdini']"));
   }

   @Test
   public void shouldEvaluatePropertyMembersByKey() throws Exception {
      Properties magicalPeople = new Properties();
      magicalPeople.put("Harry", "Wizard");
      magicalPeople.put("Sabrina", "Witch");
      magicalPeople.put("Merlin", "Wizard");
      magicalPeople.put("Houdini", "Magician");

      assertEquals("Wizard", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Harry']"));
      assertEquals("Wizard", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Merlin']"));
      assertEquals("Witch", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Sabrina']"));
      assertEquals("Magician", evaluateExpressionWithRootObject(magicalPeople,
            "#this['Houdini']"));
   }

   @Test
   public void shouldEvaluateSafeNavigationOperator() throws Exception {
      assertEquals("GANDALF",
            evaluateSimpleExpression("'Gandalf'?.toUpperCase()"));
      assertEquals(null, evaluateSimpleExpression("null?.toUpperCase()"));
   }

   @Test
   public void shouldEvaluateVariableOnRootObject() throws Exception {
      Wizard wizard = new Wizard("Merlin");
      assertEquals("Merlin", evaluateExpressionWithRootObject(wizard, "name"));
   }

   @Test
   public void shouldEvaluateSelectionExpressions() throws Exception {
      Witch northWitch = new Witch();
      northWitch.setName("Tattypoo");
      northWitch.setWicked(false);

      Witch westWitch = new Witch();
      westWitch.setName("Elfaba");
      westWitch.setWicked(true);

      Witch southWitch = new Witch();
      southWitch.setName("Glinda");
      southWitch.setWicked(false);

      Witch eastWitch = new Witch();
      eastWitch.setName("unknown");
      eastWitch.setWicked(true);

      Coven coven = new Coven(northWitch, westWitch, southWitch, eastWitch);

      assertEquals(
            asList(westWitch, eastWitch),
            evaluateExpressionWithRootObject(coven, "witchesList.?[isWicked()]"));

      assertEquals(westWitch, evaluateExpressionWithRootObject(coven,
            "witchesList.^[isWicked()]"));

      assertEquals(eastWitch, evaluateExpressionWithRootObject(coven,
            "witchesList.$[isWicked()]"));
   }

   @Test
   public void shouldEvaluateProjectionExpressions() throws Exception {
      Witch northWitch = new Witch();
      northWitch.setName("Tattypoo");
      northWitch.setWicked(false);

      Witch westWitch = new Witch();
      westWitch.setName("Elfaba");
      westWitch.setWicked(true);

      Witch southWitch = new Witch();
      southWitch.setName("Glinda");
      southWitch.setWicked(false);

      Witch eastWitch = new Witch();
      eastWitch.setName("unknown");
      eastWitch.setWicked(true);

      Coven coven = new Coven(northWitch, westWitch, southWitch, eastWitch);

      assertEquals(asList("Tattypoo", "Elfaba", "Glinda", "unknown"),
            evaluateExpressionWithRootObject(coven, "witchesList.![name]"));
   }

   @Test
   public void shouldEvaluateThisVariable() throws Exception {
      List<String> wizardNames = asList("Harry", "Gandalf", "Merlin");
      assertEquals("Merlin", evaluateExpressionWithRootObject(wizardNames,
            "#this.^[#this > 'M']"));
   }

   @Test
   public void shouldEvaluateExpressionWithCustomFunction() throws Exception {
      Method customFunctionMethod = ProgrammaticSpelTest.class
            .getDeclaredMethod("numberToString", int.class);
      assertEquals("zero", evaluateExpressionWithCustomFunction("numberToWord",
            customFunctionMethod, "#numberToWord(0)"));
      assertEquals("five", evaluateExpressionWithCustomFunction("numberToWord",
            customFunctionMethod, "#numberToWord(5)"));
      assertEquals("I don't know!", evaluateExpressionWithCustomFunction(
            "numberToWord", customFunctionMethod, "#numberToWord(56)"));
   }

   static String numberToString(int i) {
      String[] numberWords = new String[] { "zero", "one", "two", "three",
            "four", "five", "six", "seven", "eight", "nine", "ten" };
      if (i > numberWords.length - 1) {
         return "I don't know!";
      }

      return numberWords[i];
   }

   private Object evaluateSimpleExpression(String expressionString)
         throws ParseException, EvaluationException {
      return evaluateExpression(expressionString).getValue();
   }

   private Object evaluateExpressionWithRootObject(Object root,
         String expressionString) throws ParseException, EvaluationException {
      EvaluationContext context = new StandardEvaluationContext(root);
      Expression expression = evaluateExpression(expressionString);
      return expression.getValue(context);
   }

   private Object evaluateExpressionWithCustomFunction(String functionName,
         Method method, String expressionString) throws ParseException,
         EvaluationException {
      StandardEvaluationContext context = new StandardEvaluationContext();
      context.registerFunction(functionName, method);
      Expression expression = evaluateExpression(expressionString);
      return expression.getValue(context);
   }

   private Object evaluateTemplatedExpression(Object rootObject,
         String expressionString) throws ParseException, EvaluationException {
      StandardEvaluationContext context = new StandardEvaluationContext(
            rootObject);
      ParserContext parserContext = new ParserContext() {
         public String getExpressionPrefix() {
            return "${";
         }

         public String getExpressionSuffix() {
            return "}";
         }

         public boolean isTemplate() {
            return true;
         }
      };

      ExpressionParser parser = new SpelExpressionParser();
      Expression expression = parser.parseExpression(expressionString,
            parserContext);
      return expression.getValue(context);
   }

   private boolean evaluateBooleanExpression(String expressionString)
         throws ParseException, EvaluationException {
      return evaluateExpression(expressionString).getValue(boolean.class);
   }

   private boolean evaluateBooleanExpressionWithRootObject(Object rootObject,
         String expressionString) throws ParseException, EvaluationException {
      EvaluationContext context = new StandardEvaluationContext(rootObject);
      Expression expression = evaluateExpression(expressionString);
      return expression.getValue(context, boolean.class);
   }

   private Expression evaluateExpression(String expressionString)
         throws ParseException {
      ExpressionParser parser = new SpelExpressionParser();
      Expression expression = parser.parseExpression(expressionString);
      return expression;
   }
}
