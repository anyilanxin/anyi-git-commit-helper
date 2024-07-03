package com.fulinlin.ui.commit;

import com.fulinlin.localization.PluginBundle;
import com.fulinlin.model.*;
import com.fulinlin.model.enums.TypeDisplayStyleEnum;
import com.fulinlin.storage.GitCommitMessageHelperSettings;
import com.fulinlin.ui.setting.AliasEditor;
import com.intellij.ide.ui.laf.darcula.ui.DarculaEditorTextFieldBorder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.EditorTextField;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;


public class CommitPanel {
    private final GitCommitMessageHelperSettings settings;
    private final ScopeInfos scopeInfos;
    private JPanel mainPanel;
    private JComboBox<TypeAlias> changeType;
    private JComboBox<ScopeAlias> changeScope;
    private JTextField shortDescription;
    private EditorTextField longDescription;
    private EditorTextField breakingChanges;
    private JTextField closedIssues;
    private JLabel typeDescriptionLabel;
    private JLabel scopeDescriptionLabel;
    private JLabel subjectDescriptionLabel;
    private JLabel bodyDescriptionLabel;
    private JLabel closedDescriptionLabel;
    private JLabel changeDescriptionLabel;
    private JScrollPane longDescriptionScrollPane;
    private JScrollPane breakingChangesScrollPane;
    private JPanel typePanel;
    private JCheckBox approveCheckBox;
    private JComboBox<String> skipCiComboBox;
    private JLabel skipCiLabel;
    private JButton addScope;
    private JComboBox<GitmojiInfo> gitemoji;
    private ButtonGroup buttonGroup;


    public CommitPanel(Project project,
                       GitCommitMessageHelperSettings settings,
                       CommitTemplate commitMessageTemplate,
                       ScopeInfos scopeInfos) {
        this.settings = settings;
        this.scopeInfos = scopeInfos;
        // Personalized UI configuration
        typeDescriptionLabel.setText(PluginBundle.get("commit.panel.type.field"));
        scopeDescriptionLabel.setText(PluginBundle.get("commit.panel.scope.field"));
        subjectDescriptionLabel.setText(PluginBundle.get("commit.panel.subject.field"));
        bodyDescriptionLabel.setText(PluginBundle.get("commit.panel.body.field"));
        closedDescriptionLabel.setText(PluginBundle.get("commit.panel.closes.field"));
        changeDescriptionLabel.setText(PluginBundle.get("commit.panel.changes.field"));
        longDescriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
        breakingChangesScrollPane.setBorder(BorderFactory.createEmptyBorder());
        longDescription.setBorder(new DarculaEditorTextFieldBorder());
        breakingChanges.setBorder(new DarculaEditorTextFieldBorder());
        longDescription.setOneLineMode(false);
        longDescription.ensureWillComputePreferredSize();
        longDescription.addSettingsProvider(uEditor -> {
            uEditor.setVerticalScrollbarVisible(true);
            uEditor.setHorizontalScrollbarVisible(true);
            uEditor.setBorder(null);
        });
        breakingChanges.setOneLineMode(false);
        breakingChanges.ensureWillComputePreferredSize();
        breakingChanges.addSettingsProvider(uEditor -> {
            uEditor.setVerticalScrollbarVisible(true);
            uEditor.setHorizontalScrollbarVisible(true);
            uEditor.setBorder(null);
        });
        approveCheckBox.setText(PluginBundle.get("commit.panel.skip.ci.checkbox"));
        settingHidden(commitMessageTemplate);
        computePanelHeight();
    }

