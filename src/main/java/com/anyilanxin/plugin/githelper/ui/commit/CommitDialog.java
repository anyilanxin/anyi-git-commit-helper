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
package com.anyilanxin.plugin.githelper.ui.commit;

import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.anyilanxin.plugin.githelper.model.CommitTemplate;
import com.anyilanxin.plugin.githelper.model.ScopeInfos;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommitDialog extends DialogWrapper {

    private final CommitPanel panel;

    public CommitDialog(@Nullable Project project,
                        GitCommitMessageHelperSettings settings,
                        CommitTemplate commitMessageTemplate,
                        ScopeInfos scopeInfos) {
        super(project);
        panel = new CommitPanel(project, settings, commitMessageTemplate, scopeInfos);
        setTitle(PluginBundle.get("commit.panel.title"));
        setOKButtonText(PluginBundle.get("commit.panel.ok.button"));
        setCancelButtonText(PluginBundle.get("commit.panel.cancel.button"));
        init();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }


    public CommitMessage getCommitMessage(GitCommitMessageHelperSettings settings) {
        return panel.getCommitMessage(settings);
    }

    public CommitTemplate getCommitMessageTemplate() {
        return panel.getCommitMessageTemplate();
    }


}