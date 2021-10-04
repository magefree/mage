package mage.client.components.ability;

import mage.abilities.Modes;
import mage.client.SessionHandler;
import mage.client.dialog.MageDialog;
import mage.client.game.GamePanel;
import mage.client.util.ImageHelper;
import mage.remote.Session;
import mage.view.AbilityPickerView;
import org.apache.log4j.Logger;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.swingx.JXPanel;
import org.jsoup.Jsoup;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * GUI: Dialog for choosing abilities (list)
 *
 * @author nantuko, JayDi85
 */
public class AbilityPicker extends JXPanel implements MouseWheelListener {

    private static final String DEFAULT_MESSAGE = "Choose spell or ability to play (single-click)";
    private static final int DIALOG_WIDTH = 440;
    private static final int DIALOG_HEIGHT = 260;

    private static final Logger log = Logger.getLogger(AbilityPicker.class);

    private JList rows;
    private List<Object> choices;
    private String message = DEFAULT_MESSAGE;

    private Session session;
    private UUID gameId;

    private BackgroundPainter mwPanelPainter;
    private JScrollPane jScrollPane2;
    private JLabel title;

    private Image rightImage;
    private Image rightImageHovered;

    private static final String IMAGE_RIGHT_PATH = "/game/right.png";
    private static final String IMAGE_RIGHT_HOVERED_PATH = "/game/right_hovered.png";

    private static final Color SELECTED_COLOR = new Color(64, 147, 208);
    private static Color BORDER_COLOR = new Color(0, 0, 0, 50);

    private boolean selected = false;

    public AbilityPicker() {
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        initComponents();

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        UIManager.put("ScrollBar.width", 17);
        jScrollPane2.getHorizontalScrollBar().setUI(new MageScrollbarUI());
        jScrollPane2.getVerticalScrollBar().setUI(new MageScrollbarUI());
    }

    public AbilityPicker(List<Object> choices, String message) {
        this.choices = choices;
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setMessageAndPrepare(message);
        initComponents();
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        UIManager.put("ScrollBar.width", 17);
        jScrollPane2.getHorizontalScrollBar().setUI(new MageScrollbarUI());
        jScrollPane2.getVerticalScrollBar().setUI(new MageScrollbarUI());
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
    }

