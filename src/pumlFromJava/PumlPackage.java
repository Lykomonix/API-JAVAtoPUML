package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/********************************************************************
 * PumlPackage hérite de PumlElement et permet de gérer les packages dans les Diagrammes
 ********************************************************************/
public class PumlPackage extends PumlElement{

    private List<PumlClass> classList = new ArrayList<>();
    private List<PumlInterface> interfaceList = new ArrayList<>();
    private List<PumlEnum> enumList = new ArrayList<>();

    private Element element;

    /********************************************************************
     * PumlPackage est un constructeur
     * in: Element element
     * out: NULL
     ********************************************************************/
    public PumlPackage(Element element)
    {
        this.element = element;
        RetrieveElements();
    }

    /********************************************************************
     * RetrieveElements récupère les éléments du package
     * in: Ø
     * out: void
     ********************************************************************/
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

    /********************************************************************
     * toDCA est un fonction redéfini qui permet de gérer les packages dans les DCA
     * in: Ø
     * out: String
     ********************************************************************/
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

        for(PumlClass element : classList)
        {
            builder.append(PumlLink.linksToString(element.getLinks()) + "\n");
        }

        for(PumlInterface element : interfaceList)
        {
            builder.append(element.linksToString() + "\n");
        }

        return builder.toString();
    }

    /********************************************************************
     * toDCC est un fonction redéfini qui permet de gérer les packages dans les DCC
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCC() {

        StringBuilder builder = new StringBuilder();

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

        /**
        for(PumlClass element : classList)
        {
            builder.append(PumlLink.linksToString(element.getLinks()) + "\n");
        }

        for(PumlInterface element : interfaceList)
        {
            builder.append(element.linksToString() + "\n");
        }
        **/

        return builder.toString();
    }

    /********************************************************************
     * getName est un getteur qui récupère le nom des packages
     * in: Ø
     * out: String
     ********************************************************************/
    public String getName()
    {
        return this.element.getSimpleName().toString();
    }

}
