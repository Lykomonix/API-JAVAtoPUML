package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class PumlMethod extends PumlElement{

    private Element element;
    private ArrayList<MethodModifiers> methodModifiers = new ArrayList<>();

    public PumlMethod(Element element)
    {
        this.element = element;
    }

    @Override
    public String toDCA() {
        return null;
    }

    @Override
    public String toDCC() {
        return null;
    }
}

enum MethodModifiers
{
    CONSTRUCT,STATIC,ABSTRACT,PUBLIC,PRIVATE,PACKAGE
}

