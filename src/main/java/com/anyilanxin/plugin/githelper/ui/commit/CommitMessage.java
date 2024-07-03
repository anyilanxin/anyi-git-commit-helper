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

import com.anyilanxin.plugin.githelper.model.CommitTemplate;
import com.anyilanxin.plugin.githelper.model.TypeAlias;
import com.anyilanxin.plugin.githelper.storage.GitCommitMessageHelperSettings;
import com.anyilanxin.plugin.githelper.utils.VelocityUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author fulin
 */
public class CommitMessage {

    private final String content;

    public CommitMessage(GitCommitMessageHelperSettings settings,
                         TypeAlias typeAlias,
                         String gitmoji,
                         String changeScope,
                         String shortDescription,
                         String longDescription,
                         String closedIssues,
                         String breakingChanges,
                         String skipCi) {
        this.content = buildContent(
                settings,
                typeAlias,
                gitmoji,
                changeScope,
                shortDescription,
                longDescription,
                breakingChanges,
                closedIssues,
                skipCi
        );
    }

    private String buildContent(GitCommitMessageHelperSettings settings,
                                TypeAlias typeAlias,
                                String gitmoji,
                                String changeScope,
                                String shortDescription,
                                String longDescription,
                                String breakingChanges,
                                String closedIssues,
                                String skipCi) {

        CommitTemplate commitTemplate = new CommitTemplate();
        if (typeAlias != null) {
            if (StringUtils.isNotBlank(typeAlias.getTitle())) {
                commitTemplate.setType(typeAlias.getTitle());
            }
        }
        if (StringUtils.isNotBlank(gitmoji)) {
            commitTemplate.setEmoji(gitmoji);
        }
        if (StringUtils.isNotBlank(changeScope)) {
            commitTemplate.setScope(changeScope);
        }
        if (StringUtils.isNotBlank(shortDescription)) {
            commitTemplate.setSubject(shortDescription);
        }
        if (StringUtils.isNotBlank(longDescription)) {
            commitTemplate.setBody(longDescription);
        }
        if (StringUtils.isNotBlank(breakingChanges)) {
            commitTemplate.setChanges(breakingChanges);
        }
        if (StringUtils.isNotBlank(closedIssues)) {
            commitTemplate.setCloses(closedIssues);
        }
        if (StringUtils.isNotBlank(skipCi)) {
            commitTemplate.setSkipCi(skipCi);
        }
        String template = settings.getDateSettings().getTemplate().replaceAll("\\n", "");
        return VelocityUtils.convert(template, commitTemplate);
    }

    @Override
    public String toString() {
        return content;
    }
}