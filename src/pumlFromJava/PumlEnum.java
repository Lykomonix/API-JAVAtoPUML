package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementFilter;
import java.util.Enumeration;

/********************************************************************
 * La classe PumlEnum permet de gérer la construction des enumerations dans le diagramme
 * cette classe hérite de la classe PumlElement
 ********************************************************************/
public class PumlEnum extends PumlElement{

    private Element element;

    /********************************************************************
     * PumlEnum est un constructeur
     * in: Element element
     * out: NULL
     ********************************************************************/
    public PumlEnum(Element element)
    {
        this.element = element;
    }

    /********************************************************************
     * getName est un getteur qui récupère le nom de l'élément
     * in: Ø
     * out: String
     ********************************************************************/
    public String getName()
    {
        return this.element.getSimpleName().toString();
    }

    /********************************************************************
     * toDCA est une fonction héritée de la classe PumlElement, elle retourne le code permettant de créer le code pour générer les énumérations dans un DCA
     * in:
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<enumeration>>\\n " + this.element.getSimpleName()+"\" as "+this.element.getSimpleName()+"\n");
        builder.append("{\n");
        builder.append(getEnumElements());
        builder.append("}\n");

        return builder.toString();
    }

    /********************************************************************
     * toDCC est une fonction héritée de la classe PumlElement, elle retourne le code permettant de créer le code pour générer les énumérations dans un DCC
     * in:
     * out: String
     ********************************************************************/
    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<enumeration>>\\n " + this.element.getSimpleName()+"\" as "+this.element.getSimpleName()+"\n");
        builder.append("{\n");
        builder.append(getEnumElements());
        builder.append("}\n");

        return builder.toString();
    }

    /********************************************************************
     * getEnumElements est un getteur qui permet de récupérer les éléments présents dans une énumération
     * in:
     * out: String
     ********************************************************************/
    public String getEnumElements()
    {
        StringBuilder builder = new StringBuilder();
        for(Element elementN : element.getEnclosedElements())
        {
            if(elementN.getKind() == ElementKind.ENUM_CONSTANT)
            {
                builder.append(elementN.getSimpleName()+"\n");
            }
        }
        return builder.toString();
    }
}
