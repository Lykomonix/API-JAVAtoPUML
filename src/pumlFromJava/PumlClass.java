package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

/********************************************************************
 * PumlClass hérite de la classe PumlElement
 * elle permet de gérer les classes dans les diagrammes
 ********************************************************************/
public class PumlClass extends PumlElement {

    private TypeElement element;

    private ArrayList<PumlLink> links;

    private ArrayList<PumlVariable> variableList = new ArrayList<>();
    private ArrayList<PumlMethod> methodList = new ArrayList<>();

    /********************************************************************
     * C'est le Constructeur
     * in: Element element
     * out: NULL
     ********************************************************************/
    public PumlClass(Element element)
    {
        this.element = (TypeElement)element;
        RetrieveVariables();
        RetrieveMethods();
        links = PumlLink.RetrieveLinks(this.element, variableList, methodList);
    }

    /********************************************************************
     * getName est un getteur qui récupère le nom de la classe
     ********************************************************************/
    public String getName() {
        return this.element.getSimpleName().toString();
    }

    /********************************************************************
     * toDCA est une fonction hérité permet de faire les classes pour les DCA
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        for(PumlVariable variable : variableList)
        {
            if(variable.getVariableKind() == VariableKind.PRIMITIVE)
            {
                builder.append(variable.toDCA());
            }
        }

        builder.append("}\n");

        return builder.toString();
    }

    /********************************************************************
     * toDCC est une fonction hérité permet de faire les classes pour les DCC
     * in: Ø
     * out: String
     ********************************************************************/
    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        for(PumlVariable variable : variableList)
        {
            if(variable.getVariableKind() == VariableKind.PRIMITIVE)
            {
                builder.append(variable.toDCC());
            }
        }

        for(PumlMethod method : methodList)
        {
            builder.append(method.toDCC());
        }

        builder.append("}\n");

        return builder.toString();
    }

    /********************************************************************
     * RetrieveVariables permet de récupérer les champs d'une classe
     * in: Ø
     * out: void
     ********************************************************************/
    private void RetrieveVariables()
    {
        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.FIELD && enclosedElement.asType().getKind() == TypeKind.DECLARED)
            {
                variableList.add(new PumlVariable(enclosedElement,VariableKind.OBJECT));
            }
            else if(enclosedElement.getKind() == ElementKind.FIELD)
            {
                variableList.add(new PumlVariable(enclosedElement,VariableKind.PRIMITIVE));
            }
        }
    }

    /********************************************************************
     * RetrieveMethods permet de récupérer les méthodes d'une classe
     * in: Ø
     * out: void
     ********************************************************************/
    private void RetrieveMethods()
    {
        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.METHOD || enclosedElement.getKind() == ElementKind.CONSTRUCTOR)
            {
                methodList.add(new PumlMethod(enclosedElement));
            }
        }
    }

    /********************************************************************
     * getLinks est un getteur qui récupère la liste des liens d'une classe
     * in: Ø
     * out: ArrayList<PumlLink>
     ********************************************************************/
    public ArrayList<PumlLink> getLinks() {
        return links;
    }
}
