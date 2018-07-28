package net.honeyflower.lecturing.spel2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.honeyflower.lecturing.spel2.examples.SpelConditional;

public class SpelProgram {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpelConditional spelCollections = (SpelConditional) context.getBean("spelConditional");

        // Here you can choose which bean do you want to load instead of spelConditional: spelCollections, spelLogical, etc.

        System.out.println(spelCollections);
    }

}
