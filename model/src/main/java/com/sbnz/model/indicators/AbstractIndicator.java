package com.sbnz.model.indicators;

import com.sbnz.model.common.BaseFact;

public abstract class AbstractIndicator extends BaseFact {
    
    public AbstractIndicator() {
    }

    public AbstractIndicator(String studentId) {
        super(studentId);
    }
}
