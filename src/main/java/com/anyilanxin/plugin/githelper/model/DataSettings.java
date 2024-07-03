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

import java.util.List;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-05 21:22
 **/
public class DataSettings {
    private String template;
    private List<TypeAlias> typeAliases;
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

    public List<GitmojiInfo> getGitmojis() {
        return gitmojis;
    }

    public void setGitmojis(List<GitmojiInfo> gitmojis) {
        this.gitmojis = gitmojis;
    }

    public List<String> getSkipCis() {
        return skipCis;
    }

    public void setSkipCis(List<String> skipCis) {
        this.skipCis = skipCis;
    }
}
