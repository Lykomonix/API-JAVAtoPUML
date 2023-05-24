package pumlFromJava;

import javax.lang.model.element.VariableElement;

/********************************************************************
 * PumlParameter hérite de PumlElement et permet de gérer les paramètres des méthodes
 ********************************************************************/
public class PumlParameter extends PumlElement{

    private VariableElement variableElement;

    /********************************************************************
     * PumlParameter est un constructeur
     * in: VariableElement variableElement
     * out: NULL
     ********************************************************************/
    public PumlParameter(VariableElement variableElement)
    {
        this.variableElement = variableElement;
    }

    /********************************************************************
     * toDCA est un fonction redéfini qui permet de gérer les paramètres dans les DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        return null;
    }

    /********************************************************************
     * toDCC est un fonction redéfini qui permet de gérer les paramètres dans les DCC
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.variableElement.getSimpleName() + " : ");

        builder.append(GetElementTypeString(variableElement));

        return builder.toString();
    }
}
