package raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction;

public enum Access {
    PUBLIC, PRIVATE, PROTECTED, DEFAULT;

    @Override
    public String toString() {
        if(ordinal() == 0)
            return "+";
        if(ordinal() == 1)
            return "-";
        if(ordinal() == 2)
            return "#";
        if(ordinal() == 3)
            return "~";
        return super.toString();
    }

    public String toStringNames()
    {
        return super.toString();
    }

    public static Access fromString(String str)
    {
        for(Access acc : values()) {
            if(str.equalsIgnoreCase(acc.toStringNames()))
                return acc;
        }

        return null;
    }

    public static Access fromSymbol(String symbol) {
        switch (symbol) {
            case "+":
                return PUBLIC;
            case "-":
                return PRIVATE;
            case "#":
                return PROTECTED;
            case "~":
                return DEFAULT;
        }
        return null;
    }
}
