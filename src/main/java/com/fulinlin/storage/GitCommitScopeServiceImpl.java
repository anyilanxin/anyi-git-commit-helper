package com.fulinlin.storage;

import com.fulinlin.model.ScopeInfos;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.fulinlin.storage.GitCommitScopeService.SAVE_PATH;


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
