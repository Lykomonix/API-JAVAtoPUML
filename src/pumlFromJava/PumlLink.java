package pumlFromJava;


import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collection;

public class PumlLink extends PumlElement

/********************************************************************
La classe PumlLink permet de gérer les liens entre les classes / enum et interface
 ********************************************************************/
{
    private String firstElementName;
    private String secondElementName;
    private LinkType linkType;
    /********************************************************************
    PumlLink est le constructeur de la classe PumlLink
    in: String firstElement,String secondElement, LinkType linkType
    out:
     ********************************************************************/
    public PumlLink(String firstElementName,String secondElementName, LinkType linkType)
    {
        this.firstElementName = firstElementName;
        this.secondElementName = secondElementName;
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
        return firstElementName;
    }

    /********************************************************************
    getSecondElement est un getteur qui permet de récupérer le Second element du lien
    in:
    out: String
     ********************************************************************/
    public String getSecondElement() {
        return secondElementName;
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
            links.addAll(getAssociatonsDCC(element, variableList, methodList));
            links.addAll(getAssociatonsDCA(element, variableList));
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

    private static ArrayList<PumlLink> getAssociatonsDCA(Element element, ArrayList<PumlVariable> variableList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        for(PumlVariable variable : variableList)
        {
            if(variable.getVariableKind() == VariableKind.OBJECT)
            {
                if(!TypeToString(variable.getElement().asType()).equals("String") && !TypeToString(variable.getElement().asType()).equals("Set") && !TypeToString(variable.getElement().asType()).equals("List"))
                {
                    PumlLink link = new PumlLink(element.getSimpleName().toString(),
                            TypeToString(variable.getElement().asType()), LinkType.DCA_ASSOCIATE);

                    links.add(link);
                }
            }
        }

        return links;
    }


    /********************************************************************
    getAssociatons permet de récupérer les liens de type association
    in: Element element, ArrayList<PumlVariable> variableList
    out: ArrayList<PumlLink>
     ********************************************************************/
    private static ArrayList<PumlLink> getAssociatonsDCC(Element element, ArrayList<PumlVariable> variableList, ArrayList<PumlMethod> methodList)
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        for(PumlVariable variable : variableList)
        {
            if(variable.getVariableKind() == VariableKind.OBJECT)
            {

                DeclaredType declaredType = (DeclaredType) variable.getElement().asType();

                TypeElement typeElement = (TypeElement) declaredType.asElement();

                for(TypeMirror typeMirror : typeElement.getInterfaces())
                {
                    if(TypeToString(typeMirror).equals(Collection.class.getSimpleName()))
                    {
                        PumlLink link = new PumlLink(element.getSimpleName().toString()
                                + " \" * \\n " + variable.getElement().getSimpleName() + " \"",
                                ExtractContainedTypeString(variable.getElement().asType()), LinkType.LIST);

                        links.add(link);
                    }
                }
            }
        }

        for(PumlMethod method : methodList)
        {
            if(!method.getElement().getSimpleName().toString().equals("<init>"))
            {
                for(PumlParameter parameter : method.getParameters())
                {
                    if(!TypeToString(parameter.getVariableElement().asType()).equals("String"))
                    {
                        TypeKind parameterKind = parameter.getVariableElement().asType().getKind();

                        if(parameterKind == TypeKind.DECLARED && !parameterKind.isPrimitive())
                        {
                            DeclaredType declaredType = (DeclaredType) parameter.getVariableElement().asType();

                            if(declaredType.asElement().getEnclosingElement().equals(element.getEnclosingElement()))
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
            }
        }

        return links;
    }

    /********************************************************************
    linksToString permet de former la chaine de caractère qui permet de coder le lien
    in: ArrayList<PumlLink> links
    out: String
     ********************************************************************/
    public static String linksToStringDCC(ArrayList<PumlLink> links) {

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
            else if(link.getLinkType() == LinkType.LIST)//là ici c'est KC
            {
                builder.append(link.getFirstElement() + " o--> " + link.getSecondElement() + "\n");
            }
            else if(link.getLinkType() == LinkType.USE)
            {
                builder.append(link.getFirstElement() + " ..> " + link.getSecondElement() + " : <<use>> \n");
            }
        }

        return builder.toString();
    }

    public static String linksToStringDCA(ArrayList<PumlLink> links) {

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
            else if(link.getLinkType() == LinkType.DCA_ASSOCIATE)
            {
                builder.append(link.getFirstElement() + " -- " + link.getSecondElement() + "\n");
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

        return this.firstElementName.equals(link.firstElementName)
                && this.secondElementName.equals(link.secondElementName)
                && this.getLinkType() == link.getLinkType();
    }
}

/********************************************************************
LinkType est une énumération qui permet de stocker les différents type de lien
 ********************************************************************/
enum LinkType
{
    EXTENDS,IMPLEMENTS,DCA_ASSOCIATE,LIST,USE;
}
