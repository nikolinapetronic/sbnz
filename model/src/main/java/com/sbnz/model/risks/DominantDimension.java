package com.sbnz.model.risks;

import com.sbnz.model.common.BaseFact;

public class DominantDimension extends BaseFact {

    private String value;

    public DominantDimension() {
    }

    public DominantDimension(String studentId, String value) {
        super(studentId);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
