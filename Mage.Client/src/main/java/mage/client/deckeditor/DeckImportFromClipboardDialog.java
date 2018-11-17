package mage.client.deckeditor;

import mage.util.StreamUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javax.swing.*;

public class DeckImportFromClipboardDialog extends JDialog {

    private static final String FORMAT_TEXT =
            "// Example:\n" +
            "//1 Library of Congress\n" +
            "//1 Cryptic Gateway\n" +
            "//1 Azami, Lady of Scrolls\n" +
            "// NB: This is slow as, and will lock your screen :)\n" +
            "\n" +
            "// Your current clipboard:\n";

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JEditorPane txtDeckList;

    private String tmpPath;

    public DeckImportFromClipboardDialog() {
        initComponents();

        onRefreshClipboard();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Close on "ESC"
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private Optional<String> getClipboardStringData() {
        try {
            return Optional.of((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
        } catch (HeadlessException | UnsupportedFlavorException | IOException e) {
            //e.printStackTrace();
        }
        return Optional.empty();
    }

    private void onOK() {
        BufferedWriter bw = null;
        try {
            File temp = File.createTempFile("cbimportdeck", ".txt");
            bw = new BufferedWriter(new FileWriter(temp));
            bw.write(txtDeckList.getText());
            tmpPath = temp.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.closeQuietly(bw);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onRefreshClipboard() {
        txtDeckList.setText(FORMAT_TEXT + getClipboardStringData().orElse(""));
    }

    public String getTmpPath() {
        return tmpPath;
    }

    private void initComponents() {
        contentPane = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        buttonOK = new JButton();
        buttonCancel = new JButton();
        JPanel panel3 = new JPanel();
        txtDeckList = new JEditorPane();

        {
            contentPane.setMinimumSize(new Dimension(540, 450));

            contentPane.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "Import from Clipboard", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                            java.awt.Color.BLACK), contentPane.getBorder()));

            contentPane.addPropertyChangeListener(e -> {
                if ("border".equals(e.getPropertyName())) {
                    throw new RuntimeException();
                }
            });

            contentPane.addPropertyChangeListener(e -> {
                if ("border".equals(e.getPropertyName())) {
                    throw new RuntimeException();
                }
            });

            contentPane.setLayout(new GridBagLayout());
            ((GridBagLayout) contentPane.getLayout()).columnWidths = new int[]{0, 0};
            ((GridBagLayout) contentPane.getLayout()).rowHeights = new int[]{0, 0, 0};
            ((GridBagLayout) contentPane.getLayout()).columnWeights = new double[]{0.01, 1.0E-4};
            ((GridBagLayout) contentPane.getLayout()).rowWeights = new double[]{0.01, 0.0, 1.0E-4};

            {
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout) panel1.getLayout()).columnWidths = new int[]{0, 0, 0};
                ((GridBagLayout) panel1.getLayout()).rowHeights = new int[]{0, 0};
                ((GridBagLayout) panel1.getLayout()).columnWeights = new double[]{0.0, 0.01, 1.0E-4};
                ((GridBagLayout) panel1.getLayout()).rowWeights = new double[]{0.01, 1.0E-4};

                {
                    panel2.setLayout(new GridBagLayout());
                    ((GridBagLayout) panel2.getLayout()).columnWidths = new int[]{0, 4, 0, 0};
                    ((GridBagLayout) panel2.getLayout()).rowHeights = new int[]{0, 0};
                    ((GridBagLayout) panel2.getLayout()).columnWeights = new double[]{0.01, 0.0, 0.01, 1.0E-4};
                    ((GridBagLayout) panel2.getLayout()).rowWeights = new double[]{0.0, 1.0E-4};

                    //---- buttonOK ----
                    buttonOK.setText("Import");
                    panel2.add(buttonOK, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                    //---- buttonCancel ----
                    buttonCancel.setText("Cancel");
                    panel2.add(buttonCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                panel1.add(panel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            contentPane.add(panel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            {
                panel3.setLayout(new GridBagLayout());
                ((GridBagLayout) panel3.getLayout()).columnWidths = new int[]{0, 0};
                ((GridBagLayout) panel3.getLayout()).rowHeights = new int[]{0, 0};
                ((GridBagLayout) panel3.getLayout()).columnWeights = new double[]{0.0, 1.0E-4};
                ((GridBagLayout) panel3.getLayout()).rowWeights = new double[]{1.0, 1.0E-4};

                txtDeckList.setMinimumSize(new Dimension(250, 400));
                txtDeckList.setPreferredSize(new Dimension(550, 400));
                txtDeckList.setText(FORMAT_TEXT);
                JScrollPane txtScrollableDeckList = new JScrollPane(txtDeckList);
                panel3.add(txtScrollableDeckList, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            contentPane.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
        }
    }
}
