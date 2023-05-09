package pumlFromJava.pumlDocletV3;

import javax.lang.model.element.Element;

public class PumlEnum implements PumlElement{
    private Element element;
    PumlEnum(Element element)
    {
        this.element = element;
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toUml() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName());

        return builder.toString();
    }
}
