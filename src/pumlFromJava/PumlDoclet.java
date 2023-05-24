package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.io.FileWriter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/********************************************************************
 * PumlDoclet est une classe qui implemente l'interface Doclet. C'est notre doclet
 ********************************************************************/
public class PumlDoclet implements Doclet {
    private PumlDiagram pumlDiagram;
    private String filename;
    private String directory;
    private boolean dca = false;

    /********************************************************************
     * init est une redéfinition
     * in: Locale locale, Reporter reporter
     * out: void
     ********************************************************************/
    @Override
    public void init(Locale locale, Reporter reporter) {

    }

    /********************************************************************
     * getName est un getteur redéfini qui récupère le nom des classes
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /********************************************************************
     * getSupportedOptions est un getteur redéfini qui récupère les options de la commande
     * in: Ø
     * out: Set<\?extends Option>
     ********************************************************************/
    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(new OutOption(), new DirectoryOption(), new DCAOption());
    }

    /********************************************************************
     * getSupportedSourceVersion est un getteur redéfini qui récupère la version de java
     * in: Ø
     * out: SourceVersion
     ********************************************************************/
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    /********************************************************************
     * run est une classe redéfini. Elle permet de générer un lieu pour écrire le code puml
     * in: DocletEnvironment environment
     * out: boolean
     ********************************************************************/
    @Override
    public boolean run(DocletEnvironment environment) {
        InitPumlDiagram(environment);
        WritePuml();
        return true;
    }

    /********************************************************************
     * InitPumlDiagram permet d'initialiser la zone d'écriture
     * in: DocletEnvironment env
     * out: void
     ********************************************************************/
    private void InitPumlDiagram(DocletEnvironment env)
    {
        pumlDiagram = new PumlDiagram(env);

        if(filename == null)
        {
            pumlDiagram.setFilename(pumlDiagram.getPackageList().get(0).getName());
        }
        else
        {
            pumlDiagram.setFilename(this.filename);
        }

        if(directory != null)
        {
            pumlDiagram.setDirectory(this.directory);
        }
    }

    /********************************************************************
     * WritePuml permet d'écrire dans la zone définit
     * in: Ø
     * out: void
     ********************************************************************/
    private void WritePuml()
    {
        try
        {
            FileWriter writer = new FileWriter(
                    pumlDiagram.getDirectory() +
                            pumlDiagram.getFilename() + ".puml");

            writer.write("");
            if(dca)
            {
                writer.append(pumlDiagram.toDCA());
            }
            else
            {
                writer.append(pumlDiagram.toDCC());
            }
            writer.close();
        }
        catch (Exception ex)
        {
            System.out.println("The file cannot be written");
        }
    }

    /********************************************************************
     * OutOption est une nested classe qui implemente l'interface Doclet.Option
     ********************************************************************/
    private class OutOption implements Doclet.Option
    {
        /********************************************************************
         * getArgumentCount est un getteur redéfini qui récupère le nombre d'argument
         * in: Ø
         * out: int
         ********************************************************************/
        @Override
        public int getArgumentCount() {
            return 1;
        }

        /********************************************************************
         * getDescription est un getteur redéfini qui récupère la description
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getDescription() {
            return "Set the output file's name";
        }

        /********************************************************************
         * getKind est un getteur redéfini qui récupère le type de base
         * in: Ø
         * out: Kind
         ********************************************************************/
        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        /********************************************************************
         * getNames est un getteur redéfini qui récupère
         * in: Ø
         * out : List<String>
         ********************************************************************/
        @Override
        public List<String> getNames() {
            return List.of("-out");
        }

        /********************************************************************
         * getParameters est un getteur redéfini qui récupère les paramètres
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getParameters() {
            return null;
        }

        /********************************************************************
         * process est une classe redéfini qui récupère le nom du fichier
         * in: String option, List<String> arguments
         * out: boolean
         ********************************************************************/
        @Override
        public boolean process(String option, List<String> arguments) {
            filename = arguments.get(0);
            return true;
        }
    }

    /********************************************************************
     * DirectoryOption est une Nested classe qui implemente Doclet.Option
     ********************************************************************/
    private class DirectoryOption implements Doclet.Option
    {
        /********************************************************************
         * getArgumentCount est un getteur redéfini qui récupère le nombre d'argument
         * in: Ø
         * out: int
         ********************************************************************/
        @Override
        public int getArgumentCount() {
            return 1;
        }

        /********************************************************************
         * getDescription est un getteur redéfini qui récupère la description
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getDescription() {
            return "Set the file's output directory";
        }

        /********************************************************************
         * getKind est un getteur redéfini qui récupère le type de base
         * in: Ø
         * out: Kind
         ********************************************************************/
        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        /********************************************************************
         * getNames est un getteur redéfini qui récupère
         * in: Ø
         * out : List<String>
         ********************************************************************/
        @Override
        public List<String> getNames() {
            return List.of("-d");
        }

        /********************************************************************
         * getParameters est un getteur redéfini qui récupère les paramètres
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getParameters() {
            return null;
        }

        /********************************************************************
         * process est une classe redéfini qui récupère le nom du fichier
         * in: String option, List<String> arguments
         * out: boolean
         ********************************************************************/
        @Override
        public boolean process(String option, List<String> arguments) {
            directory = arguments.get(0);
            return true;
        }
    }

    /********************************************************************
     * DCAOption
     ********************************************************************/
    private class DCAOption implements Doclet.Option {
        /********************************************************************
         * getArgumentCount est un getteur redéfini qui récupère le nombre d'argument
         * in: Ø
         * out: int
         ********************************************************************/
        @Override
        public int getArgumentCount() {
            return 1;
        }

        /********************************************************************
         * getDescription est un getteur redéfini qui récupère la description
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getDescription() {
            return "If specified, creates a DCA diagram";
        }

        /********************************************************************
         * getKind est un getteur redéfini qui récupère le type de base
         * in: Ø
         * out: Kind
         ********************************************************************/
        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        /********************************************************************
         * getNames est un getteur redéfini qui récupère
         * in: Ø
         * out : List<String>
         ********************************************************************/
        @Override
        public List<String> getNames() {
            return List.of("-dca");
        }

        /********************************************************************
         * getParameters est un getteur redéfini qui récupère les paramètres
         * in: Ø
         * out: String
         ********************************************************************/
        @Override
        public String getParameters() {
            return null;
        }

        /********************************************************************
         * process est une classe redéfini qui récupère le nom du fichier
         * in: String option, List<String> arguments
         * out: boolean
         ********************************************************************/
        @Override
        public boolean process(String option, List<String> arguments) {
            dca = true;
            return true;
        }
    }
}
