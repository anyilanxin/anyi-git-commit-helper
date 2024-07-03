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

import com.anyilanxin.plugin.githelper.model.ScopeInfos;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.anyilanxin.plugin.githelper.storage.GitCommitScopeService.SAVE_PATH;


@State(name = "ay_git_commit_scope_storage",
        storages = {@Storage(SAVE_PATH)})
public class GitCommitScopeServiceImpl implements GitCommitScopeService {

    private ScopeInfos scopeInfos = new ScopeInfos();

    public GitCommitScopeServiceImpl() {
    }

    @Override
    public ScopeInfos getScope() {
        return this.scopeInfos;
    }

    @Override
    public void saveScope(ScopeInfos scopeInfos) {
        this.scopeInfos = scopeInfos;
    }

    @Override
    public @Nullable ScopeInfos getState() {
        return this.scopeInfos;
    }

    @Override
    public void loadState(@NotNull ScopeInfos scopeInfos) {
        this.scopeInfos = scopeInfos;
    }
}
