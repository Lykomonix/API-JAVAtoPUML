package pumlFromJava;

import javax.lang.model.element.Element;

public class PumlLink
{
    private PumlElement firstElement;
    private PumlElement secondElement;
    private LinkType linkType;
    PumlLink(PumlElement pumlElement1,PumlElement pumlElement2, LinkType linkType)
    {
        this.firstElement = pumlElement1;
        this.secondElement = pumlElement2;
        this.linkType = linkType;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public PumlElement getFirstElement() {
        return firstElement;
    }

    public PumlElement getSecondElement() {
        return secondElement;
    }
}

enum LinkType
{
    EXTENDS,IMPLEMENT;
}
