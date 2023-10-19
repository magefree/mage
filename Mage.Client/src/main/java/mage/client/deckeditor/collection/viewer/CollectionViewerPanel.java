package mage.client.deckeditor.collection.viewer;

import mage.cards.repository.ExpansionRepository;
import mage.cards.repository.RepositoryEvent;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.gui.FastSearchUtil;
import mage.client.util.sets.ConstructedFormats;
import mage.game.events.Listener;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Pane with big card and mage book.
 *
 * @author nantuko
 */
public final class CollectionViewerPanel extends JPanel {

    private static final Logger logger = Logger.getLogger(CollectionViewerPanel.class);

    protected static final String LAYOYT_CONFIG_KEY = "collectionViewerLayoutConfig";
    private static final String FORMAT_CONFIG_KEY = "collectionViewerFormat";
    private static Listener<RepositoryEvent> setsDbListener = null;

    public CollectionViewerPanel() {
        initComponents();
        try {
            String format = PreferencesDialog.getCachedValue(CollectionViewerPanel.FORMAT_CONFIG_KEY, ConstructedFormats.getDefault());
            formats.setSelectedItem(format);
        } catch (Exception e) {
            logger.fatal("Error setting selected format", e);
        }
    }

    public void cleanUp() {
        this.hidePopup();
        this.bigCard = null;
        ExpansionRepository.instance.unsubscribe(setsDbListener);
    }

