package pumlFromJava;


import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PumlLink extends PumlElement

/********************************************************************
La classe PumlLink permet de gérer les liens entre les classes / enum et interface
 ********************************************************************/
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
    public static ArrayList<PumlLink> RetrieveLinks(Element element, ArrayList<PumlVariable> variableList, ArrayList<PumlMethod> methodList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        links.add(getSuperClassLink(element));
        links.addAll(getInterfacesLinks(element));
        if(variableList != null)
        {
            links.addAll(getAssociatons(element, variableList, methodList));
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
    private static ArrayList<PumlLink> getAssociatons(Element element, ArrayList<PumlVariable> variableList, ArrayList<PumlMethod> methodList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        for(PumlVariable variable : variableList)
        {
            if(variable.getKind() == VariableKind.OBJECT)
            {
                String type = GetElementTypeString(variable.getElement());
                if(!type.equals("String") && !type.equals("List") && !type.equals("Set"))
                {
                    PumlLink link = new PumlLink(element.getSimpleName().toString(),
                            GetElementTypeString(variable.getElement()), LinkType.ASSOCIATE);

                    links.add(link);
                }
                else if (type.equals("List") || type.equals("Set"))
                {
                    PumlLink link = new PumlLink(element.getSimpleName().toString(),
                            ExtractContainedTypeString(variable.getElement().asType()), LinkType.LIST);

                    links.add(link);
                }
            }
        }

        for(PumlMethod method : methodList)
        {
            if(!method.getElement().getSimpleName().toString().equals("<init>"))
            {
                for(PumlParameter parameter : method.getParameters())
                {
                    String type = TypeToString(parameter.getVariableElement().asType());
                    if(!type.contains("String") && !type.contains("char") && !type.contains("int") && !type.contains("List") )
                    {
                        PumlLink link = new PumlLink(element.getSimpleName().toString(),
                                TypeToString(parameter.getVariableElement().asType()), LinkType.USE);

                        if(!links.contains(link))
                        {
                            links.add(link);
                        }
                    }
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
            else if(link.getLinkType() == LinkType.LIST)
            {
                builder.append(link.getFirstElement() + " \"*\" " + " -- " + link.getSecondElement() + "\n");
            }
            else if(link.getLinkType() == LinkType.USE)
            {
                builder.append(link.getFirstElement() + " ..> " + link.getSecondElement() + " : <<use>> \n");
            }
        }

        return builder.toString();
    }

    private static String ExtractContainedTypeString(TypeMirror type)
    {
        String FullName = type.toString();

        if(FullName.contains("<"))
        {
            String[] FullNameSplit = FullName.split("\\<");

            return FullNameSplit[1].split("\\.")[FullNameSplit[0].split("\\.").length - 1].split(">")[0];
        }
        else
        {
            String[] FullNameSplit = FullName.split("\\.");

            return FullNameSplit[FullNameSplit.length - 1];
        }
    }

    @Override
    public String toDCA() {
        return null;
    }

    @Override
    public String toDCC() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != PumlLink.class)
        {
            throw new IllegalArgumentException("AH !");
        }
        PumlLink link = (PumlLink) obj;

        return this.firstElement.equals(link.firstElement)
                && this.secondElement.equals(link.secondElement)
                && this.getLinkType() == link.getLinkType();
    }
}

/********************************************************************
LinkType est une énumération qui permet de stocker les différents type de lien
 ********************************************************************/
enum LinkType
{
    EXTENDS,IMPLEMENTS,ASSOCIATE,LIST,USE;
}
