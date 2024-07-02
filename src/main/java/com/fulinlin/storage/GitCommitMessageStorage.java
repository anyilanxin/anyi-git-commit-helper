package com.fulinlin.storage;

import com.fulinlin.model.MessageStorage;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(name = "ay_git_commit_message_storage",
        storages = {@Storage(value = "ay_git_commit_message_storage.xml")})
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