    public void cleanUp() {
        for (MouseListener ml : this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
    }

    public void show(AbilityPickerView choices, Point p) {
        this.choices = new ArrayList<>();
        this.selected = true; // to stop previous modal
        setMessageAndPrepare(choices.getMessage());

        // if not cancel from server then add own
        boolean wasCancelButton = false;
        for (Map.Entry<UUID, String> choice : choices.getChoices().entrySet()) {
            wasCancelButton = wasCancelButton || choice.getKey().equals(Modes.CHOOSE_OPTION_CANCEL_ID);
            this.choices.add(new AbilityPickerAction(choice.getKey(), choice.getValue()));
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

        MageDialog.makeWindowCentered(this, DIALOG_WIDTH, DIALOG_HEIGHT);
        //startModal();
    }

    private void initComponents() {
        Color textColor = Color.white;
        mwPanelPainter = new BackgroundPainter();
        jScrollPane2 = new JScrollPane();

        setBackground(textColor);
        setBackgroundPainter(mwPanelPainter);

        title = new JLabel();
        title.setFont(new Font("Times New Roman", 1, 15));
        title.setForeground(textColor);
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
        rows.setMinimumSize(new Dimension(67, 16));
        rows.setOpaque(false);

        // mouse actions
        rows.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    objectMouseClicked(evt);
                }
            }
        });
        rows.setSelectedIndex(0);
        rows.setFont(new Font("Times New Roman", 1, 17));
        rows.setBorder(BorderFactory.createEmptyBorder());
        rows.addMouseWheelListener(this);


        jScrollPane2.setViewportView(rows);
        jScrollPane2.setViewportBorder(BorderFactory.createEmptyBorder());

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(
                GroupLayout.TRAILING,
                layout.createSequentialGroup().addContainerGap().add(
                        layout.createParallelGroup(GroupLayout.TRAILING).add(GroupLayout.LEADING, jScrollPane2, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE).add(GroupLayout.LEADING,
                                layout.createSequentialGroup().add(title).addPreferredGap(LayoutStyle.RELATED, 175, Short.MAX_VALUE).add(1, 1, 1)).add(
                                GroupLayout.LEADING,
                                layout.createSequentialGroup().add(layout.createParallelGroup(GroupLayout.LEADING)
                                )
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(
                                                layout.createParallelGroup(GroupLayout.TRAILING)
                                                        .add(
                                                                GroupLayout.LEADING, layout.createParallelGroup(GroupLayout.LEADING))))).add(10, 10, 10)));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(
                layout.createSequentialGroup().add(
                        layout.createParallelGroup(GroupLayout.LEADING).add(
                                layout.createSequentialGroup().add(title, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                        .add(5, 5, 5)
                                        .add(
                                                layout.createParallelGroup(GroupLayout.BASELINE)
                                        )
                        ).add(layout.createSequentialGroup().add(8, 8, 8)))
                        .addPreferredGap(LayoutStyle.RELATED).add(layout.createParallelGroup(GroupLayout.BASELINE)).addPreferredGap(LayoutStyle.RELATED).add(
                        layout.createParallelGroup(GroupLayout.BASELINE)).addPreferredGap(LayoutStyle.RELATED).add(layout.createParallelGroup(GroupLayout.LEADING)).addPreferredGap(
                        LayoutStyle.RELATED).add(jScrollPane2, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE).addContainerGap(23, Short.MAX_VALUE)));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        int index = rows.getSelectedIndex();

        if (notches < 0) {
            if (index > 0) {
                rows.setSelectedIndex(index - 1);
                rows.repaint();
            }
        } else if (index < choices.size() - 1) {
            rows.setSelectedIndex(index + 1);
            rows.repaint();
        }
    }

    private void objectMouseClicked(MouseEvent event) {
        int index = rows.getSelectedIndex();
        AbilityPickerAction action = (AbilityPickerAction) choices.get(index);
        action.actionPerformed(null);
    }

    public static class ImageRenderer2 extends JEditorPane implements ListCellRenderer {

        public final Map<String, String> cache = new HashMap<>();

        @Override
        public Component getListCellRendererComponent(
                javax.swing.JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {

            setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 6));

            UI.setHTMLEditorKit(this);

            setOpaque(false);
            setBackground(new Color(0, 0, 0, 0));

            String text = value.toString();

            if (cache.containsKey(text)) {
                text = cache.get(text);
            } else {
                StringBuilder buffer = getHtmlForText(isSelected, text);
                String rendered = buffer.toString();
                cache.put(text, rendered);
                text = rendered;
            }

            final String finalText = text;
            // System.out.println(finalText);

            ImageRenderer2.super.setText(finalText);
            setCaretPosition(0);

            return this;
        }

        private StringBuilder getHtmlForText(boolean isSelected, String text) {
            int fontSize = 16;

            String fontFamily = "arial";

            final StringBuilder buffer = new StringBuilder(512);
            buffer.append("<html><body style='font-family:");
            buffer.append(fontFamily);
            buffer.append(";font-size:");
            buffer.append(fontSize);
            buffer.append("pt;margin:3px 3px 3px 3px;");
            if (isSelected) {
                buffer.append("color: #4093D0'>");
            } else {
                buffer.append("color: #FFFFFF'>");
            }
            buffer.append("<b>");

            text = text.replaceAll("#([^#]+)#", "<i>$1</i>");
            text = text.replaceAll("\\s*//\\s*", "<hr width='50%'>");
            text = text.replace("\r\n", "<div style='font-size:5pt'></div>");
            //text += "<br>";

            if (!text.isEmpty()) {
                buffer.append(ManaSymbols.replaceSymbolsWithHTML(text, ManaSymbols.Type.DIALOG));
            }

            buffer.append("</b></body></html>");
            return buffer;
        }

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
        } catch (InterruptedException ignored) {
        }

    }

    public static void main(String[] argv) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        }

        JFrame jframe = new JFrame("Test");

        List<Object> objectList = new ArrayList<>();
        objectList.add("T: add {R}. 111111111111111111111111111");
        objectList.add("T: add {B}. {this} deals 1 damage to you.");
        objectList.add("{T}: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("T: add {B}");
        objectList.add("Cancel");
        AbilityPicker panel = new AbilityPicker(objectList, "Choose ability");
        jframe.add(panel);
        panel.show(objectList);
        jframe.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        jframe.setVisible(true);
    }

    public class AbilityPickerAction extends AbstractAction {

        private final UUID id;

        public AbilityPickerAction(UUID id, String choice) {
            this.id = id;
            putValue(Action.NAME, capitalizeFirstLetter(choice));
        }

        private String capitalizeFirstLetter(String choice) {
            if (choice == null || choice.isEmpty()) {
                return choice;
            }
            choice = Jsoup.parse(choice).text(); // decode HTML entities and strip tags
            return choice.substring(0, 1).toUpperCase(Locale.ENGLISH) + choice.substring(1);
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
            if (action.toString().startsWith(need)) {
                action.actionPerformed(null);
                break;
            }
        }
    }

    public void injectHotkeys(GamePanel panel, String commandsPrefix) {
        // TODO: fix that GamePanel recive imput from any place, not only active (e.g. F9 works from lobby)
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
