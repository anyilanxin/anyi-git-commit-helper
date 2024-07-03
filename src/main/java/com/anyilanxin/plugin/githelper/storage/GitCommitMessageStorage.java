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
package com.anyilanxin.plugin.githelper.storage;

import com.anyilanxin.plugin.githelper.model.MessageStorage;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(name = "ay_git_commit_message_storage",
        storages = {@Storage(value = "ay_git_commit_message_storage.xml", roamingType = RoamingType.DISABLED)})
public class GitCommitMessageStorage implements PersistentStateComponent<GitCommitMessageStorage> {

    private MessageStorage messageStorage;

    public GitCommitMessageStorage() {
    }

    @Nullable
    @Override
    public GitCommitMessageStorage getState() {
        if (this.messageStorage == null) {
            messageStorage = new MessageStorage();
        }
        return this;
    }

    public MessageStorage getMessageStorage() {
        return messageStorage;
    }

    public void setMessageStorage(MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
    }

    @Override
    public void loadState(@NotNull GitCommitMessageStorage gitCommitMessageStorage) {
        XmlSerializerUtil.copyBean(gitCommitMessageStorage, this);
    }
}
