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

import com.anyilanxin.plugin.githelper.constant.GitCommitConstants;
import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.anyilanxin.plugin.githelper.model.CommitTemplate;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.anyilanxin.plugin.githelper.storage.GitCommitScopeService;
import com.anyilanxin.plugin.githelper.ui.setting.description.DescriptionRead;
import com.anyilanxin.plugin.githelper.utils.VelocityUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.*;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Optional;


public class TemplateEditPanel {
    private final AliasTable aliasTable;
    private final ScopeTable scopeTable;
    private final GitmojiTable gitmojiTable;
    private final Editor templateEditor;
    private final EditorTextField previewEditor;
    private final JEditorPane myDescriptionComponent;
    protected GitCommitMessageHelperSettings settings;
    private JPanel mainPanel;
    private JPanel templatePanel;
    private JPanel typeEditPanel;
    private JPanel scopeEditPanel;
    private JPanel gitmojiEditPanel;
    private JTabbedPane tabbedPane;
    private JLabel description;
    private JLabel descriptionLabel;
    private JLabel templateLabel;
    private JPanel descriptionPanel;
    private JLabel previewLabel;
    private JPanel previewPanel;
    private JCheckBox typeCheckBox;
    private JCheckBox scopeCheckBox;
    private JCheckBox subjectCheckBox;
    private JCheckBox bodyCheckBox;
    private JCheckBox changesCheckBox;
    private JCheckBox closedCheckBox;
    private JCheckBox skipCiCheckBox;
    private JButton restoreDefaultsButton;
    private JCheckBox gitMojiCheckBox;


