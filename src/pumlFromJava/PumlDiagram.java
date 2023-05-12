package pumlFromJava;

import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.ArrayList;

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

    public PumlDiagram(DocletEnvironment env)
    {
        this.env = env;
        RetrievePackages();
    }

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

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return filename;
    }

    public List<PumlPackage> getPackageList() {
        return packageList;
    }
}