    private void settingHidden(CommitTemplate commitMessageTemplate) {
        CentralSettings centralSettings = settings.getCentralSettings();
        List<TypeAlias> typeAliases = settings.getDateSettings().getTypeAliases();
        if (centralSettings.getHidden().getType()) {
            typeDescriptionLabel.setVisible(false);
            typePanel.setVisible(false);
        } else {
            if (centralSettings.getTypeDisplayStyle() == TypeDisplayStyleEnum.CHECKBOX) {
                changeType = new ComboBox<>();
                for (TypeAlias type : typeAliases) {
                    changeType.addItem(type);
                }
                if (commitMessageTemplate != null) {
                    typeAliases.stream()
                            .filter(typeAlias -> typeAlias.getTitle().equals(commitMessageTemplate.getType()))
                            .findFirst()
                            .ifPresent(typeAlias ->
                                    changeType.setSelectedItem(typeAlias)
                            );
                }
                typePanel.add(changeType);
            } else if (centralSettings.getTypeDisplayStyle() == TypeDisplayStyleEnum.RADIO) {
                buttonGroup = new ButtonGroup();
                typePanel.setLayout(new GridLayout(0, 1));
                Integer typeDisplayNumber = centralSettings.getTypeDisplayNumber();
                if (typeDisplayNumber == -1) {
                    typeDisplayNumber = typeAliases.size();
                }
                if (typeDisplayNumber > typeAliases.size()) {
                    typeDisplayNumber = typeAliases.size();
                }
                for (int i = 0; i < typeDisplayNumber; i++) {
                    TypeAlias type = typeAliases.get(i);
                    JRadioButton radioButton = new JRadioButton(type.getTitle() + "-" + type.getDescription());
                    radioButton.setActionCommand(type.getTitle());
                    buttonGroup.add(radioButton);
                    typePanel.add(radioButton);
                    if (commitMessageTemplate != null) {
                        if (type.getTitle().equals(commitMessageTemplate.getType())) {
                            radioButton.setSelected(true);
                        }
                    }
                }
            } else if (centralSettings.getTypeDisplayStyle() == TypeDisplayStyleEnum.MIXING) {
                typePanel.setLayout(new GridLayout(0, 1));
                changeType = new ComboBox<>();
                buttonGroup = new ButtonGroup();
                Integer typeDisplayNumber = centralSettings.getTypeDisplayNumber();
                if (typeDisplayNumber == -1) {
                    typeDisplayNumber = typeAliases.size();
                }
                if (typeDisplayNumber > typeAliases.size()) {
                    typeDisplayNumber = typeAliases.size();
                }
                for (int i = 0; i < typeDisplayNumber; i++) {
                    TypeAlias type = typeAliases.get(i);
                    JRadioButton radioButton = new JRadioButton(type.getTitle() + "-" + type.getDescription());
                    radioButton.setActionCommand(type.getTitle());
                    radioButton.addChangeListener(e -> {
                        if (radioButton.isSelected()) {
                            typeAliases.stream()
                                    .filter(typeAlias -> typeAlias.getTitle().equals(radioButton.getActionCommand()))
                                    .findFirst()
                                    .ifPresent(typeAlias ->
                                            changeType.setSelectedItem(typeAlias)
                                    );
                        }
                    });
                    buttonGroup.add(radioButton);
                    typePanel.add(radioButton);
                    if (commitMessageTemplate != null) {
                        if (type.getTitle().equals(commitMessageTemplate.getType())) {
                            radioButton.setSelected(true);
                        }
                    }
                }
                changeType.addActionListener(e -> {
                    if (changeType.getSelectedItem() != null) {
                        String typeTitle = ((TypeAlias) Objects.requireNonNull(changeType.getSelectedItem())).getTitle();
                        Iterator<AbstractButton> iterator = buttonGroup.getElements().asIterator();
                        boolean flag = false;
                        while (iterator.hasNext()) {
                            AbstractButton radioButton = iterator.next();
                            boolean equals = radioButton.getActionCommand().equals(typeTitle);
                            radioButton.setSelected(equals);
                            if (equals) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            buttonGroup.clearSelection();
                        }
                    }
                });
                for (TypeAlias type : typeAliases) {
                    changeType.addItem(type);
                }
                if (commitMessageTemplate != null) {
                    typeAliases.stream()
                            .filter(typeAlias -> typeAlias.getTitle().equals(commitMessageTemplate.getType()))
                            .findFirst()
                            .ifPresent(typeAlias ->
                                    changeType.setSelectedItem(typeAlias)
                            );
                }
                typePanel.add(changeType);
            }
        }
        if (centralSettings.getHidden().getScope()) {
            scopeDescriptionLabel.setVisible(false);
            changeScope.setVisible(false);
            addScope.setVisible(false);
        } else {
            loadScope();
            addScope.addActionListener(e -> {
                final AliasEditor macroEditor = new AliasEditor(PluginBundle.get("setting.alias.add.scope"), "", "");
                if (macroEditor.showAndGet()) {
                    List<ScopeAlias> scopeAliases = scopeInfos.getScopeAlias();
                    if (scopeAliases == null) {
                        scopeAliases = new ArrayList<>();
                    }
                    scopeAliases.add(new ScopeAlias(macroEditor.getTitle(), macroEditor.getDescription()));
                    scopeInfos.setScopeAlias(scopeAliases);
                    loadScope();
                }
            });
        }

        if (centralSettings.getHidden().getGitmoji()) {
            gitemoji.setVisible(false);
        } else {
            List<GitmojiInfo> gitmojis = settings.getDateSettings().getGitmojis();
            if (gitmojis == null || gitmojis.isEmpty()) {
                gitmojis = new ArrayList<>();
            }
            GitmojiInfo defaultMoji = new GitmojiInfo();
            defaultMoji.setCode("");
            defaultMoji.setDescription("<empty>");
            gitemoji.addItem(defaultMoji);
            gitmojis.sort(Comparator.comparing(GitmojiInfo::getCode));
            for (GitmojiInfo moji : gitmojis) {
                gitemoji.addItem(moji);
            }
        }
        if (centralSettings.getHidden().getBody()) {
            bodyDescriptionLabel.setVisible(false);
            longDescriptionScrollPane.setVisible(false);
            longDescription.setVisible(false);
        }
        if (centralSettings.getHidden().getChanges()) {
            changeDescriptionLabel.setVisible(false);
            breakingChangesScrollPane.setVisible(false);
            breakingChanges.setVisible(false);
        }
        if (centralSettings.getHidden().getClosed()) {
            closedDescriptionLabel.setVisible(false);
            closedIssues.setVisible(false);
        }
        if (centralSettings.getHidden().getSkipCi()) {
            skipCiLabel.setVisible(false);
            skipCiComboBox.setVisible(false);
            approveCheckBox.setVisible(false);
        } else {
            if (!centralSettings.getSkipCiComboboxEnable()) {
                skipCiComboBox.setVisible(false);
            }
            List<String> skipCis = settings.getDateSettings().getSkipCis();
            for (String skipCi : skipCis) {
                skipCiComboBox.addItem(skipCi);
            }
            if (settings.getCentralSettings().getSkipCiDefaultApprove()) {
                approveCheckBox.setSelected(true);
            }
            if (settings.getCentralSettings().getSkipCiDefaultValue() != null) {
                skipCiComboBox.setSelectedItem(settings.getCentralSettings().getSkipCiDefaultValue());
            }
        }
        if (commitMessageTemplate != null) {
            // with cache init
            changeScope.setSelectedItem(commitMessageTemplate.getScope());
//            changeScope.setText(commitMessageTemplate.getScope());
            shortDescription.setText(commitMessageTemplate.getSubject());
            longDescription.setText(commitMessageTemplate.getBody());
            breakingChanges.setText(commitMessageTemplate.getChanges());
            closedIssues.setText(commitMessageTemplate.getCloses());
        }
    }


