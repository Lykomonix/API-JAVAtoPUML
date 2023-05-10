package pumlFromJava;

import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.List;
import java.util.ArrayList;

public class PumlDiagram extends PumlElement{

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
            System.out.println(element.getEnclosedElements());
            for(Element el : element.getEnclosedElements())
            {
                System.out.println(el.getSimpleName());
                switch(el.getKind())
                {
                    case CLASS -> classList.add(new PumlClass(el));
                    case INTERFACE -> interfaceList.add(new PumlInterface(el));
                    case ENUM -> enumList.add(new PumlEnum(el));
                }
            }
        }
    }

    @Override
    public String toDCC()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("@startuml\n\n");

        builder.append(skinparam);

        for(PumlElement element : classList)
        {
            builder.append(element.toDCC() + "\n");
        }

        for(PumlElement element : interfaceList)
        {
            builder.append(element.toDCC() + "\n");
        }

        for(PumlElement element : enumList)
        {
            builder.append(element.toDCC() + "\n");
        }
        builder.append("\n\n@enduml");
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
