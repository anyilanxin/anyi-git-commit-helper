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
package com.anyilanxin.plugin.githelper.configurable;

import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.anyilanxin.plugin.githelper.ui.central.CentralSettingPanel;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CentralSettingConfigurable implements SearchableConfigurable {

    private CentralSettingPanel centralSettingPanel;

    private GitCommitMessageHelperSettings settings;

    public CentralSettingConfigurable() {
        settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "plugins.gitcommitmessagehelper";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (centralSettingPanel == null) {
            centralSettingPanel = new CentralSettingPanel(settings);
        }
        return centralSettingPanel.getMainPanel();
    }

    @Override
    public void reset() {
        centralSettingPanel.reset(settings);
    }

    @Override
    public boolean isModified() {
        return centralSettingPanel.isModified(settings);
    }

    @Override
    public void apply() {
        settings.setCentralSettings(centralSettingPanel.getSettings().getCentralSettings());
        settings = centralSettingPanel.getSettings().clone();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "AyGitCommitHelp";
    }
}
