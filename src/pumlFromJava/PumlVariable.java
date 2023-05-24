package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class PumlVariable extends PumlElement {

    private Element element;
    private VariableKind kind;

    public PumlVariable(Element element, VariableKind kind)
    {
        this.element = element;
        this.kind = kind;
    }

    @Override
    public String toDCA() {
        return this.element.getSimpleName().toString() + "\n";
    }

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

    public Element getElement() {
        return element;
    }

    public VariableKind getKind() {
        return kind;
    }
}

enum VariableKind
{
    PRIMITIVE,OBJECT,ENUM;
}

