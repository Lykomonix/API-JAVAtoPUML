import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

public class PumlDoclet implements Doclet {

    private String pumlPATH = "./test.puml";
    private FileWriter writer;

    @Override
    public void init(Locale locale, Reporter reporter) {
        InitPumlFile();
    }

    @Override
    public String getName() {return getClass().getSimpleName();}

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return null;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment)
    {
        RetrieveClasses(environment);
        ClosePumlFile();
        return true;
    }

    private void InitPumlFile()
    {
        try
        {
            writer = new FileWriter(pumlPATH);
            writer.write("@startuml\n\n" +
                    "skinparam style strictuml\n" +
                    "skinparam classAttributeIconSize 0\n" +
                    "skinparam classFontStyle Bold\n\n");
        }
        catch (IOException ex)
        {
            System.out.println("The file cannot be accessed !");
        }
    }

    private void ClosePumlFile()
    {
        try
        {
            writer.append("@enduml\n");
            writer.close();
        }
        catch (IOException ex)
        {
            System.out.println("The file cannot be accessed !");
        }
    }

    private void RetrieveClasses(DocletEnvironment environment)
    {
        try
        {
            for(Element element : environment.getSpecifiedElements())
            {
                writer.append("class " + element.getSimpleName() + "\n");
            }
        }
        catch (IOException ex)
        {
            System.out.println("The file cannot be accessed !");
        }
    }
}