    public TemplateEditPanel(GitCommitMessageHelperSettings settings) {
        //Get setting
        this.settings = settings.clone();
        // Init  description
        description.setText(PluginBundle.get("setting.description"));
        descriptionLabel.setText(PluginBundle.get("setting.template.description"));
        templateLabel.setText(PluginBundle.get("setting.template.edit"));
        previewLabel.setText(PluginBundle.get("setting.template.preview"));
        tabbedPane.setTitleAt(0, PluginBundle.get("setting.tabbed.panel.template"));
        tabbedPane.setTitleAt(1, PluginBundle.get("setting.tabbed.panel.type"));
        tabbedPane.setTitleAt(2, PluginBundle.get("setting.tabbed.panel.scope"));
        restoreDefaultsButton.setText(PluginBundle.get("setting.template.restore.defaults"));

        // Init descriptionPanel
        myDescriptionComponent = new JEditorPane();
        myDescriptionComponent.setEditorKit(UIUtil.getHTMLEditorKit());
        myDescriptionComponent.setEditable(false);
        myDescriptionComponent.addHyperlinkListener(new BrowserHyperlinkListener());
        myDescriptionComponent.setCaretPosition(0);
        JBScrollPane descriptionScrollPanel = new JBScrollPane(myDescriptionComponent);
        descriptionScrollPanel.setMaximumSize(new Dimension(150, 50));
        descriptionPanel.add(descriptionScrollPanel);

        // Init  templatePanel
        String template = Optional.of(settings.getDateSettings().getTemplate()).orElse("");
        templateEditor = EditorFactory.getInstance().createEditor(
                EditorFactory.getInstance().createDocument(""),
                null,
                FileTypeManager.getInstance().getFileTypeByExtension("vm"),
                false);
        EditorSettings templateEditorSettings = templateEditor.getSettings();
        templateEditorSettings.setAdditionalLinesCount(0);
        templateEditorSettings.setAdditionalColumnsCount(0);
        templateEditorSettings.setLineMarkerAreaShown(false);
        templateEditorSettings.setVirtualSpace(false);
        JBScrollPane templateScrollPane = new JBScrollPane(templateEditor.getComponent());
        templateScrollPane.setMaximumSize(new Dimension(150, 50));
        templatePanel.add(templateScrollPane);

        // Init previewPanel
        previewEditor = new EditorTextField();
        previewEditor.setViewer(true);
        previewEditor.setOneLineMode(false);
        previewEditor.ensureWillComputePreferredSize();
        previewEditor.addSettingsProvider(uEditor -> {
            uEditor.setVerticalScrollbarVisible(true);
            uEditor.setHorizontalScrollbarVisible(true);
            uEditor.setBorder(null);
        });
        JBScrollPane previewScrollPane = new JBScrollPane(previewEditor.getComponent());
        previewScrollPane.setMaximumSize(new Dimension(150, 50));
        previewPanel.add(previewScrollPane);
        templateEditor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                showPreview();
            }
        });
        typeCheckBox.addChangeListener(e -> showPreview());
        gitMojiCheckBox.addChangeListener(e -> showPreview());
        scopeCheckBox.addChangeListener(e -> showPreview());
        subjectCheckBox.addChangeListener(e -> showPreview());
        bodyCheckBox.addChangeListener(e -> showPreview());
        changesCheckBox.addChangeListener(e -> showPreview());
        closedCheckBox.addChangeListener(e -> showPreview());
        skipCiCheckBox.addChangeListener(e -> showPreview());
        // Init  typeEditPanel
        aliasTable = new AliasTable();
        typeEditPanel.add(
                ToolbarDecorator.createDecorator(aliasTable)
                        .setAddAction(button -> aliasTable.addAlias())
                        .setRemoveAction(button -> aliasTable.removeSelectedAliases())
                        .setEditAction(button -> aliasTable.editAlias())
                        .setMoveUpAction(anActionButton -> aliasTable.moveUp())
                        .setMoveDownAction(anActionButton -> aliasTable.moveDown())
                        .addExtraActions(new AnActionButton("Reset Default Aliases", AllIcons.Actions.Rollback) {
                            @Override
                            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                                aliasTable.resetDefaultAliases();
                            }
                        }).createPanel(), BorderLayout.CENTER);
        // Init scopeEditPanel
        scopeTable = new ScopeTable();
        scopeEditPanel.add(
                ToolbarDecorator.createDecorator(scopeTable)
                        .setAddAction(button -> scopeTable.addAlias())
                        .setRemoveAction(button -> scopeTable.removeSelectedAliases())
                        .setEditAction(button -> scopeTable.editAlias())
                        .setMoveUpAction(anActionButton -> scopeTable.moveUp())
                        .setMoveDownAction(anActionButton -> scopeTable.moveDown())
                        .createPanel(), BorderLayout.CENTER);
        // Init gitmojiEditPanel
        gitmojiTable = new GitmojiTable();
        gitmojiEditPanel.add(
                ToolbarDecorator.createDecorator(gitmojiTable)
                        .setAddAction(button -> gitmojiTable.addAlias())
                        .setRemoveAction(button -> gitmojiTable.removeSelectedAliases())
                        .setEditAction(button -> gitmojiTable.editAlias())
                        .setMoveUpAction(anActionButton -> gitmojiTable.moveUp())
                        .setMoveDownAction(anActionButton -> gitmojiTable.moveDown())
                        .addExtraActions(new AnActionButton("Reset Default GitEmoji", AllIcons.Actions.Rollback) {
                            @Override
                            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                                gitmojiTable.resetDefaultAliases();
                            }
                        }).createPanel(), BorderLayout.CENTER);
        // Init data
        ApplicationManager.getApplication().runWriteAction(() -> {
            templateEditor.getDocument().setText(template);
            myDescriptionComponent.setText(DescriptionRead.readHtmlFile());
        });
        restoreDefaultsButton.addActionListener(e -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                templateEditor.getDocument().setText(GitCommitConstants.DEFAULT_TEMPLATE);
            });
        });
        // Add  DoubleClickListener
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return aliasTable.editAlias();
            }
        }.installOn(aliasTable);
        // Add  DoubleClickListener
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return scopeTable.editAlias();
            }
        }.installOn(scopeTable);
        // Add  DoubleClickListener
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return gitmojiTable.editAlias();
            }
        }.installOn(gitmojiTable);
    }

    private void showPreview() {
        CommitTemplate commitTemplate = new CommitTemplate();
        if (gitMojiCheckBox.isSelected()) {
            commitTemplate.setType("<gitemoji>");
        }
        if (typeCheckBox.isSelected()) {
            commitTemplate.setType("<type>");
        }
        if (scopeCheckBox.isSelected()) {
            commitTemplate.setScope("<scope>");
        }
        if (subjectCheckBox.isSelected()) {
            commitTemplate.setSubject("<subject>");
        }
        if (bodyCheckBox.isSelected()) {
            commitTemplate.setBody("<body>");
        }
        if (changesCheckBox.isSelected()) {
            commitTemplate.setChanges("<changes>");
        }
        if (closedCheckBox.isSelected()) {
            commitTemplate.setCloses("<closes>");
        }
        if (skipCiCheckBox.isSelected()) {
            commitTemplate.setSkipCi("<skipCi>");
        }
        ApplicationManager.getApplication().runWriteAction(() -> {
            String previewTemplate = templateEditor.getDocument().getText().replaceAll("\\n", "");
            previewEditor.getDocument().setText(VelocityUtils.convert(previewTemplate, commitTemplate));
        });
    }


    public GitCommitMessageHelperSettings getSettings() {
        aliasTable.commit(settings);
        scopeTable.commit();
        gitmojiTable.commit(settings);
        settings.getDateSettings().setTemplate(templateEditor.getDocument().getText());
        return settings;
    }

    public void reset(GitCommitMessageHelperSettings settings) {
        this.settings = settings.clone();
        aliasTable.reset(settings);
        scopeTable.reset();
        gitmojiTable.reset(settings);
        ApplicationManager.getApplication().runWriteAction(() ->
                templateEditor.getDocument().setText(settings.getDateSettings().getTemplate())
        );
        myDescriptionComponent.setText(DescriptionRead.readHtmlFile());
    }

    public boolean isSettingsModified(GitCommitMessageHelperSettings settings) {
        GitCommitScopeService instance = GitCommitScopeService.getInstance();
        if (aliasTable.isModified(settings) || scopeTable.isModified(instance) || gitmojiTable.isModified(settings)) {
            return true;
        }
        return isModified(settings);
    }


    public boolean isModified(GitCommitMessageHelperSettings data) {
        if (!StringUtil.equals(settings.getDateSettings().getTemplate(), templateEditor.getDocument().getText())) {
            return true;
        }
        return settings.getDateSettings().getTypeAliases() == data.getDateSettings().getTypeAliases();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
