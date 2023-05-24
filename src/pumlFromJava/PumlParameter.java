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

        builder.append(TypeToString());

        return builder.toString();
    }

    public String TypeToString()
    {
        String FullName = variableElement.asType().toString();

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
}
