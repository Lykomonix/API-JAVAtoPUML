package pumlFromJava;

import javax.lang.model.element.VariableElement;

public class PumlParameter extends PumlElement{

    private VariableElement variableElement;

    public PumlParameter(VariableElement variableElement)
    {
        this.variableElement = variableElement;
    }

    @Override
    public String toDCA() {
        return null;
    }

    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.variableElement.getSimpleName() + " : ");

        builder.append(GetElementTypeString(variableElement));

        return builder.toString();
    }
}
