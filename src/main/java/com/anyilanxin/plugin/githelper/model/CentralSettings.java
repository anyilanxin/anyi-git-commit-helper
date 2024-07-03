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

import com.anyilanxin.plugin.githelper.model.enums.TypeDisplayStyleEnum;

public class CentralSettings {

    private TypeDisplayStyleEnum typeDisplayStyle;

    private Integer typeDisplayNumber;

    private String skipCiDefaultValue;

    private Boolean skipCiDefaultApprove;

    private Boolean skipCiComboboxEnable;

    private Hidden hidden;


    public TypeDisplayStyleEnum getTypeDisplayStyle() {
        return typeDisplayStyle;
    }

    public void setTypeDisplayStyle(TypeDisplayStyleEnum typeDisplayStyle) {
        this.typeDisplayStyle = typeDisplayStyle;
    }

    public Integer getTypeDisplayNumber() {
        return typeDisplayNumber;
    }

    public void setTypeDisplayNumber(Integer typeDisplayNumber) {
        this.typeDisplayNumber = typeDisplayNumber;
    }

    public String getSkipCiDefaultValue() {
        return skipCiDefaultValue;
    }

    public void setSkipCiDefaultValue(String skipCiDefaultValue) {
        this.skipCiDefaultValue = skipCiDefaultValue;
    }

    public Boolean getSkipCiDefaultApprove() {
        return skipCiDefaultApprove;
    }

    public void setSkipCiDefaultApprove(Boolean skipCiDefaultApprove) {
        this.skipCiDefaultApprove = skipCiDefaultApprove;
    }

    public Boolean getSkipCiComboboxEnable() {
        return skipCiComboboxEnable;
    }

    public void setSkipCiComboboxEnable(Boolean skipCiComboboxEnable) {
        this.skipCiComboboxEnable = skipCiComboboxEnable;
    }

    public Hidden getHidden() {
        return hidden;
    }

    public void setHidden(Hidden hidden) {
        this.hidden = hidden;
    }

    public static class Hidden {
        private Boolean type;
        private Boolean scope;
        private Boolean gitmoji;
        private Boolean subject;
        private Boolean body;
        private Boolean changes;
        private Boolean closed;
        private Boolean skipCi;

        public Boolean getType() {
            return type;
        }

        public void setType(Boolean type) {
            this.type = type;
        }

        public Boolean getScope() {
            return scope;
        }

        public Boolean getGitmoji() {
            return gitmoji;
        }

        public void setGitmoji(Boolean gitmoji) {
            this.gitmoji = gitmoji;
        }

        public void setScope(Boolean scope) {
            this.scope = scope;
        }

        public Boolean getSubject() {
            return subject;
        }

        public void setSubject(Boolean subject) {
            this.subject = subject;
        }

        public Boolean getBody() {
            return body;
        }

        public void setBody(Boolean body) {
            this.body = body;
        }

        public Boolean getChanges() {
            return changes;
        }

        public void setChanges(Boolean changes) {
            this.changes = changes;
        }

        public Boolean getClosed() {
            return closed;
        }

        public void setClosed(Boolean closed) {
            this.closed = closed;
        }

        public Boolean getSkipCi() {
            return skipCi;
        }

        public void setSkipCi(Boolean skipCi) {
            this.skipCi = skipCi;
        }
    }

}
