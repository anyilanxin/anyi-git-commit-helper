package com.fulinlin.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-06 21:11
 **/
public class ScopeInfos {

    private List<ScopeAlias> scopeAlias = new ArrayList<>();

    public List<ScopeAlias> getScopeAlias() {
        return scopeAlias;
    }

    public void setScopeAlias(List<ScopeAlias> scopeAlias) {
        this.scopeAlias = scopeAlias;
    }
}
