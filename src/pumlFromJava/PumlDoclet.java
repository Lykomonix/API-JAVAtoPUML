import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class PumlDoclet implements Doclet {
    private Set<Option> options;
    private String pumlPATH;
    private FileWriter writer;

    @Override
    public void init(Locale locale, Reporter reporter) {
        options = Set.of(new OutOption());
    }

    @Override
    public String getName() {return getClass().getSimpleName();}

    @Override
    public Set<? extends Option> getSupportedOptions() {

        return options;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment)
    {
        InitPumlFile();
        RetrieveClasses(environment);
        ClosePumlFile();
        return true;
    }

    private void InitPumlFile()
    {
        try
        {
            if(pumlPATH == null)
            {
                pumlPATH = "./default.puml";
            }
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
            writer.append("\n@enduml\n");
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

    private class OutOption implements Doclet.Option
    {
        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "Set the name of the puml output file.";
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return List.of("-out");
        }

        @Override
        public String getParameters() {
            return null;
        }

        @Override
        public boolean process(String option, List<String> arguments) {

            pumlPATH = "./" + arguments.get(0) + ".puml";

            return true;
        }
    }
}
