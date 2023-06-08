package pumlFromJava;

import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.ArrayList;

/********************************************************************
 * PumlDiagram hérite de PumlElement est permet de gérer le Diagramme
 ********************************************************************/
public class PumlDiagram extends PumlElement{

    private String skinparam =
            "skinparam style strictuml\n" +
            "skinparam classAttributeIconSize 0\n" +
            "skinparam classFontStyle Bold\n" +
            "hide empty members\n\n";

    private String directory = "./";
    private String filename;

    private List<PumlPackage> packageList = new ArrayList<>();

    private DocletEnvironment env;

    /********************************************************************
     * PumlDiagram est un constructeur
     * in: DocletEnvironment env
     * out: NULL
     ********************************************************************/
    public PumlDiagram(DocletEnvironment env)
    {
        this.env = env;
        RetrievePackages();
    }

    /********************************************************************
     * RetrievePackages permet de récupérer les packages
     * in: Ø
     * out: void
     ********************************************************************/
    private void RetrievePackages()
    {
        for(Element element : env.getSpecifiedElements())
        {
            if(element.getKind() == ElementKind.PACKAGE)
            {
                packageList.add(new PumlPackage(element));
            }
        }
    }

    /********************************************************************
     * toDCA est une fonction redéfini. elle permet de gérer les diagrammes dans un DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("@startuml\n\n");

        builder.append(skinparam);

        for(PumlPackage pumlPackage : packageList)
        {
            builder.append("Package \""+pumlPackage.getName()+"\" {\n");
            builder.append(pumlPackage.toDCA());
            builder.append("}\n");
        }

        builder.append("\n@enduml\n\n");

        return builder.toString();
    }

    /********************************************************************
     * toDCC est une fonction redéfini. elle permet de gérer les diagrammes dans un DCC
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCC()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("@startuml\n\n");

        builder.append(skinparam);

        for(PumlPackage pumlPackage : packageList)
        {
            builder.append("Package \""+pumlPackage.getName()+"\" {\n");
            builder.append(pumlPackage.toDCC());
            builder.append("}\n");
        }

        builder.append("\n@enduml\n\n");

        return builder.toString();
    }


    /********************************************************************
     * setDirectory est un setteur qui définit le répertoire
     * in: String directory
     * out: void
     ********************************************************************/
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /********************************************************************
     * setFilename est un setteur qui définit le fichier
     * in: String filename
     * out: void
     ********************************************************************/
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /********************************************************************
     * getDirectory est un getteur qui récupère le répertoire
     * in: void
     * out: String
     ********************************************************************/
    public String getDirectory() {
        return directory;
    }

    /********************************************************************
     * getFilename est un getteur qui récupère le fichier
     * in: void
     * out: String
     ********************************************************************/
    public String getFilename() {
        return filename;
    }

    /********************************************************************
     * getPackageList est un getteur qui récupère la liste des packages
     * in: void
     * out: List<PumlPackage>
     ********************************************************************/
    public List<PumlPackage> getPackageList() {
        return packageList;
    }
}
