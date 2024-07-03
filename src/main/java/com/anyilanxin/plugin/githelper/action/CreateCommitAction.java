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
package com.anyilanxin.plugin.githelper.action;

import com.anyilanxin.plugin.githelper.model.CommitTemplate;
import com.anyilanxin.plugin.githelper.model.MessageStorage;
import com.anyilanxin.plugin.githelper.model.ScopeInfos;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageStorage;
import com.anyilanxin.plugin.githelper.storage.GitCommitScopeService;
import com.anyilanxin.plugin.githelper.ui.commit.CommitDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.Nullable;

/**
 * @author fulin
 */
public class CreateCommitAction extends AnAction implements DumbAware {

    private final GitCommitMessageHelperSettings settings;


    public CreateCommitAction() {
        this.settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @Override
    public void actionPerformed(@Nullable AnActionEvent actionEvent) {
        final CommitMessageI commitPanel = getCommitPanel(actionEvent);
        if (commitPanel == null) {
            return;
        }
        Project project = actionEvent.getProject();
        assert project != null;
        GitCommitMessageStorage storage = project.getService(GitCommitMessageStorage.class);
        GitCommitMessageStorage state = storage.getState();
        assert state != null;
        MessageStorage messageStorage = state.getMessageStorage();


        ScopeInfos scope = GitCommitScopeService.getInstance().getScope();
        CommitDialog dialog = new CommitDialog(
                project,
                settings,
                messageStorage.getCommitTemplate(),
                scope
        );
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(dialog.getCommitMessage(settings).toString());
            storage.getMessageStorage().setCommitTemplate(null);
        }
        if (dialog.getExitCode() == DialogWrapper.CANCEL_EXIT_CODE) {
            CommitTemplate commitMessageTemplate = dialog.getCommitMessageTemplate();
            storage.getMessageStorage().setCommitTemplate(commitMessageTemplate);
        }
    }

    @Nullable
    private static CommitMessageI getCommitPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitMessageI) data;
        }
        return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
    }
}