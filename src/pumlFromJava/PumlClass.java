package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
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

        for(PumlLink link : getLinks())
        {
            if(!link.getSecondElement().equals("Object") && link.getLinkType() == LinkType.EXTENDS)
            {
                builder.append(link.getFirstElement() + " --|> " + link.getSecondElement() + "\n");
            }
            else if(link.getLinkType() == LinkType.IMPLEMENT)
            {
                builder.append(link.getFirstElement() + " ..|> " + link.getSecondElement() + "\n");
            }
        }

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
    public ArrayList<PumlLink> getLinks()
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        links.add(this.getSuperClass());
        links.addAll(this.getInterfaces());

        return links;
    }

    private PumlLink getSuperClass()
    {
        TypeElement typeElement = this.element;

        String[] superClassFullName = typeElement.getSuperclass().toString().split("\\.");

        PumlLink link = new PumlLink(typeElement.getSimpleName().toString(),
                superClassFullName[superClassFullName.length - 1], LinkType.EXTENDS);

        return link;
    }

    private ArrayList<PumlLink> getInterfaces()
    {
        ArrayList<PumlLink> links = new ArrayList<>();

        TypeElement typeElement = this.element;

        for(TypeMirror typeMirror : typeElement.getInterfaces())
        {
            String[] interfaceFullName = typeMirror.toString().split("\\.");

            PumlLink link = new PumlLink(typeElement.getSimpleName().toString(),
                    interfaceFullName[interfaceFullName.length - 1], LinkType.IMPLEMENT);

            links.add(link);
        }

        return links;
    }
}
