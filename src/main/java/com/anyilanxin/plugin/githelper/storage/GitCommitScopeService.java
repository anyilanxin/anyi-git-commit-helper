/*
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
import com.anyilanxin.plugin.githelper.utils.ProjectUtils;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

/**
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/14 15:16
 */
public interface GitCommitScopeService extends PersistentStateComponent<ScopeInfos> {
    String SAVE_PATH = "ay_git_commit_scope_storage.xml";

    /**
     * 获取实例
     *
     * @return {@link GitCommitScopeService}
     */
    static GitCommitScopeService getInstance() {
        try {
            return ServiceManager.getService(ProjectUtils.getCurrProject(), GitCommitScopeService.class);
        } catch (AssertionError e) {
            // 出现配置文件被错误修改，或不兼容时直接删除配置文件。
            VirtualFile workspaceFile = ProjectUtils.getCurrProject().getWorkspaceFile();
            if (workspaceFile != null) {
                VirtualFile configFile = workspaceFile.getParent().findChild(SAVE_PATH);
                if (configFile != null && configFile.exists()) {
                    WriteCommandAction.runWriteCommandAction(ProjectUtils.getCurrProject(), () -> {
                        try {
                            configFile.delete(null);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
            // 重新获取配置
            return ServiceManager.getService(ProjectUtils.getCurrProject(), GitCommitScopeServiceImpl.class);
        }
    }

    /**
     * 获取表信息
     *
     * @return {@link ScopeInfos}
     */
    ScopeInfos getScope();

    /**
     * 保存配置
     *
     * @param scopeInfos scope配置信息
     */
    void saveScope(ScopeInfos scopeInfos);
}
