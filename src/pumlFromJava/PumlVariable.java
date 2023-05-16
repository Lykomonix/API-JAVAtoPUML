package pumlFromJava;

import javax.lang.model.element.Element;

public class PumlVariable extends PumlElement {

    private Element element;
    private VariableKind kind;

    PumlVariable(Element element, VariableKind kind)
    {
        this.element = element;
        this.kind = kind;
    }

    public String TypeToString()
    {
        String FullName = element.asType().toString();

        if(FullName.contains("<"))
        {
            String[] FullNameSplit = FullName.split("\\<");

            return FullNameSplit[0].split("\\.")[FullNameSplit[0].split("\\.").length - 1];
        }
        else
        {
            String[] FullNameSplit = FullName.split("\\.");

            return FullNameSplit[FullNameSplit.length - 1];
        }
    }

    @Override
    public String toDCA() {
        return this.element.getSimpleName().toString() + "\n";
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

