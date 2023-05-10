package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

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
    public String toDCC() {
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
                System.out.println(enclosedElement.asType().toString());
                builder.append(enclosedElement.getSimpleName() + "\n");
            }
        }

        return builder.toString();
    }
}
