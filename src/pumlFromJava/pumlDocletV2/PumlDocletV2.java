import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PumlDocletV2 implements Doclet {

    //Set de toutes les options utilisés par le doclet
    private Set<Option> options;

    //Répertoire utilisé pour le fichier PUML
    private String directory = ".";

    //Nom utilisé pour le fichier PUML
    private String pumlName;

    //Objet StringBuilder utilisé pour construire le fichier PUML
    private StringBuilder builder = new StringBuilder();

    //Objet FileWriter utilisé pour l'écriture du fichier PUML
    private FileWriter writer;

    @Override
    public void init(Locale locale, Reporter reporter)
    {
        options = Set.of(new OutOption(), new DirectoryOption());
    }

    @Override
    public String getName()
    {
        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions()
    {
        return options;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment)
    {
        RetrieveClasses(environment);
        WritePuml();
        return true;
    }

    private void WritePuml()
    {
        try
        {
            writer = new FileWriter(directory + "/" + pumlName);

            writer.append(
                    "@startuml\n\n" +
                    "skinparam style strictuml\n" +
                    "skinparam classAttributeIconSize 0\n" +
                    "skinparam classFontStyle Bold\n\n");

            writer.append(builder.toString());

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
        int i =0;
        for(Element element : environment.getSpecifiedElements())
        {
            if(pumlName == null && i == 0)
            {
                pumlName = element.getSimpleName().toString() + ".puml";
            }

            builder.append("class " + element.getSimpleName() + "\n");

            i++;
        }
    }

    private class OutOption implements Option
    {
        @Override
        public int getArgumentCount()
        {
            return 1;
        }

        @Override
        public String getDescription()
        {
            return "Set the name of the puml output file.";
        }

        @Override
        public Kind getKind()
        {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames()
        {
            return List.of("-out");
        }

        @Override
        public String getParameters()
        {
            return null;
        }

        @Override
        public boolean process(String option, List<String> arguments)
        {
            pumlName = arguments.get(0);
            return true;
        }
    }

    private class DirectoryOption implements Option
    {
        @Override
        public int getArgumentCount()
        {
            return 1;
        }

        @Override
        public String getDescription()
        {
            return "Set the directory of the puml output file.";
        }

        @Override
        public Kind getKind()
        {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames()
        {
            return List.of("-d");
        }

        @Override
        public String getParameters()
        {
            return null;
        }

        @Override
        public boolean process(String option, List<String> arguments)
        {
            directory = arguments.get(0);
            return true;
        }
    }
}
