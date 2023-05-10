package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.io.FileWriter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PumlDoclet implements Doclet {
    private PumlDiagram pumlDiagram;
    private String filename;
    private String directory;

    @Override
    public void init(Locale locale, Reporter reporter) {

    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(new OutOption(), new DirectoryOption());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        InitPumlDiagram(environment);
        WritePuml();
        return true;
    }

    private void InitPumlDiagram(DocletEnvironment env)
    {
        pumlDiagram = new PumlDiagram(env);

        if(filename == null)
        {
            pumlDiagram.setFilename(pumlDiagram.getClassList().get(0).getName());
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

    private void WritePuml()
    {
        try
        {
            FileWriter writer = new FileWriter(
                    pumlDiagram.getDirectory() +
                            pumlDiagram.getFilename() + ".puml");

            writer.write("");
            writer.append(pumlDiagram.toDCC());
            writer.close();
        }
        catch (Exception ex)
        {
            System.out.println("The file cannot be written");
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
}