    private void reloadFormatCombobox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel<>(ConstructedFormats.getTypes());
        formats.setModel(model);
        formats.setSelectedItem(ConstructedFormats.getDefault());
    }

    public void initComponents() {
        buttonsPanel = new javax.swing.JPanel();
        buttonsPanel.setOpaque(false);
        bigCard = new BigCard();
        BoxLayout boxlayout = new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS);
        buttonsPanel.setLayout(boxlayout);
        btnExit = new javax.swing.JButton();
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsPanel.add(btnExit);

        JLabel label1 = new JLabel("Choose format:");
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label1.setForeground(Color.white);
        buttonsPanel.add(label1);

        // SELECT SET
        // panel
        setPanel = new JPanel();
        setPanel.setLayout(new javax.swing.BoxLayout(setPanel, javax.swing.BoxLayout.LINE_AXIS));
        setPanel.setOpaque(false);
        setPanel.setPreferredSize(new Dimension(200, 25));
        setPanel.setMaximumSize(new Dimension(200, 25));
        setPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsPanel.add(setPanel);
        // combo set
        formats = new JComboBox<>();
        reloadFormatCombobox();
        // auto-update sets list on changes
        setsDbListener = new Listener<RepositoryEvent>() {
            @Override
            public void event(RepositoryEvent event) {
                if (event.getEventType().equals(RepositoryEvent.RepositoryEventType.DB_UPDATED)) {
                    reloadFormatCombobox();
                }
            }
        };
        ExpansionRepository.instance.subscribe(setsDbListener);
        // update cards on format combobox changes
        formats.addActionListener(e -> {
            if (mageBook != null) {
                String format = (String) formats.getSelectedItem();
                MageFrame.getPreferences().put(CollectionViewerPanel.FORMAT_CONFIG_KEY, format);
                mageBook.updateDispayedSets(format);
            }
        });

        formats.setAlignmentX(0.0F);
        formats.setMinimumSize(new Dimension(50, 25));
        formats.setPreferredSize(new Dimension(50, 25));
        formats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        setPanel.add(formats);
        // search button
        btnSetFastSearch = new JButton();
        btnSetFastSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png")));
        btnSetFastSearch.setToolTipText(FastSearchUtil.DEFAULT_EXPANSION_TOOLTIP_MESSAGE);
        btnSetFastSearch.setAlignmentX(1.0F);
        btnSetFastSearch.setMinimumSize(new java.awt.Dimension(24, 24));
        btnSetFastSearch.setPreferredSize(new java.awt.Dimension(32, 32));
        btnSetFastSearch.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSetFastSearch.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FastSearchUtil.showFastSearchForStringComboBox(formats, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE);
            }
        });
        setPanel.add(btnSetFastSearch);

        JLabel label2 = new JLabel("Choose size:");
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        label2.setForeground(Color.white);
        buttonsPanel.add(label2);

        small3x3 = new JRadioButton("3x3");
        small3x3.setForeground(Color.white);
        boolean selected3x3 = MageFrame.getPreferences().get(LAYOYT_CONFIG_KEY, MageBook.LAYOUT_3X3).equals(MageBook.LAYOUT_3X3);
        small3x3.setSelected(selected3x3);
        small3x3.addActionListener(e -> {
            big4x4.setSelected(false);
            mageBook.updateSize(MageBook.LAYOUT_3X3);
            MageFrame.getPreferences().put(LAYOYT_CONFIG_KEY, MageBook.LAYOUT_3X3);
        });
        buttonsPanel.add(small3x3);

        big4x4 = new JRadioButton("4x4");
        big4x4.setForeground(Color.white);
        big4x4.setSelected(!selected3x3);
        big4x4.addActionListener(e -> {
            small3x3.setSelected(false);
            mageBook.updateSize(MageBook.LAYOUT_4X4);
            MageFrame.getPreferences().put(LAYOYT_CONFIG_KEY, MageBook.LAYOUT_4X4);
        });
        buttonsPanel.add(big4x4);

        JLabel label3 = new JLabel("Switch tabs:");
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        label3.setForeground(Color.white);
        buttonsPanel.add(label3);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 100));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsPanel.add(buttonPanel);

        JButton prev = new JButton("Prev");
        prev.addActionListener(e -> mageBook.prev());
        buttonPanel.add(prev);

        JButton next = new JButton("Next");
        next.addActionListener(e -> mageBook.next());
        buttonPanel.add(next);

        JLabel labelCardTokenSwitch = new JLabel("Show cards or tokens:");
        labelCardTokenSwitch.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelCardTokenSwitch.setForeground(Color.white);
        buttonsPanel.add(labelCardTokenSwitch);

        JCheckBox cardsOrTokens = new JCheckBox("Display Cards");
        cardsOrTokens.setSelected(true);
        cardsOrTokens.setForeground(Color.white);
        cardsOrTokens.setToolTipText("Select to show Cards for the chosen set.  When unselected, will show Tokens, Emblems and Planes for the set instead");
        cardsOrTokens.addActionListener(e -> mageBook.cardsOrTokens(cardsOrTokens.isSelected()));
        buttonsPanel.add(cardsOrTokens);

        buttonsPanel.add(Box.createVerticalGlue());

        bigCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bigCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        bigCard.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        buttonsPanel.add(bigCard);

        jPanel2 = new MageBookContainer();
        jPanel2.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 604, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );

        btnExit.setText("Exit");
        btnExit.addActionListener(evt -> btnExitActionPerformed(evt));

    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        this.removeCollectionViewer();
    }

    private void hidePopup() {
        Plugins.instance.getActionCallback().mouseExited(null, null);
    }

    public void removeCollectionViewer() {
        this.cleanUp();
        Component c = this.getParent();
        while (c != null && !(c instanceof CollectionViewerPane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((CollectionViewerPane) c).removeFrame();
        }
    }

    private final class MageBookContainer extends JPanel {

        public MageBookContainer() {
            super();
            initComponents();
        }

        public void initComponents() {
            jPanel = new JPanel();
            jScrollPane1 = new JScrollPane(jPanel);
            jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));

            jPanel.setLayout(new GridBagLayout()); // centers mage book
            jPanel.setBackground(new Color(0, 0, 0, 0));
            mageBook = new MageBook(bigCard);
            jPanel.add(mageBook);

            setLayout(new java.awt.BorderLayout());
            add(jScrollPane1, java.awt.BorderLayout.CENTER);
        }

        private JPanel jPanel;
        private javax.swing.JScrollPane jScrollPane1;
    }

    public void showCards() {
        if (mageBook != null) {
            mageBook.showCardsOrTokens();
        }
    }

    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel setPanel;
    private javax.swing.JButton btnSetFastSearch;
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnExit;
    private JComboBox formats;
    private MageBook mageBook;
    private JRadioButton small3x3;
    private JRadioButton big4x4;

}
