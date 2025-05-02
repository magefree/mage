package mage.client.components.ability;

import mage.abilities.Modes;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.components.ColorPane;
import mage.client.dialog.MageDialog;
import mage.client.game.GamePanel;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.util.CardUtil;
import mage.view.AbilityPickerView;
import org.apache.log4j.Logger;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.swingx.JXPanel;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * GUI: dialog for choosing abilities (list). Example: ability for activate/cast
 *
 * @author nantuko, JayDi85
 */
public class AbilityPicker extends JXPanel implements MouseWheelListener {

    // TODO: add gui scale support (form file lost, so it's ok for scale, see PlayerPanelExt)

    private static final String DEFAULT_MESSAGE = "Choose spell or ability to play (single-click)";
    private static final int DIALOG_WIDTH = 440;
    private static final int DIALOG_HEIGHT = 260;
    private static final String CHOICE_PREFIX = "<html>";

    private static final Logger log = Logger.getLogger(AbilityPicker.class);

    private JList rows;
    private List<Object> choices;
    private String message = DEFAULT_MESSAGE;

    float guiScaleMod = 1.0f;

    private UUID gameId;

    private BackgroundPainter mwPanelPainter;
    private JScrollPane jScrollPane2;
    private ColorPane title;

    private Image rightImage;
    private Image rightImageHovered;

    private static final String IMAGE_RIGHT_PATH = "/game/right.png";
    private static final String IMAGE_RIGHT_HOVERED_PATH = "/game/right_hovered.png";

    private static final Color SELECTED_COLOR = new Color(64, 147, 208);

    private boolean selected = false;

    public AbilityPicker() {
        this(1.0f);
    }

    public AbilityPicker(float guiScaleMod) {
        createAllComponents(guiScaleMod);
    }

    public void init(UUID gameId, BigCard bigCard) {
        this.gameId = gameId;
        this.title.setGameData(gameId, bigCard);
    }

    public void cleanUp() {
        for (MouseListener ml : this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
    }

    private int sizeMod(int value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScaleMod);
    }

    private float sizeMod(float value) {
        return GUISizeHelper.guiSizeScale(value, this.guiScaleMod);
    }

    /**
     * Refresh full panel's components due actual GUI settings
     */
    public void fullRefresh(float guiScaleMod) {
        this.cleanUp();
        this.removeAll();
        this.createAllComponents(guiScaleMod);
        this.invalidate();
    }

    public void createAllComponents(float guiScaleMod) {
        this.guiScaleMod = guiScaleMod;

        setSize(sizeMod(DIALOG_WIDTH), sizeMod(DIALOG_HEIGHT));
        initComponents();

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        UIManager.put("ScrollBar.width", sizeMod(17)); // TODO: is it work?
        jScrollPane2.getHorizontalScrollBar().setUI(new MageScrollbarUI());
        jScrollPane2.getVerticalScrollBar().setUI(new MageScrollbarUI());
    }

    public void show(AbilityPickerView choices, Point p) {
        this.choices = new ArrayList<>();
        this.selected = true; // to stop previous modal
        setMessageAndPrepare(choices.getMessage());

        // if not cancel from server then add own
        boolean wasCancelButton = false;
        for (Map.Entry<UUID, String> choice : choices.getChoices().entrySet()) {
            wasCancelButton = wasCancelButton || choice.getKey().equals(Modes.CHOOSE_OPTION_CANCEL_ID);
            String htmlText = CHOICE_PREFIX + ManaSymbols.replaceSymbolsWithHTML(CardUtil.getTextWithFirstCharUpperCase(choice.getValue()), ManaSymbols.Type.DIALOG);
            this.choices.add(new AbilityPickerAction(choice.getKey(), htmlText));
        }
        if (!wasCancelButton) {
            this.choices.add(new AbilityPickerAction(null, "Cancel"));
        }

        show(this.choices);
    }

    private void show(List<Object> choices) {
        this.choices = choices;
        this.selected = true; // to stop previous modal

        rows.setListData(this.choices.toArray());
        this.rows.setSelectedIndex(0);
        this.selected = false; // back to false - waiting for selection
        this.title.setText(this.message);
        setVisible(true);

        MageDialog.makeWindowCentered(this, this.getWidth(), this.getHeight());
        //startModal();
    }

