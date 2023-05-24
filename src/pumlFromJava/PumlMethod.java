package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.sql.SQLSyntaxErrorException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class PumlMethod extends PumlElement {

    private Element element;
    private ExecutableElement executableElement;
    private ArrayList<PumlParameter> parameters = new ArrayList<>();

    public PumlMethod(Element element) {
        this.element = element;
        this.executableElement = (ExecutableElement) element;
        RetrieveParameters();
    }

    private void RetrieveParameters()
    {
        for(VariableElement variableElement : this.executableElement.getParameters())
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

        builder.append(") ");

        if(!this.executableElement.getReturnType().toString().equals("void"))
        {
            builder.append(": " + TypeToString(this.executableElement.getReturnType()));
        }

        builder.append("\n");

        return builder.toString();
    }
}