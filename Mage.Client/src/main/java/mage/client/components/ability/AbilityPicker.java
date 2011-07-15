package mage.client.components.ability;

import com.sun.deploy.util.ArrayUtil;
import mage.client.MageFrame;
import mage.client.util.ImageHelper;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.remote.Session;
import mage.view.AbilityPickerView;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Dialog for choosing abilities.
 *
 * @author nantuko
 */
public class AbilityPicker extends JXPanel implements MouseWheelListener {

    private static final String DEFAULT_MESSAGE = "Choose spell or ability to play (double-click)";;
    private static final int DIALOG_WIDTH = 320;
    private static final int DIALOG_HEIGHT = 240;

    private JList rows;
    private List<Object> choices;
    private String message = DEFAULT_MESSAGE;

    private Session session;
	private UUID gameId;

    private BackgroundPainter mwPanelPainter;
	private JScrollPane jScrollPane2;
    private JTextField title;

    private Image rightImage;
    private Image rightImageHovered;

    private static final String IMAGE_RIGHT_PATH = "/game/right.png";
    private static final String IMAGE_RIGHT_HOVERED_PATH = "/game/right_hovered.png";

    private static Color SELECTED_COLOR = new Color(64,147,208);
    private static Color BORDER_COLOR = new Color(0,0,0,50);

    public AbilityPicker() {
        initComponents();
        addMouseWheelListener(this);
        setSize(320, 240);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jScrollPane2.setOpaque(false);
                jScrollPane2.getViewport().setOpaque(false);
                //jScrollPane2.getHorizontalScrollBar().setUI(new MageScrollbarUI());
                //jScrollPane2.getVerticalScrollBar().setUI(new MageScrollbarUI());
            }
        });
    }

    public AbilityPicker(List<Object> choices, String message) {
		this.choices = choices;
		if (message!= null) {
			this.message = message + " (double-click)";
		}
		initComponents();
		jScrollPane2.setOpaque(false);
		jScrollPane2.getViewport().setOpaque(false);
		jScrollPane2.getHorizontalScrollBar().setUI(new MageScrollbarUI());
		jScrollPane2.getVerticalScrollBar().setUI(new MageScrollbarUI());

		addMouseWheelListener(this);
	}

    public void init(Session session, UUID gameId) {
		this.session = session;
		this.gameId = gameId;
	}

    public void show(AbilityPickerView choices, Point p) {
		if (p == null) return;
        this.choices = new ArrayList<Object>();

		for (Map.Entry<UUID, String> choice: choices.getChoices().entrySet()) {
			this.choices.add(new AbilityPickerAction(choice.getKey(), choice.getValue()));
		}
        this.choices.add(new AbilityPickerAction(null, "Cancel"));

        Point centered = SettingsManager.getInstance().getComponentPosition(320, 240);
        this.setLocation(centered.x, centered.y);
		GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, this);
	}

    public void initComponents() {
        JLabel jLabel1;
		JLabel jLabel3;

		Color textColor = Color.white;

		mwPanelPainter = new BackgroundPainter();
		jLabel1 = new JLabel();
		jLabel3 = new JLabel();

		title = new JTextField();
		jScrollPane2 = new JScrollPane();

		setBackground(textColor);
		setBackgroundPainter(mwPanelPainter);
		jLabel1.setFont(new Font("Times New Roman", 1, 18));
		jLabel1.setForeground(textColor);
		jLabel1.setText(message);

		jLabel3.setForeground(textColor);
		jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabel3.setText("Selected:");

		title.setFont(new Font("Tahoma", 1, 11));
		title.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		jScrollPane2.setBorder(null);
		jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		rightImage = ImageHelper.loadImage(IMAGE_RIGHT_PATH);
		rightImageHovered = ImageHelper.loadImage(IMAGE_RIGHT_HOVERED_PATH);

		//BufferedImage[] images = new BufferedImage[choices.size()];
		//rows = new JList(images);
        rows = new JList();
        rows.setModel (
            new AbstractListModel() {
                public int getSize() {
                    if (AbilityPicker.this.choices == null) {
                        return 0;
                    }
                    return AbilityPicker.this.choices.size();
                }
                public Object getElementAt(int i) { return AbilityPicker.this.choices.get(i); }
            }
        );

		rows.setBackground(textColor);
		rows.setCellRenderer(new ImageRenderer());
		rows.ensureIndexIsVisible(rows.getModel().getSize());
		rows.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rows.setLayoutOrientation(JList.VERTICAL);
		rows.setMaximumSize(new Dimension(32767, 32767));
		rows.setMinimumSize(new Dimension(67, 16));
		rows.setOpaque(false);
		rows.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() > 1) {
                    objectMouseClicked(evt);
                }
            }
        });
		rows.setSelectedIndex(0);
		rows.setFont(new Font("Times New Roman", 1, 17));

		rows.addMouseWheelListener(this);

		jScrollPane2.setViewportView(rows);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				layout.createSequentialGroup().addContainerGap().add(
						layout.createParallelGroup(GroupLayout.TRAILING).add(GroupLayout.LEADING, jScrollPane2, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE).add(GroupLayout.LEADING,
								layout.createSequentialGroup().add(jLabel1).addPreferredGap(LayoutStyle.RELATED, 175, Short.MAX_VALUE).add(1, 1, 1)).add(
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
								layout.createSequentialGroup().add(jLabel1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
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
				rows.setSelectedIndex(index-1);
				rows.repaint();
			}
		} else {
			if (index < choices.size() - 1) {
				rows.setSelectedIndex(index+1);
				rows.repaint();
			}
		}
    }

    private void objectMouseClicked(MouseEvent event) {
        AbilityPickerAction action = (AbilityPickerAction)choices.get(rows.getSelectedIndex());
        action.actionPerformed(null);
	}

    class ImageRenderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			JLabel label = ((JLabel) c);
			label.setOpaque(false);
			label.setForeground(Color.white);

			if (choices.size() <= index) return label;

			Object object = choices.get(index);
			String name = object.toString();
			label.setText(name);

			if (isSelected) {
				label.setIcon(new ImageIcon(rightImageHovered));
				label.setForeground(SELECTED_COLOR);
				label.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
			} else {
				label.setIcon(new ImageIcon(rightImage));
			}

			return label;
		}

		private static final long serialVersionUID = 7689696087189956997L;
	}

    public static void main(String[] argv) {
		JFrame jframe = new JFrame("Test");

		List<Object> objectList = new ArrayList<Object>();
		objectList.add("T: add {R} to your mana pool");
		objectList.add("T: add {B} to your mana pool");
		objectList.add("Cancel");
		AbilityPicker panel = new AbilityPicker(objectList, "Choose ability");
		jframe.add(panel);
		jframe.setSize(640, 480);
		jframe.setVisible(true);
	}

    public class AbilityPickerAction extends AbstractAction {

		private UUID id;

		public AbilityPickerAction(UUID id, String choice) {
			this.id = id;
			putValue(Action.NAME, choice);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
            // cancel
            if (id == null) {
                session.sendPlayerBoolean(gameId, false);
            } else {
			    session.sendPlayerUUID(gameId, id);
            }
			setVisible(false);
		}

        @Override
        public String toString() {
            return (String)getValue(Action.NAME);
        }

	}
}
