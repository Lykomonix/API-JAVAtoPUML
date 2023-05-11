package pumlFromJava;

import javax.lang.model.element.Element;

public class PumlInterface extends PumlElement {
    private Element element;
    PumlInterface(Element element)
    {
        this.element = element;
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class \"<<interface>>\\n " + this.element.getSimpleName()+"\"");

        return builder.toString();
    }
}
