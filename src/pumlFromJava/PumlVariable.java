package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

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
        Set<Modifier> modifierList = this.element.getModifiers();

        if(!(modifierList.contains(Modifier.PRIVATE) || modifierList.contains(Modifier.PUBLIC) || modifierList.contains(Modifier.PROTECTED)))
        {
            builder.append("~ ");
        }
        for(Modifier modifier : this.element.getModifiers())
        {
            switch(modifier)
            {
                case PRIVATE -> builder.append("- ");
                case PUBLIC -> builder.append("+ ");
                case PROTECTED -> builder.append("~ ");
            }
        }

        switch (GetElementTypeString(this.element))
        {
            case ("int") -> builder.append( this.element.getSimpleName() + " : " + "Integer");
            case("boolean") -> builder.append( this.element.getSimpleName() + " : " + "Boolean");
            default -> builder.append( this.element.getSimpleName() + " : " + GetElementTypeString(this.element));
        }

        if(this.element.getModifiers().contains(Modifier.STATIC))
        {
            builder.append(" {static}");
        }
        if(this.element.getModifiers().contains(Modifier.FINAL))
        {
            builder.append(" {readOnly}");
        }
        builder.append("\n");
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
    public VariableKind getVariableKind() {
        return kind;
    }


    public static boolean IsCollection(PumlVariable variable)
    {
        boolean result = false;

        if(variable.getVariableKind() != VariableKind.PRIMITIVE)
        {
            DeclaredType declaredType = (DeclaredType) variable.getElement().asType();

            TypeElement typeElement = (TypeElement) declaredType.asElement();

            for (TypeMirror typeMirror : typeElement.getInterfaces())
            {
                if (TypeToString(typeMirror).equals(Collection.class.getSimpleName()))
                {
                    result = true;
                }
            }
        }

        return result;
    }
}

/********************************************************************
 * VariableKind est un énumération avec les types possibles
 ********************************************************************/
enum VariableKind
{
    PRIMITIVE,OBJECT,ENUM;
}

