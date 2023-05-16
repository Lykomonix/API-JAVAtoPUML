package pumlFromJava;


import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

public class PumlLink
{
    private String firstElement;
    private String secondElement;
    private LinkType linkType;
    PumlLink(String firstElement,String secondElement, LinkType linkType)
    {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.linkType = linkType;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }

    public static ArrayList<PumlLink> RetrieveLinks(Element element, ArrayList<PumlVariable> variableList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        links.add(getSuperClassLink(element));
        links.addAll(getInterfacesLinks(element));
        if(variableList != null)
        {
            links.addAll(getAssociatons(element, variableList));
        }

        return links;
    }

    private static PumlLink getSuperClassLink(Element element)
    {
        TypeElement typeElement = (TypeElement) element;

        String[] superClassFullName = typeElement.getSuperclass().toString().split("\\.");

        PumlLink link = new PumlLink(typeElement.getSimpleName().toString(),
                superClassFullName[superClassFullName.length - 1], LinkType.EXTENDS);

        return link;
    }

    private static ArrayList<PumlLink> getInterfacesLinks(Element element)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        TypeElement typeElement = (TypeElement) element;

        for(TypeMirror typeMirror : typeElement.getInterfaces())
        {
            String[] interfaceFullName = typeMirror.toString().split("\\.");

            PumlLink link = new PumlLink(typeElement.getSimpleName().toString(),
                    interfaceFullName[interfaceFullName.length - 1], LinkType.IMPLEMENTS);

            links.add(link);
        }

        return links;
    }

    private static ArrayList<PumlLink> getAssociatons(Element element, ArrayList<PumlVariable> variableList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        for(PumlVariable variable : variableList)
        {
            if(variable.getKind() == VariableKind.OBJECT)
            {
                if(!variable.TypeToString().equals("String") && !variable.TypeToString().equals("Set") && !variable.TypeToString().equals("List"))
                {
                    PumlLink link = new PumlLink(element.getSimpleName().toString(),
                            variable.TypeToString(), LinkType.ASSOCIATE);

                    links.add(link);
                }
            }
        }

        return links;
    }

    public static String linksToString(ArrayList<PumlLink> links) {

        StringBuilder builder = new StringBuilder();

        for(PumlLink link : links)
        {
            if(link.getLinkType() == LinkType.EXTENDS && !link.getSecondElement().equals("Object"))
            {
                builder.append(link.getFirstElement() + " --|> " + link.getSecondElement() + "\n");
            }
            else if(link.getLinkType() == LinkType.IMPLEMENTS)
            {
                builder.append(link.getFirstElement() + " ..|> " + link.getSecondElement() + "\n");
            }
            else if(link.getLinkType() == LinkType.ASSOCIATE)
            {
                builder.append(link.getFirstElement() + " -- " + link.getSecondElement() + "\n");
            }
        }

        return builder.toString();
    }
}

enum LinkType
{
    EXTENDS,IMPLEMENTS,ASSOCIATE;
}
