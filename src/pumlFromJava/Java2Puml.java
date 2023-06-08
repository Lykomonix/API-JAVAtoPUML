package pumlFromJava;

import java.util.spi.ToolProvider;

public class Java2Puml
{
    /********************************************************************
     * c'est le point d'entrée du programme
     ********************************************************************/
    public static void main(String[] args)
    {
        ToolProvider toolProvider = ToolProvider.findFirst("javadoc").get();
        System.out.println(toolProvider.name());

/********************************************************************
    javadoc -private -sourcepath <src> -doclet pumlFromJava.FirstDoclet -docletpath out/production/<projet>
      <package> ... <fichiers>
 ********************************************************************/
        toolProvider.run(System.err, System.out, args);
    }
}
