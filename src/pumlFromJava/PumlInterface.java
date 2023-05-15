package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class PumlInterface extends PumlElement implements PumlLinkable {
    private Element element;
    public PumlInterface(Element element)
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

    @Override
    public ArrayList<PumlLink> getDCALinks() {
        return null;
    }
}
