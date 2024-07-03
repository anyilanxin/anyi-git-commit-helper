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

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MojiEditor extends DialogWrapper {
    private JPanel myMojiEditor;
    private JTextField codeField;
    private JTextField emojiField;
    private JTextField descriptionField;


    public interface Validator {
        boolean isOK(String name, String value);
    }

    public MojiEditor(String code, String emoji, String description) {
        super(true);
        setTitle("Moji editor");
        codeField.setText(code);
        emojiField.setText(emoji);
        descriptionField.setText(description);
        init();
    }

    public JPanel getMyMojiEditor() {
        return myMojiEditor;
    }

    public String getCode() {
        return codeField.getText().trim();
    }

    public String getEmoji() {
        return emojiField.getText().trim();
    }


    public String getDescription() {
        return descriptionField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myMojiEditor;
    }
}
