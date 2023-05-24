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

    /********************************************************************
     * GetElementTypeString est un getteur qui récupère le type des éléments. c'est une fonction liée à la classe
     * in: Element element
     * out: String
     ********************************************************************/
    public static String GetElementTypeString(Element element)
    {
        String FullName = element.asType().toString();

        return ExtractTypeString(FullName);
    }

    /********************************************************************
     * TypeToString est une fonction liée à la classe qui transforme un type en String
     * in: TypeMirror type
     * out: String
     ********************************************************************/
    public static String TypeToString(TypeMirror type)
    {
        String FullName = type.toString();

        return ExtractTypeString(FullName);
    }

    /********************************************************************
     * ExtractTypeString est un fonction lié à la classe. retourne le type d'une chaine de caractère.
     * in: String FullName
     * out: String
     ********************************************************************/
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
