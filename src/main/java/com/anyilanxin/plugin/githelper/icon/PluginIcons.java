package com.anyilanxin.plugin.githelper.icon;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface PluginIcons {

    Icon ICON = IconLoader.getIcon("/icons/icon.png", PluginIcons.class);

    Icon EDIT = IconLoader.getIcon("/icons/edit.svg", PluginIcons.class);

    Icon HISTORY = IconLoader.getIcon("/icons/history.svg", PluginIcons.class);
}
