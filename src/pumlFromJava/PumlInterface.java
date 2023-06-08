package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

/********************************************************************
 * PumlInterface hérite de PumlElement est permet de gérer les interfaces dans les diagrammes
 ********************************************************************/
public class PumlInterface extends PumlElement {

    private Element element;

    private ArrayList<PumlLink> links;
    private ArrayList<PumlMethod> methodList = new ArrayList<>();

    /********************************************************************
     * PumlInterface est un constructeur
     * in: Element element
     * out: NULL
     ********************************************************************/
    public PumlInterface(Element element)
    {
        this.element = element;
        links = PumlLink.RetrieveLinks(element,null,methodList);
        RetrieveMethods();
    }

    /********************************************************************
     * getName est un getteur qui récupère le nom de l'interface
     * in: Ø
     * out: String
     ********************************************************************/
    public String getName() {
        return this.element.getSimpleName().toString();
    }

    /********************************************************************
     * toDCA est une fonction redéfini qui construit les interfaces dans les DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<interface>>\\n " + this.element.getSimpleName()+"\" as "+this.element.getSimpleName()+"\n");

        return builder.toString();
    }

    /********************************************************************
     * toDCC est une fonction redéfini qui construit les interfaces dans les DCC
     * in: Ø
     * out: String
     ********************************************************************/
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

    /********************************************************************
     * linksToString retourne un lien
     * in: Ø
     * out: String
     ********************************************************************/
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

    /********************************************************************
     * RetrieveMethods permet de récupérer les méthodes des interfaces
     * in: Ø
     * out: void
     ********************************************************************/
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
