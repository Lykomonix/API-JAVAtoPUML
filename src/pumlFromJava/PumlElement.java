package pumlFromJava;

import javax.lang.model.element.Element;

/********************************************************************
 * PumlElement est une classe abstract qui permet de forcer la définition des fonctions toDCA et toDCC dans les classes qui l'implémentent
 ********************************************************************/
public abstract class PumlElement {
    public abstract String toDCA();
    public abstract String toDCC();

}
