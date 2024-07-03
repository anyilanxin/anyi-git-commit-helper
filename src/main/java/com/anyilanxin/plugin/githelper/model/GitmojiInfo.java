/*
 * Copyright (c) 2018-present FuLin. All rights reserved.
 * Copyright © 2024 anyilanxin xuanhongzhou(anyilanxin@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anyilanxin.plugin.githelper.model;


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
    public String description;


    public GitmojiInfo() {
    }

    public GitmojiInfo(String emoji, String code, String description) {
        this.emoji = StringEscapeUtils.escapeJava(emoji);
        this.code = StringEscapeUtils.escapeJava(code);
        this.description = StringEscapeUtils.escapeJava(description);
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
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(emoji)) {
            builder.append(getEmoji()).append(" ");
        }
        if (StringUtils.isNotBlank(code)) {
            builder.append(getCode()).append(" ");
        }
        if (StringUtils.isNotBlank(description)) {
            builder.append(getDescription()).append(" ");
        }
        return builder.toString();
    }

}
