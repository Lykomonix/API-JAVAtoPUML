package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

/********************************************************************
 * PumlVariable hérite de PumlElement et permet de gérer les Variables dans les diagrammes
 ********************************************************************/
public class PumlVariable extends PumlElement {

    private Element element;
    private VariableKind kind;

    /********************************************************************
     * PumlVariable est un constructeur
     * in: Element element, VariableKind kind
     * out: NULL
     ********************************************************************/
    public PumlVariable(Element element, VariableKind kind)
    {
        this.element = element;
        this.kind = kind;
    }

    /********************************************************************
     * toDCA est un fonction redéfini qui permet de gérer les packages dans les DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        return this.element.getSimpleName().toString() + "\n";
    }

    /********************************************************************
     * toDCC est un fonction redéfini qui permet de gérer les packages dans les DCC
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        for(Modifier modifier : this.element.getModifiers())
        {
            switch(modifier)
            {
                case STATIC -> builder.append("{static} ");
                case PRIVATE -> builder.append("- ");
                case PUBLIC -> builder.append("+ ");
                case PROTECTED -> builder.append("~ ");
            }
        }

        switch (GetElementTypeString(this.element))
        {
            case ("int") -> builder.append( this.element.getSimpleName() + " : " + "Integer" + "\n");
            case("boolean") -> builder.append( this.element.getSimpleName() + " : " + "Boolean" + "\n");
            default -> builder.append( this.element.getSimpleName() + " : " + GetElementTypeString(this.element) + "\n");
        }



        return builder.toString();
    }

    /********************************************************************
     * getElement est un getteur qui récupère la variables
     * in: Ø
     * out: Element
     ********************************************************************/
    public Element getElement() {
        return element;
    }

    /********************************************************************
     * getKind est un getteur qui récupère le type de la variable
     * in: Ø
     * out VariableKind
     ********************************************************************/
    public VariableKind getKind() {
        return kind;
    }
}

/********************************************************************
 * VariableKind est un énumération avec les types possibles
 ********************************************************************/
enum VariableKind
{
    PRIMITIVE,OBJECT,ENUM;
}

