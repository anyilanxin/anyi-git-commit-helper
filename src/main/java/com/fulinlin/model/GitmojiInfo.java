package com.fulinlin.model;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;


/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-06 21:11
 **/
public class GitmojiInfo extends DomainObject {
    public String emoji;
    public String code;
    public String name;
    public String description;


    public GitmojiInfo() {
    }

    public GitmojiInfo(String emoji, String code, String name, String description) {
        this.emoji = StringEscapeUtils.escapeJava(emoji);
        this.code = StringEscapeUtils.escapeJava(code);
        this.name = StringEscapeUtils.escapeJava(name);
        this.description = StringEscapeUtils.escapeJava(description);
    }

    public String getName() {
        return StringEscapeUtils.unescapeJava(name);
    }

    public void setName(String name) {
        this.name = StringEscapeUtils.escapeJava(name);
    }

    public String getDescription() {
        return StringEscapeUtils.unescapeJava(description);
    }

    public void setDescription(String description) {
        this.description = StringEscapeUtils.escapeJava(description);
    }

    public String getEmoji() {
        return StringEscapeUtils.unescapeJava(emoji);
    }

    public void setEmoji(String emoji) {
        this.emoji = StringEscapeUtils.escapeJava(emoji);
    }

    public String getCode() {
        return StringEscapeUtils.unescapeJava(code);
    }

    public void setCode(String code) {
        this.code = StringEscapeUtils.escapeJava(code);
    }

    @Override
    public String toString() {
        if (StringUtils.isBlank(this.getEmoji())) {
            return this.getDescription();
        } else {
            return String.format("%s - %s", this.getEmoji(), this.getDescription());
        }
    }

}
