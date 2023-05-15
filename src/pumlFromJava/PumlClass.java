package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PumlClass extends PumlElement implements PumlLinkable{
    private TypeElement element;
    private List<PumlLink> links= new ArrayList<>();
    public PumlClass(Element element)
    {
        this.element = (TypeElement)element;
    }

    public String getName() {
        return this.element.getSimpleName().toString();
    }

    @Override
    public String toDCA() {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + this.element.getSimpleName() + "\n");

        builder.append("{\n");

        builder.append(getPrimitiveVariables());

        builder.append("}\n");

        ArrayList<PumlLink> test = getDCALinks();

        return builder.toString();
    }

    private String getPrimitiveVariables()
    {
        StringBuilder builder = new StringBuilder();

        for(Element enclosedElement : this.element.getEnclosedElements())
        {
            if(enclosedElement.getKind() == ElementKind.FIELD)
            {
                if(enclosedElement.asType().getKind() != TypeKind.DECLARED)
                {
                    builder.append(enclosedElement.getSimpleName() + "\n");
                }
            }
        }

        return builder.toString();
    }
    @Override
    public ArrayList<PumlLink> getDCALinks()
    {
        ArrayList<PumlLink> Links = new ArrayList<>();

        for(Modifier modifier : this.element.getModifiers())
        {
            System.out.println(modifier.name().toString());
        }

        return Links;
    }
}
