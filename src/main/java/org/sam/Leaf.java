package org.sam;

public abstract class Leaf {
    protected String name;

    public Leaf() {
        super();
        name = "Un-named";
    }

    public abstract boolean validate();
    public abstract void execute();
}
