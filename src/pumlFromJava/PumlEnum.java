package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementFilter;
import java.util.Enumeration;

public class PumlEnum extends PumlElement{
    private Element element;
    PumlEnum(Element element)
    {
        this.element = element;
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<enumeration>>\\n" + this.element.getSimpleName()+"\"\n" );
        builder.append("{\n");
        builder.append(getEnumElements());
        builder.append("}\n");

        return builder.toString();
    }
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
