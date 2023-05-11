package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

public class PumlPackage extends PumlElement{

    private List<PumlClass> classList = new ArrayList<>();
    private List<PumlInterface> interfaceList = new ArrayList<>();
    private List<PumlEnum> enumList = new ArrayList<>();

    private Element element;

    PumlPackage(Element element)
    {
        this.element = element;
        RetrieveElements();
    }

    private void RetrieveElements()
    {
        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            switch (enclosedElement.getKind())
            {
                case CLASS -> classList.add(new PumlClass(enclosedElement));
                case INTERFACE -> interfaceList.add(new PumlInterface(enclosedElement));
                case ENUM -> enumList.add(new PumlEnum(enclosedElement));
            }
        }
    }

    @Override
    public String toDCA() {

        StringBuilder builder = new StringBuilder();

        for(PumlElement element : classList)
        {
            builder.append(element.toDCA() + "\n");
        }

        for(PumlElement element : interfaceList)
        {
            builder.append(element.toDCA() + "\n");
        }

        for(PumlElement element : enumList)
        {
            builder.append(element.toDCA() + "\n");
        }

        return builder.toString();
    }

    public String getName()
    {
        return this.element.getSimpleName().toString();
    }
}
