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
package com.anyilanxin.plugin.githelper.ui.setting;

import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AliasEditor extends DialogWrapper {
    private JPanel myPanel;
    private JTextField titleField;
    private JTextField descriptionField;
    private JLabel titleFieldLabel;
    private JLabel descriptionFieldLabel;


    public interface Validator {
        boolean isOK(String name, String value);
    }

    public AliasEditor(String title, String macroName, String value) {
        super(true);
        setTitle(title);
        titleFieldLabel.setText(PluginBundle.get("setting.alias.field.title"));
        descriptionFieldLabel.setText(PluginBundle.get("setting.alias.field.description"));
        titleField.setText(macroName);
        descriptionField.setText(value);
        init();
    }

    @Override
    public String getTitle() {
        return titleField.getText().trim();
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }

}