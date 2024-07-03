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
package com.anyilanxin.plugin.githelper.storage;

import com.anyilanxin.plugin.githelper.constant.GitCommitConstants;
import com.anyilanxin.plugin.githelper.localization.PluginBundle;
import com.anyilanxin.plugin.githelper.model.CentralSettings;
import com.anyilanxin.plugin.githelper.model.DataSettings;
import com.anyilanxin.plugin.githelper.model.GitmojiInfo;
import com.anyilanxin.plugin.githelper.model.TypeAlias;
import com.anyilanxin.plugin.githelper.model.enums.TypeDisplayStyleEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-05 21:13
 **/
@State(name = "ay_git_commit_message_helper_settings",
        storages = {@Storage(value = "ay_git_commit_message_helper_settings.xml")})
public class GitCommitMessageHelperSettings implements PersistentStateComponent<GitCommitMessageHelperSettings> {
    private static final Logger log = Logger.getInstance(GitCommitMessageHelperSettings.class);
    private final static Gson GSON = new Gson();
    private DataSettings dataSettings;
    private CentralSettings centralSettings;

    public GitCommitMessageHelperSettings() {
    }

    @Nullable
    @Override
    public GitCommitMessageHelperSettings getState() {
        if (this.dataSettings == null) {
            loadDefaultDataSettings();
        } else {
            checkDefaultDataSettings(dataSettings);
        }
        if (centralSettings == null) {
            loadDefaultCentralSettings();
        }
        return this;
    }

    @Override
    public void loadState(@NotNull GitCommitMessageHelperSettings gitCommitMessageHelperSettings) {
        XmlSerializerUtil.copyBean(gitCommitMessageHelperSettings, this);
    }


    public CentralSettings getCentralSettings() {
        if (centralSettings == null) {
            loadDefaultCentralSettings();
        }
        return centralSettings;
    }

    /**
     * Spelling error here, in order to maintain the current status of existing user data
     */
    public DataSettings getDateSettings() {
        if (dataSettings == null) {
            loadDefaultDataSettings();
        } else {
            checkDefaultDataSettings(dataSettings);
        }
        return dataSettings;
    }


    private void loadDefaultCentralSettings() {
        centralSettings = new CentralSettings();
        try {
            centralSettings.setTypeDisplayStyle(TypeDisplayStyleEnum.CHECKBOX);
            centralSettings.setTypeDisplayNumber(-1);
            centralSettings.setSkipCiDefaultValue("[skip ci]");
            centralSettings.setSkipCiDefaultApprove(Boolean.FALSE);
            centralSettings.setSkipCiComboboxEnable(Boolean.FALSE);
            CentralSettings.Hidden hidden = new CentralSettings.Hidden();
            centralSettings.setHidden(hidden);
            centralSettings.getHidden().setType(Boolean.FALSE);
            centralSettings.getHidden().setGitmoji(Boolean.FALSE);
            centralSettings.getHidden().setScope(Boolean.FALSE);
            centralSettings.getHidden().setSubject(Boolean.FALSE);
            centralSettings.getHidden().setBody(Boolean.FALSE);
            centralSettings.getHidden().setClosed(Boolean.FALSE);
            centralSettings.getHidden().setChanges(Boolean.FALSE);
            centralSettings.getHidden().setSkipCi(Boolean.FALSE);
        } catch (Exception e) {
            log.error("loadDefaultCentralSettings failed", e);
        }
    }


    private void loadDefaultDataSettings() {
        dataSettings = new DataSettings();
        try {
            dataSettings.setTemplate(GitCommitConstants.DEFAULT_TEMPLATE);
            List<TypeAlias> typeAliases = getTypeAliases();
            dataSettings.setTypeAliases(typeAliases);
            List<GitmojiInfo> gitmojiInfos = getGitmojiDefaultInfos();
            dataSettings.setGitmojis(gitmojiInfos);
            List<String> skipCis = getSkipCis();
            dataSettings.setSkipCis(skipCis);
        } catch (Exception e) {
            log.error("loadDefaultDataSettings failed", e);
        }
    }

