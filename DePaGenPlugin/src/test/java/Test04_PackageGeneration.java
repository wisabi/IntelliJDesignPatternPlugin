import com.DesignPatternFactory.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.fail;

public class Test04_PackageGeneration {


    @Test
    public void AbstractFactoryTest01(){
        AbstractFactory abstractFactory = new AbstractFactory();

        abstractFactory.setInterfaceName("TestInterface");

        abstractFactory.setMethodNames("meth01");
        abstractFactory.setMethodNames("meth02");
        abstractFactory.setMethodNames("meth03");

        abstractFactory.setClassNames("Class01");
        abstractFactory.setClassNames("Class02");
        abstractFactory.setClassNames("Class03");

        abstractFactory.buildPattern();
        String code = abstractFactory.printCodeString();

       String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/AbstractFactory.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            //Assert.assertTrue(generatedString.equals(code));
            Assert.assertEquals(generatedString, code);
        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void FactoryTest02(){
        Factory factory = new Factory();

        factory.setInterfaceName("TestInterface");

        factory.setAbstractMethods("meth01");
        factory.setAbstractMethods("meth02");
        factory.setAbstractMethods("meth03");

        factory.setConcreteClassNames("Class01");
        factory.setConcreteClassNames("Class02");
        factory.setConcreteClassNames("Class03");

        factory.buildPattern();
        String code = factory.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Factory.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            //Assert.assertTrue(generatedString.equals(code));
            Assert.assertEquals(generatedString, code);
        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void BuilderTest03(){

        Builder builder = new Builder();

        builder.setDirectorName("DirectorClass");

        builder.setBuilderInterfaceName("BuilderInterface");

        builder.setBuilderInterfaceMethodNames("BuilderInterfaceMethod01");
        builder.setBuilderInterfaceMethodNames("BuilderInterfaceMethod02");
        builder.setBuilderInterfaceMethodNames("BuilderInterfaceMethod03");

        builder.setConcreteBuilderNames("ConcreteBuilder01");
        builder.setConcreteBuilderNames("ConcreteBuilder02");
        builder.setConcreteBuilderNames("ConcreteBuilder03");

        builder.setComplexObjectName("ComplexObject");

        builder.setTopProductInterfaceName("TopInterfaceName");

        builder.setProductInterfaceNames("ProductInterface01");
        builder.setProductInterfaceNames("ProductInterface02");
        builder.setProductInterfaceNames("ProductInterface03");

        builder.buildPattern();
        String code = builder.printCodeString();

        String generatedString = "";


        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Builder.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            Assert.assertEquals(generatedString, code);

        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void FacadeTest04(){
        Facade facade = new Facade();

        facade.setAbstractFacadeClassName("TestAbstractClass");

        facade.setFacadeClassName("Class01");
        facade.setFacadeClassName("Class02");
        facade.setFacadeClassName("Class03");

        facade.setForwardedClassesNames("ForwardedClass01");
        facade.setForwardedClassesNames("ForwardedClass02");
        facade.setForwardedClassesNames("ForwardedClass03");

        facade.buildPattern();
        String code = facade.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Facade.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            //Assert.assertTrue(generatedString.equals(code));
            Assert.assertEquals(generatedString, code);
        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void ChainOfResponsibilityTest05(){
        Chain chain = new Chain();

        chain.setAbstractHandlerClassName("TestAbstractClass");

        chain.setReceiverClassNames("Class01");
        chain.setReceiverClassNames("Class01");
        chain.setReceiverClassNames("Class01");

        chain.setFinalReceiverClassName("FinalClass01");

        chain.buildPattern();
        String code = chain.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Chain.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            Assert.assertEquals(generatedString, code);

        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void MediatorTest06(){
        Mediator mediator = new Mediator();

        mediator.setMediatorAbstractClassName("TestAbstractClass");

        mediator.setMediatorNames("Class01");
        mediator.setMediatorNames("Class01");
        mediator.setMediatorNames("Class01");

        mediator.setColleagueNames("ColleagueClass01");
        mediator.setColleagueNames("ColleagueClass02");
        mediator.setColleagueNames("ColleagueClass03");

        mediator.buildPattern();
        String code = mediator.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Mediator.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            Assert.assertEquals(generatedString, code);

        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void VisitorTest07(){
        Visitor visitor = new Visitor();

        visitor.setElementInterfaceStr("TestInterface");

        visitor.setElementMethods("meth01");
        visitor.setElementMethods("meth02");
        visitor.setElementMethods("meth03");

        visitor.setConcreteElements("ConcreteClass01");
        visitor.setConcreteElements("ConcreteClass02");
        visitor.setConcreteElements("ConcreteClass03");

        visitor.setVisitorInterfaceStr("VisitorInterface");

        visitor.setConcreteVisitors("ConcreteVisitorClass01");
        visitor.setConcreteVisitors("ConcreteVisitorClass02");
        visitor.setConcreteVisitors("ConcreteVisitorClass03");

        visitor.buildPattern();
        String code = visitor.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Visitor.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            Assert.assertEquals(generatedString, code);

        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }

    @Test
    public void TemplateTest08(){

        Template template = new Template();

        template.setAbstractClassName("AbstractClass");

        template.setAbstractMethods("meth01");
        template.setAbstractMethods("meth02");
        template.setAbstractMethods("meth03");

        template.setFinalMethods("finalMethod01");
        template.setFinalMethods("finalMethod02");
        template.setFinalMethods("finalMethod03");

        template.setConcreteClasses("ConcreteClass01");
        template.setConcreteClasses("ConcreteClass02");
        template.setConcreteClasses("ConcreteClass03");

        template.buildPattern();
        String code = template.printCodeString();

        String generatedString = "";

        try {
            File file = new File( System.getProperty("user.dir") +"/src/test/resources/Template.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                generatedString = generatedString + myReader.nextLine() + "\n";
            }
            System.out.println(generatedString);
            myReader.close();
            Assert.assertEquals(generatedString, code);

        }
        catch (Exception e){
            System.out.println(generatedString);
            fail();
        }
    }
}