    private void initComponents() {
        Color textColor = Color.white;
        mwPanelPainter = new BackgroundPainter();
        jScrollPane2 = new JScrollPane();

        setBackground(textColor);
        setBackgroundPainter(mwPanelPainter);

        title = new ColorPane();
        title.setFont(new Font("Times New Roman", Font.BOLD, sizeMod(15)));
        title.setEditable(false);
        title.setFocusCycleRoot(false);
        title.setOpaque(false);
        title.setForeground(textColor);
        title.setExtBackgroundColor(mwPanelPainter);
        title.setBorder(BorderFactory.createEmptyBorder());
        title.enableHyperlinksAndCardPopups();
        title.setText(message);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightImage = ImageHelper.loadImage(IMAGE_RIGHT_PATH);
        rightImageHovered = ImageHelper.loadImage(IMAGE_RIGHT_HOVERED_PATH);

        setOpaque(false);

        rows = new JList();

        rows.setBackground(textColor);
        rows.setCellRenderer(new ImageRenderer());
        rows.ensureIndexIsVisible(rows.getModel().getSize());
        rows.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rows.setLayoutOrientation(JList.VERTICAL);
        rows.setMaximumSize(new Dimension(32767, 32767));
        rows.setMinimumSize(new Dimension(sizeMod(67), sizeMod(16)));
        rows.setOpaque(false);

        // mouse actions
        rows.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt) && !rows.isSelectionEmpty()) {
                    objectMouseClicked(evt);
                }
            }
        });

        rows.setSelectedIndex(0);
        rows.setFont(new Font("Times New Roman", 1, sizeMod(17)));
        rows.setBorder(BorderFactory.createEmptyBorder());
        rows.addMouseWheelListener(this);


        jScrollPane2.setViewportView(rows);
        jScrollPane2.setViewportBorder(BorderFactory.createEmptyBorder());

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(
                GroupLayout.TRAILING,
                layout.createSequentialGroup().addContainerGap().add(
                        layout.createParallelGroup(GroupLayout.TRAILING).add(GroupLayout.LEADING, jScrollPane2, GroupLayout.DEFAULT_SIZE, sizeMod(422), Short.MAX_VALUE).add(GroupLayout.LEADING,
                                layout.createSequentialGroup().add(title).addPreferredGap(LayoutStyle.RELATED, sizeMod(5), Short.MAX_VALUE).add(sizeMod(1), sizeMod(1), sizeMod(1))).add(
                                GroupLayout.LEADING,
                                layout.createSequentialGroup().add(layout.createParallelGroup(GroupLayout.LEADING)
                                        )
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(
                                                layout.createParallelGroup(GroupLayout.TRAILING)
                                                        .add(
                                                                GroupLayout.LEADING, layout.createParallelGroup(GroupLayout.LEADING))))).add(sizeMod(10), sizeMod(10), sizeMod(10))));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(
                layout.createSequentialGroup().add(
                                layout.createParallelGroup(GroupLayout.LEADING).add(
                                        layout.createSequentialGroup().add(title, GroupLayout.PREFERRED_SIZE, sizeMod(72), GroupLayout.PREFERRED_SIZE)
                                                .add(sizeMod(5), sizeMod(5), sizeMod(5))
                                                .add(
                                                        layout.createParallelGroup(GroupLayout.BASELINE)
                                                )
                                ).add(layout.createSequentialGroup().add(sizeMod(8), sizeMod(8), sizeMod(8))))
                        .addPreferredGap(LayoutStyle.RELATED).add(layout.createParallelGroup(GroupLayout.BASELINE)).addPreferredGap(LayoutStyle.RELATED).add(
                                layout.createParallelGroup(GroupLayout.BASELINE)).addPreferredGap(LayoutStyle.RELATED).add(layout.createParallelGroup(GroupLayout.LEADING)).addPreferredGap(
                                LayoutStyle.RELATED).add(jScrollPane2, GroupLayout.PREFERRED_SIZE, sizeMod(180), GroupLayout.PREFERRED_SIZE).addContainerGap(sizeMod(23), Short.MAX_VALUE)));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = e.getWheelRotation() < 0 ? -1 : +1;
        int index = rows.getSelectedIndex() + direction;
        if (index < 0) {
            index = 0;
        } else if (index >= choices.size()) {
            index = choices.size() - 1;
        }

        rows.setSelectedIndex(index);
        rows.repaint();
    }

    private void objectMouseClicked(MouseEvent event) {
        int index = rows.getSelectedIndex();
        AbilityPickerAction action = (AbilityPickerAction) choices.get(index);
        action.actionPerformed(null);
    }

    class ImageRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            JLabel label = ((JLabel) c);

            label.setOpaque(false);
            label.setForeground(Color.white);

            if (choices.size() <= index) {
                return label;
            }

            Object object = choices.get(index);
            String name = object.toString();
            label.setText(name);

            if (isSelected) {
                label.setIcon(new ImageIcon(rightImageHovered));
                label.setForeground(SELECTED_COLOR);
                label.setBorder(BorderFactory.createEmptyBorder());
            } else {
                label.setIcon(new ImageIcon(rightImage));
            }

            return label;
        }

        private static final long serialVersionUID = 7689696087189956997L;
    }

    @Deprecated // do not use modal for it (use PickChoice instead)
    private synchronized void startModal() {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                EventQueue theQueue = getToolkit().getSystemEventQueue();
                while (!selected) {
                    AWTEvent event = theQueue.getNextEvent();
                    Object source = event.getSource();
                    boolean dispatch = true;

                    /*if (event instanceof MouseEvent) {
                        MouseEvent e = (MouseEvent) event;
                        if (e.getID() == MouseEvent.MOUSE_PRESSED || e.getID() == MouseEvent.MOUSE_CLICKED) {
                              MouseEvent m = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this);
                            if (!this.contains(m.getPoint())) {
                                selected = true;
                                cancel();
                                setVisible(false);
                                dispatch = false;
                            }
                        }
                    }*/
                    if (event instanceof MouseEvent) {
                        MouseEvent e = (MouseEvent) event;
                        MouseEvent m = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this);
                        if (!this.contains(m.getPoint()) && e.getID() != MouseEvent.MOUSE_DRAGGED) {
                            dispatch = false;
                        }
                    }

                    if (dispatch) {
                        if (event instanceof ActiveEvent) {
                            ((ActiveEvent) event).dispatch();
                        } else if (source instanceof Component) {
                            ((Component) source).dispatchEvent(event);
                        } else if (source instanceof MenuComponent) {
                            ((MenuComponent) source).dispatchEvent(event);
                        }
                    }
                }
            } else {
                while (!selected) {
                    wait();
                }
            }
        } catch (InterruptedException ignore) {
        }

    }

    public class AbilityPickerAction extends AbstractAction {

        private final UUID id;

        public AbilityPickerAction(UUID id, String choice) {
            this.id = id;
            putValue(Action.NAME, choice);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // cancel
            if (id == null) {
                cancel();
            } else {
                SessionHandler.sendPlayerUUID(gameId, id);
            }
            setVisible(false);
            AbilityPicker.this.selected = true;
        }

        @Override
        public String toString() {
            return (String) getValue(Action.NAME);
        }

    }

    private void cancel() {
        try {
            SessionHandler.sendPlayerBoolean(gameId, false);
        } catch (Exception e) {
            log.error("Couldn't cancel choose dialog: " + e, e);
        }
    }

    private void setMessageAndPrepare(String message) {
        if (message != null) {
            this.message = message + " (single-click or hotkeys)";
        } else {
            this.message = DEFAULT_MESSAGE;
        }
        this.message = "<html>" + this.message;
    }

    private void tryChoiceDone() {
        // done by keyboard
        if (!isVisible() || choices == null) {
            return;
        }
        for (Object obj : choices) {
            AbilityPickerAction action = (AbilityPickerAction) obj;
            if (Modes.CHOOSE_OPTION_DONE_ID.equals(action.id)) {
                action.actionPerformed(null);
                break;
            }
        }
    }

    private void tryChoiceCancel() {
        // cancel by keyboard
        if (!isVisible() || choices == null) {
            return;
        }
        for (Object obj : choices) {
            AbilityPickerAction action = (AbilityPickerAction) obj;
            if (Modes.CHOOSE_OPTION_CANCEL_ID.equals(action.id)) {
                action.actionPerformed(null);
                break;
            }
        }

        // old cancel style (just close dialog)
        for (Object obj : choices) {
            AbilityPickerAction action = (AbilityPickerAction) obj;
            if (action.id == null) {
                cancel();
                break;
            }
        }
    }

    private void tryChoiceOption(int choiceNumber) {
        // choice by keyboard
        if (!isVisible() || choices == null) {
            return;
        }
        String need = choiceNumber + ".";
        for (Object obj : choices) {
            AbilityPickerAction action = (AbilityPickerAction) obj;
            if (action.toString().startsWith(CHOICE_PREFIX + need)) {
                action.actionPerformed(null);
                break;
            }
        }
    }

    public void injectHotkeys(GamePanel panel, String commandsPrefix) {
        // TODO: fix that GamePanel receive input from any place, not only active (e.g. F9 works from lobby)
        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;

        // choice keys
        Map<Integer, Integer> numbers = new HashMap<>();
        numbers.put(KeyEvent.VK_1, 1);
        numbers.put(KeyEvent.VK_2, 2);
        numbers.put(KeyEvent.VK_3, 3);
        numbers.put(KeyEvent.VK_4, 4);
        numbers.put(KeyEvent.VK_5, 5);
        numbers.put(KeyEvent.VK_6, 6);
        numbers.put(KeyEvent.VK_7, 7);
        numbers.put(KeyEvent.VK_8, 8);
        numbers.put(KeyEvent.VK_9, 9);
        numbers.forEach((vk, num) -> {
            KeyStroke ks = KeyStroke.getKeyStroke(vk, 0);
            panel.getInputMap(c).put(ks, commandsPrefix + "_CHOOSE_" + num);
            panel.getActionMap().put(commandsPrefix + "_CHOOSE_" + num, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    tryChoiceOption(num);
                }
            });
        });

        // done key (space, enter)
        panel.getInputMap(c).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), commandsPrefix + "_CHOOSE_DONE");
        panel.getInputMap(c).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), commandsPrefix + "_CHOOSE_DONE");
        panel.getActionMap().put(commandsPrefix + "_CHOOSE_DONE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryChoiceDone();
            }
        });

        // cancel key (esc can be lost by focus on exile window popup)
        panel.getInputMap(c).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), commandsPrefix + "_CHOOSE_CANCEL");
        panel.getActionMap().put(commandsPrefix + "_CHOOSE_CANCEL", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryChoiceCancel();
            }
        });
    }
}
