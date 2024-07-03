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
package com.anyilanxin.plugin.githelper.ui.central;

import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.anyilanxin.plugin.githelper.model.DataSettings;
import com.anyilanxin.plugin.githelper.model.enums.TypeDisplayStyleEnum;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.JBIntSpinner;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class CentralSettingPanel {
    protected GitCommitMessageHelperSettings settings;
    private JPanel mainPanel;
    private JPanel hiddenPanel;
    private JPanel typePanel;
    private JPanel skipCiPanel;
    private JRadioButton typeComboboxRadioButton;
    private JRadioButton typeRadioRadioButton;
    private JRadioButton typeMixingRadioButton;
    private JBIntSpinner typeDisplayNumberSpinner;
    private JCheckBox skipCiEnableCheckBox;
    private JComboBox<String> skipCiComboBox;
    private JCheckBox skipCiDefaultApproveCheckedBox;

    //********************* hidden *********************//
    private JCheckBox typeCheckBox;
    private JCheckBox scopeCheckBox;
    private JCheckBox subjectCheckBox;
    private JCheckBox bodyCheckBox;
    private JCheckBox changesCheckBox;
    private JCheckBox closedCheckBox;
    private JCheckBox skipCiCheckBox;
    private JLabel typeDiskPlayStyleLabel;
    private JLabel typeDisplayNumberLabel;
    private JLabel skipCiDefaultValueLabel;
    private JLabel skipEnableComboboxLabel;
    private JCheckBox gitmoji;


    public CentralSettingPanel(GitCommitMessageHelperSettings settings) {
        //Get setting
        this.settings = settings.clone();
        // Init  description
        typePanel.setBorder(IdeBorderFactory.createTitledBorder(PluginBundle.get("setting.central.type.panel.title"), true));
        skipCiPanel.setBorder(IdeBorderFactory.createTitledBorder(PluginBundle.get("setting.central.skip.ci.panel.title"), true));
        hiddenPanel.setBorder(IdeBorderFactory.createTitledBorder(PluginBundle.get("setting.central.hidden.panel.title"), true));
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(typeComboboxRadioButton);
        buttonGroup.add(typeRadioRadioButton);
        buttonGroup.add(typeMixingRadioButton);
        typeDiskPlayStyleLabel.setText(PluginBundle.get("setting.central.type.style"));
        typeDisplayNumberLabel.setText(PluginBundle.get("setting.central.type.number"));
        typeDisplayNumberSpinner.setToolTipText(PluginBundle.get("setting.central.type.number.tooltip"));
        typeComboboxRadioButton.setText(PluginBundle.get("setting.central.type.combobox.button"));
        typeRadioRadioButton.setText(PluginBundle.get("setting.central.type.radio.button"));
        typeMixingRadioButton.setText(PluginBundle.get("setting.central.type.mixing.button"));
        // Init  skip ci option
        skipCiDefaultValueLabel.setText(PluginBundle.get("setting.central.skip.ci.enable.default"));
        skipEnableComboboxLabel.setText(PluginBundle.get("setting.central.skip.ci.enable.selection"));
        skipCiEnableCheckBox.setText(PluginBundle.get("setting.central.skip.ci.enable.checkbox"));
        skipCiDefaultApproveCheckedBox.setText(PluginBundle.get("setting.central.skip.ci.default.checked.checkbox"));
        DataSettings dateSettings = settings.getDateSettings();
        List<String> skipCis = dateSettings.getSkipCis();
        for (String skipCi : skipCis) {
            skipCiComboBox.addItem(skipCi);
        }
        // Init
        typeComboboxRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                typeDisplayNumberSpinner.setEnabled(false);
            }
        });
        typeRadioRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                typeDisplayNumberSpinner.setEnabled(true);
            }
        });
        typeMixingRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                typeDisplayNumberSpinner.setEnabled(true);
            }
        });
        // Init  Component
        initComponent(settings);

    }

    public GitCommitMessageHelperSettings getSettings() {
        // Type Display Style Option
        int number = typeDisplayNumberSpinner.getNumber();
        if (typeComboboxRadioButton.isSelected()) {
            settings.getCentralSettings().setTypeDisplayStyle(TypeDisplayStyleEnum.CHECKBOX);
        } else if (typeRadioRadioButton.isSelected()) {
            settings.getCentralSettings().setTypeDisplayStyle(TypeDisplayStyleEnum.RADIO);
        } else if (typeMixingRadioButton.isSelected()) {
            settings.getCentralSettings().setTypeDisplayStyle(TypeDisplayStyleEnum.MIXING);
        }
        settings.getCentralSettings().setTypeDisplayNumber(number);
        // Skip CI Option
        settings.getCentralSettings().setSkipCiDefaultApprove(skipCiDefaultApproveCheckedBox.isSelected());
        if (skipCiComboBox.getSelectedItem() != null) {
            settings.getCentralSettings().setSkipCiDefaultValue(skipCiComboBox.getSelectedItem().toString());
        }
        settings.getCentralSettings().setSkipCiComboboxEnable(skipCiEnableCheckBox.isSelected());
        // Hidden Option
        // settings.getCentralSettings().getHidden().setSubject(subjectCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setType(typeCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setScope(scopeCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setGitmoji(gitmoji.isSelected());
        settings.getCentralSettings().getHidden().setBody(bodyCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setChanges(changesCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setClosed(closedCheckBox.isSelected());
        settings.getCentralSettings().getHidden().setSkipCi(skipCiCheckBox.isSelected());
        return settings;
    }


    public void reset(GitCommitMessageHelperSettings settings) {
        this.settings = settings.clone();
        initComponent(settings);
    }

    private void initComponent(GitCommitMessageHelperSettings settings) {
        // Type Display Style Option
        if (settings.getCentralSettings().getTypeDisplayStyle().equals(TypeDisplayStyleEnum.CHECKBOX)) {
            typeComboboxRadioButton.setSelected(true);
        } else if (settings.getCentralSettings().getTypeDisplayStyle().equals(TypeDisplayStyleEnum.RADIO)) {
            typeRadioRadioButton.setSelected(true);
        } else if (settings.getCentralSettings().getTypeDisplayStyle().equals(TypeDisplayStyleEnum.MIXING)) {
            typeMixingRadioButton.setSelected(true);
        } else {
            typeComboboxRadioButton.setSelected(true);
        }
        typeDisplayNumberSpinner.setNumber(settings.getCentralSettings().getTypeDisplayNumber());
        // Skip CI Option
        skipCiDefaultApproveCheckedBox.setSelected(settings.getCentralSettings().getSkipCiDefaultApprove());
        skipCiComboBox.setSelectedItem(settings.getCentralSettings().getSkipCiDefaultValue());
        skipCiEnableCheckBox.setSelected(settings.getCentralSettings().getSkipCiComboboxEnable());
        // Hidden Option
        typeCheckBox.setSelected(settings.getCentralSettings().getHidden().getType());
        gitmoji.setSelected(settings.getCentralSettings().getHidden().getGitmoji());
        scopeCheckBox.setSelected(settings.getCentralSettings().getHidden().getScope());
        //subjectCheckBox.setSelected(settings.getCentralSettings().getHidden().getSubject());
        subjectCheckBox.setEnabled(false);
        bodyCheckBox.setSelected(settings.getCentralSettings().getHidden().getBody());
        changesCheckBox.setSelected(settings.getCentralSettings().getHidden().getChanges());
        closedCheckBox.setSelected(settings.getCentralSettings().getHidden().getClosed());
        skipCiCheckBox.setSelected(settings.getCentralSettings().getHidden().getSkipCi());
    }


    public boolean isModified(GitCommitMessageHelperSettings data) {
        boolean isModified = false;
        // Type Display Style Option
        if (typeComboboxRadioButton.isSelected() != data.getCentralSettings().getTypeDisplayStyle()
                .equals(TypeDisplayStyleEnum.CHECKBOX)) {
            isModified = true;
        } else if (typeRadioRadioButton.isSelected() != data.getCentralSettings().getTypeDisplayStyle()
                .equals(TypeDisplayStyleEnum.RADIO)) {
            isModified = true;
        } else if (typeMixingRadioButton.isSelected() != data.getCentralSettings().getTypeDisplayStyle()
                .equals(TypeDisplayStyleEnum.MIXING)) {
            isModified = true;
        } else if (typeDisplayNumberSpinner.getNumber() != data.getCentralSettings().getTypeDisplayNumber()) {
            isModified = true;
        }
        // Skip CI Option
        else if (skipCiDefaultApproveCheckedBox.isSelected() != data.getCentralSettings().getSkipCiDefaultApprove()) {
            isModified = true;
        } else if (skipCiComboBox.getSelectedItem() != null && !skipCiComboBox.getSelectedItem().toString()
                .equals(data.getCentralSettings().getSkipCiDefaultValue())) {
            isModified = true;
        } else if (skipCiEnableCheckBox.isSelected() != data.getCentralSettings().getSkipCiComboboxEnable()) {
            isModified = true;
        }
        // Hidden Option
        else if (typeCheckBox.isSelected() != data.getCentralSettings().getHidden().getType()) {
            isModified = true;
        } else if (scopeCheckBox.isSelected() != data.getCentralSettings().getHidden().getScope()) {
            isModified = true;
        } else if (bodyCheckBox.isSelected() != data.getCentralSettings().getHidden().getBody()) {
            isModified = true;
        } else if (changesCheckBox.isSelected() != data.getCentralSettings().getHidden().getChanges()) {
            isModified = true;
        } else if (closedCheckBox.isSelected() != data.getCentralSettings().getHidden().getClosed()) {
            isModified = true;
        } else if (skipCiCheckBox.isSelected() != data.getCentralSettings().getHidden().getSkipCi()) {
            isModified = true;
        } else if (gitmoji.isSelected() != data.getCentralSettings().getHidden().getGitmoji()) {
            isModified = true;
        }
        return isModified;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }


    private void createUIComponents() {
        typeDisplayNumberSpinner = new JBIntSpinner(1, -1, 999);
    }
}
