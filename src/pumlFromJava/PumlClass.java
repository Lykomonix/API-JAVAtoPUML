package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class PumlClass extends PumlElement {

    private TypeElement element;

    private ArrayList<PumlLink> links;

    private ArrayList<PumlVariable> variableList = new ArrayList<>();
    private ArrayList<PumlMethod> methodList = new ArrayList<>();

    public PumlClass(Element element)
    {
        this.element = (TypeElement)element;
        RetrieveVariables();
        links = PumlLink.RetrieveLinks(this.element, variableList);
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        for(PumlVariable variable : variableList)
        {
            if(variable.getKind() == VariableKind.PRIMITIVE)
            {
                builder.append(variable.toDCA());
            }
        }

        builder.append("}\n");

        return builder.toString();
    }

    @Override
    public String toDCC() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        for(PumlVariable variable : variableList)
        {
            builder.append(variable.toDCC());
        }

        builder.append("}\n");

        return builder.toString();
    }

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

    private void RetrieveMethods()
    {
        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.METHOD)
            {
                methodList.add(new PumlMethod(enclosedElement, MethodKind.CONSTRUCT));
            }
        }
    }

    public ArrayList<PumlLink> getLinks() {
        return links;
    }
}
