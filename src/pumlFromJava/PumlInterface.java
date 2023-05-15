package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

public class PumlInterface extends PumlElement implements PumlLinkable {
    private Element element;
    public PumlInterface(Element element)
    {
        this.element = element;
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
    public ArrayList<PumlLink> getLinks() {
        ArrayList<PumlLink> Links = new ArrayList<>();



        Links.addAll(getInterfaces());

        return Links;
    }



    public ArrayList<PumlLink> getInterfaces(){
        ArrayList<PumlLink> Links = new ArrayList<PumlLink>();
        for(TypeMirror typeMirror : ((TypeElement)this.element).getInterfaces())
        {
            String[] interfacesFullName = typeMirror.toString().split("\\.");

            PumlLink Link = new PumlLink(this.element.getSimpleName().toString(),
                    interfacesFullName[interfacesFullName.length - 1], LinkType.IMPLEMENTS);
            Links.add(Link);
        }
        return Links;
    }

    @Override
    public String linksToString() {

        StringBuilder builder = new StringBuilder();

        for(PumlLink link : getLinks())
        {
            if(link.getLinkType() == LinkType.IMPLEMENTS && !link.getSecondElement().equals("none"))
            {
                builder.append(link.getFirstElement() + " --|> " + link.getSecondElement() + "\n");
            }
        }

        return builder.toString();
    }
}
