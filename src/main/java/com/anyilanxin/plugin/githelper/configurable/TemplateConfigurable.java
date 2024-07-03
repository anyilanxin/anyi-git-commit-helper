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

import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.anyilanxin.plugin.githelper.ui.setting.TemplateEditPanel;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 这个类Settings 中的属性被创建的时候
 *
 * @author: fulin
 */
public class TemplateConfigurable implements SearchableConfigurable {

    private TemplateEditPanel templateEditPanel;

    private GitCommitMessageHelperSettings settings;


    public TemplateConfigurable() {
        settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "plugins.gitcommitmessagehelper.template";
    }


    @Nullable
    @Override
    public JComponent createComponent() {
        if (templateEditPanel == null) {
            templateEditPanel = new TemplateEditPanel(settings);
        }
        return templateEditPanel.getMainPanel();
    }


    @Nullable
    @Override
    public String getHelpTopic() {
        return "help.gitcommitmessagehelper.configuration";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return PluginBundle.get("setting.configurable.template");
    }


    public void reset() {
        if (templateEditPanel != null) {
            templateEditPanel.reset(settings);
        }
    }

    @Override
    public boolean isModified() {
        return templateEditPanel != null && templateEditPanel.isSettingsModified(settings);
    }


    @Override
    public void apply() {
        settings.setDateSettings(templateEditPanel.getSettings().getDateSettings());
        settings = templateEditPanel.getSettings().clone();
    }
}