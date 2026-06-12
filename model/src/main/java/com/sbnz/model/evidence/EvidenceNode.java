package com.sbnz.model.evidence;

import com.sbnz.model.common.BaseFact;


// cinjenica koja govori da za odredjenog studenta postoji odredjeni dokaz

public class EvidenceNode extends BaseFact {

    private String name;

    public EvidenceNode() {
    }

    public EvidenceNode(String studentId, String name) {
        super(studentId);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
