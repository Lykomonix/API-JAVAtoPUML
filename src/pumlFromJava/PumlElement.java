package pumlFromJava;

import javax.lang.model.element.Element;

/********************************************************************
 * PumlElement est une classe abstract implémenté par PumlVariable, PumlClass, PumlEnum, PumlInterface, PumlPackage et PumlDiagram
 ********************************************************************/
public abstract class PumlElement {
    public abstract String toDCA();
    public abstract String toDCC();

}
