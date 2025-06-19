package org.sam.Branches;

import org.sam.Branch;

public class Tree extends Branch {
    public Tree() {
        //children.add(new UnderAttackByPkerBranch());
    }

    @Override
    public boolean validate() {
        return true;
    }
}
