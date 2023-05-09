package pumlFromJava.pumlDocletV3;

import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.List;
import java.util.ArrayList;

public class PumlDiagram implements PumlElement{

    private String skinparam =
            "skinparam style strictuml\n" +
            "skinparam classAttributeIconSize 0\n" +
            "skinparam classFontStyle Bold\n\n";

    private String directory = ".";
    private String filename;

    private List<PumlClass> classList = new ArrayList<>();
    private List<PumlInterface> interfaceList = new ArrayList<>();
    private List<PumlEnum> enumList = new ArrayList<>();

    private DocletEnvironment env;

    PumlDiagram(DocletEnvironment env)
    {
        this.env = env;
        RetrieveFiles();
    }

    private void RetrieveFiles()
    {
        for(Element element : env.getSpecifiedElements())
        {
            switch(element.getKind())
            {
                case CLASS -> classList.add(new PumlClass(element));
                case INTERFACE -> interfaceList.add(new PumlInterface(element));
                case ENUM -> enumList.add(new PumlEnum(element));
            }
        }
    }

    @Override
    public String toUml()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("@startuml\n\n");

        builder.append(skinparam);

        for(PumlElement element : classList)
        {
            builder.append(element.toUml() + "\n");
        }

        for(PumlElement element : interfaceList)
        {
            builder.append(element.toUml() + "\n");
        }

        for(PumlElement element : enumList)
        {
            builder.append(element.toUml() + "\n");
        }

        return builder.toString();
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<PumlClass> getClassList() {
        return classList;
    }

    public List<PumlInterface> getInterfaceList() {
        return interfaceList;
    }

    public List<PumlEnum> getEnumList() {
        return enumList;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return filename;
    }
}
