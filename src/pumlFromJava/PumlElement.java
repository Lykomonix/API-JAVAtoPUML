package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;

/********************************************************************
 * PumlElement est une classe abstract qui permet de forcer la définition des fonctions toDCA et toDCC dans les classes qui l'implémentent
 ********************************************************************/
public abstract class PumlElement {
    public abstract String toDCA();
    public abstract String toDCC();

    public static String GetElementTypeString(Element element)
    {
        String FullName = element.asType().toString();

        return ExtractTypeString(FullName);
    }

    public static String TypeToString(TypeMirror type)
    {
        String FullName = type.toString();

        return ExtractTypeString(FullName);

    }

    private static String ExtractTypeString(String FullName)
    {
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
