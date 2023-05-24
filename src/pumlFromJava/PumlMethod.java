package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.sql.SQLSyntaxErrorException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

/********************************************************************
 * PumlMethode hérite de PumlElement. la classe permet de s'occuper des méthodes des classes et interfaces
 ********************************************************************/
public class PumlMethod extends PumlElement {

    private Element element;
    private ExecutableElement executableElement;
    private ArrayList<PumlParameter> parameters = new ArrayList<>();

    /********************************************************************
     * PumlMethod est un constructeur
     * in: Element element
     * out: NULL
     ********************************************************************/
    public PumlMethod(Element element) {
        this.element = element;
        this.executableElement = (ExecutableElement) element;
        RetrieveParameters();
    }

    /********************************************************************
     * RetrieveParameters permet de récupérer les paramètres des méthodes
     * in: Ø
     * out: void
     ********************************************************************/
    private void RetrieveParameters()
    {
        for(VariableElement variableElement : this.executableElement.getParameters())
        {
            this.parameters.add(new PumlParameter(variableElement));
        }
    }

    /********************************************************************
     * toDCA est redéfini et permet de s'occuper des méthodes dans un DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        return null;
    }

    /********************************************************************
     * toDCC est redéfini et permet de s'occuper des méthodes dans un DCC
     * in: Ø
     * out: String
     ********************************************************************/
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