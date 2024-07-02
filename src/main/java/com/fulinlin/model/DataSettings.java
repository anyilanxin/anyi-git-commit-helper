package com.fulinlin.model;

import java.util.List;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-05 21:22
 **/
public class DataSettings {
    private String template;
    private List<TypeAlias> typeAliases;
    private List<ScopeAlias> scopeAliases;
    private List<GitmojiInfo> gitmojis;
    private List<String> skipCis;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<TypeAlias> getTypeAliases() {
        return typeAliases;
    }

    public void setTypeAliases(List<TypeAlias> typeAliases) {
        this.typeAliases = typeAliases;
    }

    public List<ScopeAlias> getScopeAliases() {
        return scopeAliases;
    }

    public List<GitmojiInfo> getGitmojis() {
        return gitmojis;
    }

    public void setGitmojis(List<GitmojiInfo> gitmojis) {
        this.gitmojis = gitmojis;
    }

    public void setScopeAliases(List<ScopeAlias> scopeAliases) {
        this.scopeAliases = scopeAliases;
    }

    public List<String> getSkipCis() {
        return skipCis;
    }

    public void setSkipCis(List<String> skipCis) {
        this.skipCis = skipCis;
    }
}
