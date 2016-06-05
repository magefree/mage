package mage.client.deckeditor;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeckImportFromClipboardDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JEditorPane txtDeckList;

    private String tmpPath;


    public DeckImportFromClipboardDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Close on "ESC"
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            File temp = File.createTempFile("cbimportdeck", ".txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            bw.write(txtDeckList.getText());
            bw.close();

            tmpPath = temp.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public String getTmpPath() {
        return tmpPath;
    }
}
