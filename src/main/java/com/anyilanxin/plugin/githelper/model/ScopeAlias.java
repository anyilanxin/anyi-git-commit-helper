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
