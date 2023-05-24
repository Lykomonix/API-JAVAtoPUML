package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

public class PumlInterface extends PumlElement {

    private Element element;

    private ArrayList<PumlLink> links;
    private ArrayList<PumlMethod> methodList = new ArrayList<>();

    public PumlInterface(Element element)
    {
        this.element = element;
        links = PumlLink.RetrieveLinks(element,null);
        RetrieveMethods();
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<interface>>\\n " + this.element.getSimpleName()+"\" as "+this.element.getSimpleName()+"\n");

        return builder.toString();
    }

    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<interface>>\\n " + this.element.getSimpleName()+"\" as "+this.element.getSimpleName()+"\n");

        builder.append("{\n");

        for(PumlMethod method : methodList)
        {
            builder.append(method.toDCC());
        }

        builder.append("\n}\n");

        return builder.toString();
    }

    public String linksToString() {

        StringBuilder builder = new StringBuilder();

        for(PumlLink link : links)
        {
            if(link.getLinkType() == LinkType.IMPLEMENTS && !link.getSecondElement().equals("none"))
            {
                builder.append(link.getFirstElement() + " --|> " + link.getSecondElement() + "\n");
            }
        }

        return builder.toString();
    }

    private void RetrieveMethods()
    {
        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.METHOD || enclosedElement.getKind() == ElementKind.CONSTRUCTOR)
            {
                methodList.add(new PumlMethod(enclosedElement));
            }
        }
    }
}
