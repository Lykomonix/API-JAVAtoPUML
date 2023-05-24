package pumlFromJava;


import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

/********************************************************************
La classe PumlLink permet de gérer les liens entre les classes / enum et interface
 ********************************************************************/
public class PumlLink
{
    private String firstElement;
    private String secondElement;
    private LinkType linkType;
    /********************************************************************
    PumlLink est le constructeur de la classe PumlLink
    in: String firstElement,String secondElement, LinkType linkType
    out:
     ********************************************************************/
    public PumlLink(String firstElement,String secondElement, LinkType linkType)
    {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.linkType = linkType;
    }

    /********************************************************************
    getLinkType est un getteur qui permet de récupérer le type de liaison
    in:
    out: LinkType
     ********************************************************************/
    public LinkType getLinkType() {
        return linkType;
    }

    /********************************************************************
    getFirstElement est un getteur qui permet de récupérer le premier element du lien
    in:
    out: String
     ********************************************************************/
    public String getFirstElement() {
        return firstElement;
    }

    /********************************************************************
    getSecondElement est un getteur qui permet de récupérer le Second element du lien
    in:
    out: String
     ********************************************************************/
    public String getSecondElement() {
        return secondElement;
    }

    /********************************************************************
    RetrieveLinks permet de récupérer tous les liens du diagramme
    in: Element element, ArrayList<PumlVariable> variableList
    out: ArrayList<PumlLink>
     ********************************************************************/
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

    /********************************************************************
    getSuperClassLink permet de trouver les liens de type extends
    in: Element element
    out: PumlLink
     ********************************************************************/
    private static PumlLink getSuperClassLink(Element element)
    {
        TypeElement typeElement = (TypeElement) element;

        String[] superClassFullName = typeElement.getSuperclass().toString().split("\\.");

        PumlLink link = new PumlLink(typeElement.getSimpleName().toString(),
                superClassFullName[superClassFullName.length - 1], LinkType.EXTENDS);

        return link;
    }

    /********************************************************************
    getInterfacesLinks permet de récupérer les liens de type implements
    in: Element element
    out: ArrayList<PumlLink>
     ********************************************************************/
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

    /********************************************************************
    getAssociatons permet de récupérer les liens de type association
    in: Element element, ArrayList<PumlVariable> variableList
    out: ArrayList<PumlLink>
     ********************************************************************/
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

    /********************************************************************
    linksToString permet de former la chaine de caractère qui permet de coder le lien
    in: ArrayList<PumlLink> links
    out: String
     ********************************************************************/
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

/********************************************************************
LinkType est une énumération qui permet de stocker les différents type de lien
 ********************************************************************/
enum LinkType
{
    EXTENDS,IMPLEMENTS,ASSOCIATE;
}
