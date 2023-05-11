package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.ElementType;
import java.lang.reflect.Type;

public class PumlClass extends PumlElement{
    private Element element;
    PumlClass(Element element)
    {
        this.element = element;
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        builder.append(getPrimitiveVariables());

        builder.append("}\n");

        return builder.toString();
    }

    private String getPrimitiveVariables()
    {
        StringBuilder builder = new StringBuilder();

        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.FIELD)
            {
                //System.out.println(enclosedElement.asType().getKind()) ;

                builder.append(enclosedElement.getSimpleName() + "\n");
            }
        }

        return builder.toString();
    }
}