    private void checkDefaultDataSettings(DataSettings dataSettings) {
        if (dataSettings.getTemplate() == null) {
            dataSettings.setTemplate(GitCommitConstants.DEFAULT_TEMPLATE);
        }
        if (dataSettings.getTypeAliases() == null) {
            List<TypeAlias> typeAliases = getTypeAliases();
            dataSettings.setTypeAliases(typeAliases);
        }
        if (dataSettings.getGitmojis() == null) {
            List<GitmojiInfo> gitmojiInfos = getGitmojiDefaultInfos();
            dataSettings.setGitmojis(gitmojiInfos);
        }
        if (dataSettings.getSkipCis() == null) {
            List<String> skipCis = getSkipCis();
            dataSettings.setSkipCis(skipCis);
        }
    }


    @NotNull
    private static List<String> getSkipCis() {
        List<String> skipCis = new LinkedList<>();
        skipCis.add("[skip ci]");
        skipCis.add("[ci skip]");
        skipCis.add("[no ci]");
        skipCis.add("[skip actions]");
        skipCis.add("[actions skip]");
        skipCis.add("skip-checks:true");
        skipCis.add("skip-checks: true");
        return skipCis;
    }

    @NotNull
    public static List<TypeAlias> getTypeAliases() {
        List<TypeAlias> typeAliases = new LinkedList<>();
        // default init i18n
        typeAliases.add(new TypeAlias("feat", PluginBundle.get("feat.description")));
        typeAliases.add(new TypeAlias("fix", PluginBundle.get("fix.description")));
        typeAliases.add(new TypeAlias("docs", PluginBundle.get("docs.description")));
        typeAliases.add(new TypeAlias("style", PluginBundle.get("style.description")));
        typeAliases.add(new TypeAlias("refactor", PluginBundle.get("refactor.description")));
        typeAliases.add(new TypeAlias("perf", PluginBundle.get("perf.description")));
        typeAliases.add(new TypeAlias("test", PluginBundle.get("test.description")));
        typeAliases.add(new TypeAlias("build", PluginBundle.get("build.description")));
        typeAliases.add(new TypeAlias("ci", PluginBundle.get("ci.description")));
        typeAliases.add(new TypeAlias("chore", PluginBundle.get("chore.description")));
        typeAliases.add(new TypeAlias("revert", PluginBundle.get("revert.description")));
        return typeAliases;
    }


    public static List<GitmojiInfo> getGitmojiDefaultInfos() {
        // GitmojiInfo(String emoji, String code, String description)
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = GitCommitMessageHelperSettings.class.getResourceAsStream("/gitmojis.json");
            if (Objects.nonNull(resourceAsStream)) {
                byte[] bytes = resourceAsStream.readAllBytes();
                String json = new String(bytes);
                Gson gson = new Gson();
                Type type = new TypeToken<List<GitmojiInfo>>() {
                }.getType();
                return gson.fromJson(json, type);
            }
        } catch (IOException ignored) {

        } finally {
            if (Objects.nonNull(resourceAsStream)) {
                try {
                    resourceAsStream.close();
                } catch (IOException ignored) {

                }
            }
        }
        return Collections.emptyList();
    }


    public void updateTemplate(String template) {
        dataSettings.setTemplate(template);
    }

    public void updateTypeMap(List<TypeAlias> typeAliases) {
        dataSettings.setTypeAliases(typeAliases);
    }

    /**
     * Spelling error here, in order to maintain the current status of existing user data
     */
    public void setDateSettings(DataSettings dateSettings) {
        this.dataSettings = dateSettings;
    }

    public void setCentralSettings(CentralSettings centralSettings) {
        this.centralSettings = centralSettings;
    }

    @Override
    public GitCommitMessageHelperSettings clone() {
        String json = GSON.toJson(this);
        return GSON.fromJson(json, GitCommitMessageHelperSettings.class);
    }

//    @Override
//    @SuppressWarnings("MethodDoesntCallSuperMethod")
//    public GitCommitMessageHelperSettings clone() {
//        Cloner cloner = new Cloner();
//        cloner.nullInsteadOfClone();
//        return cloner.deepClone(this);
//    }

}
