package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.sql.SQLSyntaxErrorException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class PumlMethod extends PumlElement {

    private Element element;
    private ArrayList<PumlParameter> parameters = new ArrayList<>();

    public PumlMethod(Element element) {
        this.element = element;
        RetrieveParameters();
    }

    private void RetrieveParameters()
    {
        ExecutableElement executableElement = (ExecutableElement) this.element;

        for(VariableElement variableElement : executableElement.getParameters())
        {
            this.parameters.add(new PumlParameter(variableElement));
        }
    }

    @Override
    public String toDCA() {
        return null;
    }

    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        for (Modifier modifier : this.element.getModifiers()) {
            switch (modifier) {
                case STATIC -> builder.append("{static} ");
                case ABSTRACT -> builder.append("{abstract} ");

                case PUBLIC -> {
                    builder.append("+ ");

                    if (this.element.getSimpleName().toString().equals("<init>")) {
                        builder.append("<<create>> ");
                    }
                }
                case PRIVATE -> builder.append("- ");
                case PROTECTED -> builder.append("~ ");
            }
        }

        if (this.element.getSimpleName().toString().equals("<init>"))
        {
            builder.append(this.element.getEnclosingElement().getSimpleName() + "(");
        }
        else
        {
            builder.append(this.element.getSimpleName() + "(");
        }

        for(int i=0; i<parameters.size(); i++)
        {
            builder.append(parameters.get(i).toDCC());
            if(parameters.size() != 1 && i != parameters.size() - 1)
            {
                builder.append(", ");
            }
        }

        builder.append(")\n");

        return builder.toString();
    }
}