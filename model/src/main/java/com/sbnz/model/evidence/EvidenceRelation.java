package com.sbnz.model.evidence;

import java.io.Serializable;

// veza u stablu/hijerarhiji dokaza, npr. EvidenceRelation("ExhaustionIndicator", "CoreBurnoutEvidence")
// sto znaci ExhaustionIndicator podrzava CoreBurnoutEvidence

public class EvidenceRelation implements Serializable {

    private String child;
    private String parent;

    public EvidenceRelation() {
    }

    public EvidenceRelation(String child, String parent) {
        this.child = child;
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
