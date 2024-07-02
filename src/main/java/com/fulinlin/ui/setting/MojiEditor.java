package com.fulinlin.ui.setting;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MojiEditor extends DialogWrapper {
    private JPanel myMojiEditor;
    private JTextField codeField;
    private JTextField emojiField;
    private JTextField descriptionField;


    public interface Validator {
        boolean isOK(String name, String value);
    }

    public MojiEditor(String code, String emoji, String description) {
        super(true);
        setTitle("Moji editor");
        codeField.setText(code);
        emojiField.setText(emoji);
        descriptionField.setText(description);
        init();
    }

    public JPanel getMyMojiEditor() {
        return myMojiEditor;
    }

    public String getCode() {
        return codeField.getText().trim();
    }

    public String getEmoji() {
        return emojiField.getText().trim();
    }


    public String getDescription() {
        return descriptionField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myMojiEditor;
    }
}
