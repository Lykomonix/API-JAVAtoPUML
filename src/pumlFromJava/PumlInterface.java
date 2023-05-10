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
    public String toUml() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName());

        return builder.toString();
    }
}
