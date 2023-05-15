package pumlFromJava;

import javax.lang.model.element.Element;

public class PumlLink
{
    private String firstElement;
    private String secondElement;
    private LinkType linkType;
    PumlLink(String firstElement,String secondElement, LinkType linkType)
    {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.linkType = linkType;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }
}

enum LinkType
{
    EXTENDS,IMPLEMENTS,ASSOCIATE;
}
