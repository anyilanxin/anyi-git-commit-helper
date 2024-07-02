package com.fulinlin.ui.setting;

import com.fulinlin.model.GitmojiInfo;
import com.fulinlin.storage.GitCommitMessageHelperSettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-06 21:21
 **/
public class GitmojiTable extends JBTable {

    private static final Logger log = Logger.getInstance(GitmojiTable.class);
    private static final int CODE_COLUMN = 0;
    private static final int EMOJI_COLUMN = 1;
    private static final int DESCRIPTION_COLUMN = 2;
    private final MyTableModel myTableModel = new MyTableModel();


    private final List<GitmojiInfo> gitmoji = new LinkedList<>();

    /**
     * instantiation AliasTable
     */
    public GitmojiTable() {
        setModel(myTableModel);
        TableColumn column = getColumnModel().getColumn(CODE_COLUMN);
        TableColumn emojiColumn = getColumnModel().getColumn(EMOJI_COLUMN);
        TableColumn descriptionColumn = getColumnModel().getColumn(DESCRIPTION_COLUMN);
        column.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                final String macroValue = getAliasValueAt(row);
                component.setForeground(macroValue.isEmpty()
                        ? JBColor.RED
                        : isSelected ? table.getSelectionForeground() : table.getForeground());
                return component;
            }
        });
        setColumnSize(column, 150, 250, 150);
        setColumnSize(emojiColumn, 150, 250, 150);
        setColumnSize(descriptionColumn, 550, 750, 550);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    /**
     * Set  Something  ColumnSize
     */
    public static void setColumnSize(TableColumn column, int preferredWidth, int maxWidth, int minWidth) {
        column.setPreferredWidth(preferredWidth);
        column.setMaxWidth(maxWidth);
        column.setMinWidth(minWidth);
    }


    public String getAliasValueAt(int row) {
        return (String) getValueAt(row, CODE_COLUMN);
    }


    public void addAlias() {
        final MojiEditor macroEditor = new MojiEditor("", "", "");
        if (macroEditor.showAndGet()) {
            final String name = macroEditor.getTitle();
            gitmoji.add(new GitmojiInfo(macroEditor.getEmoji(), macroEditor.getCode(), macroEditor.getDescription()));
            final int index = indexOfAliasWithName(name);
            log.assertTrue(index >= 0);
            myTableModel.fireTableDataChanged();
            setRowSelectionInterval(index, index);
        }
    }

    private boolean isValidRow(int selectedRow) {
        return selectedRow >= 0 && selectedRow < gitmoji.size();
    }

    public void moveUp() {
        int selectedRow = getSelectedRow();
        int index1 = selectedRow - 1;
        if (selectedRow != -1) {
            Collections.swap(gitmoji, selectedRow, index1);
        }
        setRowSelectionInterval(index1, index1);
    }


    public void moveDown() {
        int selectedRow = getSelectedRow();
        int index1 = selectedRow + 1;
        if (selectedRow != -1) {
            Collections.swap(gitmoji, selectedRow, index1);
        }
        setRowSelectionInterval(index1, index1);
    }


    public void removeSelectedAliases() {
        final int[] selectedRows = getSelectedRows();
        if (selectedRows.length == 0) {
            return;
        }
        Arrays.sort(selectedRows);
        final int originalRow = selectedRows[0];
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            final int selectedRow = selectedRows[i];
            if (isValidRow(selectedRow)) {
                gitmoji.remove(selectedRow);
            }
        }
        myTableModel.fireTableDataChanged();
        if (originalRow < getRowCount()) {
            setRowSelectionInterval(originalRow, originalRow);
        } else if (getRowCount() > 0) {
            final int index = getRowCount() - 1;
            setRowSelectionInterval(index, index);
        }
    }


    public void commit(GitCommitMessageHelperSettings settings) {
        settings.getDateSettings().setGitmojis(new LinkedList<>(gitmoji));
    }

    public void resetDefaultAliases() {
        this.gitmoji.clear();
        this.gitmoji.addAll(GitCommitMessageHelperSettings.getGitmojiDefaultInfos());
        myTableModel.fireTableDataChanged();
    }

    public void reset(GitCommitMessageHelperSettings settings) {
        obtainAliases(gitmoji, settings);
        myTableModel.fireTableDataChanged();
    }


    private int indexOfAliasWithName(String name) {
        for (int i = 0; i < gitmoji.size(); i++) {
            final GitmojiInfo typeAlias = gitmoji.get(i);
            if (name.equals(typeAlias.getCode())) {
                return i;
            }
        }
        return -1;
    }

    private void obtainAliases(@NotNull List<GitmojiInfo> aliases, GitCommitMessageHelperSettings settings) {
        aliases.clear();
        List<GitmojiInfo> gitmoji = settings.getDateSettings().getGitmojis();
        if (gitmoji != null && !gitmoji.isEmpty()) {
            aliases.addAll(gitmoji);
        }

    }

    public boolean editAlias() {
        if (getSelectedRowCount() != 1) {
            return false;
        }
        final int selectedRow = getSelectedRow();
        final GitmojiInfo typeAlias = gitmoji.get(selectedRow);
        final MojiEditor editor = new MojiEditor(typeAlias.getCode(), typeAlias.getEmoji(), typeAlias.getDescription());
        if (editor.showAndGet()) {
            typeAlias.setCode(editor.getCode());
            typeAlias.setDescription(editor.getDescription());
            typeAlias.setEmoji(editor.getEmoji());
            myTableModel.fireTableDataChanged();
        }
        return true;
    }

    public boolean isModified(GitCommitMessageHelperSettings settings) {
        final List<GitmojiInfo> aliases = new LinkedList<>();
        obtainAliases(aliases, settings);
        return !aliases.equals(gitmoji);
    }

    //==========================================================================//

    /**
     * MyTableModel
     */
    private class MyTableModel extends AbstractTableModel {
        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public int getRowCount() {
            return gitmoji.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final GitmojiInfo pair = gitmoji.get(rowIndex);
            switch (columnIndex) {
                case EMOJI_COLUMN:
                    return pair.getEmoji();
                case CODE_COLUMN:
                    return pair.getCode();
                case DESCRIPTION_COLUMN:
                    return pair.getDescription();
            }
            log.error("Wrong indices");
            return null;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case EMOJI_COLUMN:
//                    return PluginBundle.get("setting.alias.field.title");
                    return "Emoji";
                case CODE_COLUMN:
                    return "Code";
                case DESCRIPTION_COLUMN:
                    return "Description";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
}
