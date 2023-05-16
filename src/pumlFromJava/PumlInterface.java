package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

public class PumlInterface extends PumlElement {

    private Element element;

    private ArrayList<PumlLink> links;

    public PumlInterface(Element element)
    {
        this.element = element;
        links = PumlLink.RetrieveLinks(element,null);
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
}
