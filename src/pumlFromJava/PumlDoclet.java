package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.io.FileWriter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/*
PumlDoclet est un class qui implements l'interface Doclet. c'est notre Doclet
 */
public class PumlDoclet implements Doclet {
    private PumlDiagram pumlDiagram;
    private String filename;
    private String directory;
    private boolean dca = false;

    /*
    init
    in: Locale locale, Reporter reporter
    out: void
     */
    @Override
    public void init(Locale locale, Reporter reporter) {

    }

    /*
    getName est un getteur qui récupère le nom
    in:
    out: String
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /*
    getSupportedOptions est un getteur qui récupère les options
    in:
    out: Set<? extends Option>
     */
    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(new OutOption(), new DirectoryOption(), new DCAOption());
    }

    /*
    getSupportedSourceVersion est un getteur qui récupère la version
    in:
    out: SourceVersion
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    /*
    run permet de générer un lieu pour écrire le code puml
    in: DocletEnvironment environment
    out: boolean
     */
    @Override
    public boolean run(DocletEnvironment environment) {
        InitPumlDiagram(environment);
        WritePuml();
        return true;
    }

    /*
    InitPumlDiagram permet d'initialiser la zone d'écriture
    in: DocletEnvironment env
    out:
     */
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

    /*
    WritePuml permet d'écrire dans la zone définit
    in:
    out:
     */
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

    /*
    OutOption est une nested qui implements Doclet.Option
     */
    private class OutOption implements Doclet.Option
    {
        /*
        
         */
        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "Set the output file's name";
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
            filename = arguments.get(0);
            return true;
        }
    }

    private class DirectoryOption implements Doclet.Option
    {
        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "Set the file's output directory";
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return List.of("-d");
        }

        @Override
        public String getParameters() {
            return null;
        }

        @Override
        public boolean process(String option, List<String> arguments) {
            directory = arguments.get(0);
            return true;
        }
    }

    private class DCAOption implements Doclet.Option {
        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "If specified, creates a DCA diagram";
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return List.of("-dca");
        }

        @Override
        public String getParameters() {
            return null;
        }

        @Override
        public boolean process(String option, List<String> arguments) {
            dca = true;
            return true;
        }
    }
}
