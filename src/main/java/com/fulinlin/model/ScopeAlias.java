package com.fulinlin.model;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;


/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-06 21:11
 **/
public class ScopeAlias extends DomainObject {

    public String title;

    public String description;

    public ScopeAlias() {
    }

    public ScopeAlias(String title, String description) {
        this.title = StringEscapeUtils.escapeJava(title);
        this.description = StringEscapeUtils.escapeJava(description);
    }

    public String getTitle() {
        return StringEscapeUtils.unescapeJava(title);
    }

    public void setTitle(String title) {
        this.title = StringEscapeUtils.escapeJava(title);
    }

    public String getDescription() {
        return StringEscapeUtils.unescapeJava(description);
    }

    public void setDescription(String description) {
        this.description = StringEscapeUtils.escapeJava(description);
    }

    @Override
    public String toString() {
        if (StringUtils.isBlank(this.getTitle())) {
            return this.getDescription();
        } else {
            return String.format("%s - %s", this.getTitle(), this.getDescription());
        }
    }

}