    private void computePanelHeight() {
        int height = 0;
        if (changeType != null) {
            height += 33;
        }
        if (buttonGroup != null) {
            height += 33 * buttonGroup.getButtonCount();
        }
        if (!settings.getCentralSettings().getHidden().getScope()) {
            height += 33;
        }
        if (!settings.getCentralSettings().getHidden().getSubject()) {
            height += 33;
        }
        if (!settings.getCentralSettings().getHidden().getBody()) {
            longDescriptionScrollPane.setPreferredSize(new Dimension(730, 130));
            height += 150;
        }
        if (!settings.getCentralSettings().getHidden().getChanges()) {
            longDescriptionScrollPane.setPreferredSize(new Dimension(730, 100));
            height += 100;
        }
        if (!settings.getCentralSettings().getHidden().getClosed()) {
            height += 43;
        }
        if (!settings.getCentralSettings().getHidden().getSkipCi()) {
            height += 33;
        }
        mainPanel.setPreferredSize(new Dimension(730, height));
    }

    CommitMessage getCommitMessage(GitCommitMessageHelperSettings settings) {
        TypeAlias type = new TypeAlias();
        if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.CHECKBOX) {
            if (changeType != null) {
                if (changeType.getSelectedItem() != null) {
                    type = ((TypeAlias) Objects.requireNonNull(changeType.getSelectedItem()));
                }
            }
        } else if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.RADIO) {
            if (buttonGroup != null) {
                if (buttonGroup.getSelection() != null) {
                    if (buttonGroup.getSelection().getActionCommand() != null) {
                        type = new TypeAlias(buttonGroup.getSelection().getActionCommand(), "");
                    }
                }
            }
        } else if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.MIXING) {
            if (changeType != null) {
                if (changeType.getSelectedItem() != null) {
                    type = ((TypeAlias) Objects.requireNonNull(changeType.getSelectedItem()));
                }
            }
        }
        String skipCi = "";
        if (!settings.getCentralSettings().getHidden().getSkipCi()) {
            if (approveCheckBox.isSelected()) {
                if (settings.getCentralSettings().getSkipCiComboboxEnable()) {
                    if (skipCiComboBox.getSelectedItem() != null) {
                        skipCi = skipCiComboBox.getSelectedItem().toString();
                    }
                } else {
                    skipCi = settings.getCentralSettings().getSkipCiDefaultValue();
                }
            }

        }
        String scope = "";
        Object scopeSelectedItem = changeScope.getSelectedItem();
        if (Objects.nonNull(scopeSelectedItem)) {
            scope = ((ScopeAlias) scopeSelectedItem).getTitle();
        }
        String gitEmoji = "";
        Object gitEmojiSelectedItem = gitemoji.getSelectedItem();
        if (Objects.nonNull(gitEmojiSelectedItem)) {
            gitEmoji = ((GitmojiInfo) gitEmojiSelectedItem).getCode();
        }
        return new CommitMessage(
                settings,
                type,
                gitEmoji,
                scope,
                shortDescription.getText().trim(),
                longDescription.getText().trim(),
                closedIssues.getText().trim(),
                breakingChanges.getText().trim(),
                skipCi.trim()
        );
    }

    CommitTemplate getCommitMessageTemplate() {
        CommitTemplate commitTemplate = new CommitTemplate();
        if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.CHECKBOX) {
            if (changeType != null) {
                if (changeType.getSelectedItem() != null) {
                    commitTemplate.setType(((TypeAlias) Objects.requireNonNull(changeType.getSelectedItem())).getTitle());
                }
            }
        } else if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.RADIO) {
            if (buttonGroup != null) {
                if (buttonGroup.getSelection() != null) {
                    if (buttonGroup.getSelection().getActionCommand() != null) {
                        commitTemplate.setType(buttonGroup.getSelection().getActionCommand());
                    }
                }
            }
        } else if (settings.getCentralSettings().getTypeDisplayStyle() == TypeDisplayStyleEnum.MIXING) {
            if (changeType != null) {
                if (changeType.getSelectedItem() != null) {
                    commitTemplate.setType(((TypeAlias) Objects.requireNonNull(changeType.getSelectedItem())).getTitle());
                }
            }
        }
        String skipCi = "";
        if (!settings.getCentralSettings().getHidden().getSkipCi()) {
            if (approveCheckBox.isSelected()) {
                if (settings.getCentralSettings().getSkipCiComboboxEnable()) {
                    if (skipCiComboBox.getSelectedItem() != null) {
                        skipCi = skipCiComboBox.getSelectedItem().toString();
                    } else {
                        skipCi = settings.getCentralSettings().getSkipCiDefaultValue();
                    }
                }
            }

        }
        String scope = "";
        Object selectedItem = changeScope.getSelectedItem();
        if (Objects.nonNull(selectedItem)) {
            scope = ((ScopeAlias) selectedItem).title;
        }
        String gitEmoji = "";
        Object gitEmojiSelectedItem = gitemoji.getSelectedItem();
        if (Objects.nonNull(gitEmojiSelectedItem)) {
            gitEmoji = ((GitmojiInfo) gitEmojiSelectedItem).getCode();
        }
        commitTemplate.setSkipCi(skipCi.trim());
        commitTemplate.setEmoji(gitEmoji);
        commitTemplate.setScope(scope);
        commitTemplate.setSubject(shortDescription.getText().trim());
        commitTemplate.setBody(longDescription.getText().trim());
        commitTemplate.setChanges(breakingChanges.getText().trim());
        commitTemplate.setCloses(closedIssues.getText().trim());
        return commitTemplate;
    }


    private void loadScope() {
        changeScope.removeAllItems();
        List<ScopeAlias> scopeAliases = scopeInfos.getScopeAlias();
        if (scopeAliases == null || scopeAliases.isEmpty()) {
            scopeAliases = new ArrayList<>();
        }
        ScopeAlias defaultScope = new ScopeAlias();
        defaultScope.setTitle("");
        defaultScope.setDescription("<empty>");
        changeScope.addItem(defaultScope);
        scopeAliases.sort(Comparator.comparing(ScopeAlias::getTitle));
        for (ScopeAlias scope : scopeAliases) {
            changeScope.addItem(scope);
        }
    }

    JPanel getMainPanel() {
        return mainPanel;
    }


}
