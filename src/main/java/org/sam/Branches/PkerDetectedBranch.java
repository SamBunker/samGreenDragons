package org.sam.Branches;

import org.sam.Branch;
import org.sam.Variables;

public class PkerDetectedBranch extends Branch {
    private final Variables vars;

    pubic PkerDetectedBranch(Variables vars) {
        this.vars = vars;
    }

    @Override
    public boolean validate() {
        return vars.pKerDetected
                && 
    }

}
