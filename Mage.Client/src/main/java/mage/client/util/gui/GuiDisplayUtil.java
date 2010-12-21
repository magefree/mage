package mage.client.util.gui;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import mage.Constants.CardType;
import mage.utils.CardUtil;
import mage.view.CardView;

import org.jdesktop.swingx.JXPanel;

public class GuiDisplayUtil {
	private static final Font cardNameFont = new Font("Calibri", Font.BOLD, 15);
    private static Insets DEFAULT_INSETS = new Insets(0,0, 68, 15);
	
    public static JXPanel getDescription(CardView card, int width, int height) {
    	JXPanel descriptionPanel = new JXPanel();
		
    	//descriptionPanel.setAlpha(.8f);
		descriptionPanel.setBounds(0, 0,  width, height);
		descriptionPanel.setVisible(false);
		descriptionPanel.setLayout(null);
		
		//descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JButton j = new JButton("");
		j.setBounds(0, 0,  width, height);
		j.setBackground(Color.black);
		j.setLayout(null);

		JLabel name = new JLabel("Wrath of God");
		name.setBounds(5, 5,  width - 90, 20);
		name.setForeground(Color.white);
		name.setFont(cardNameFont);
		//name.setBorder(BorderFactory.createLineBorder(Color.green));
		j.add(name);
		
		JLabel cost = new JLabel("B R G W U");
		cost.setBounds(width - 85, 5, 77, 20);
		cost.setForeground(Color.white);
		cost.setFont(cardNameFont);
		//cost.setBorder(BorderFactory.createLineBorder(Color.green));
		cost.setHorizontalAlignment(SwingConstants.RIGHT);
		j.add(cost);
		
		JLabel type = new JLabel("Creature - Goblin Shaman");
		type.setBounds(5, 70,  width - 8, 20);
		type.setForeground(Color.white);
		type.setFont(cardNameFont);
		//type.setBorder(BorderFactory.createLineBorder(Color.green));
		j.add(type);
		
		JLabel cardText = new JLabel();
		cardText.setBounds(5, 100,  width - 8, 260);
		cardText.setForeground(Color.white);
		cardText.setFont(cardNameFont);
		cardText.setVerticalAlignment(SwingConstants.TOP);
		//cardText.setBorder(new EtchedBorder());
		j.add(cardText);
		
		name.setText(card.getName());
		cost.setText(card.getManaCost().toString());
		String typeText = "";
		String delimiter = card.getCardTypes().size() > 1 ? " - " : "";
		for (CardType t : card.getCardTypes()) {
			typeText += t;
			typeText += delimiter;
			delimiter = " "; // next delimiters are just spaces
		}
		type.setText(typeText);
		cardText.setText("<html>"+card.getRules()+"</html>");
		
		if (CardUtil.isCreature(card)) {
			JLabel pt = new JLabel(card.getPower() + "/" + card.getToughness());
			pt.setBounds(width - 50, height - 30, 40, 20);
			pt.setForeground(Color.white);
			pt.setFont(cardNameFont);
			pt.setHorizontalAlignment(JLabel.RIGHT);
			j.add(pt);
		}
		
		descriptionPanel.add(j);
		
		return descriptionPanel;
    }

    public static String cleanString(String in) {
        StringBuffer out = new StringBuffer();
        char c;
        for (int i = 0; i < in.length(); i++) {
            c = in.charAt(i);
            if (c == ' ' || c == '-')
                out.append('_');
            else if (Character.isLetterOrDigit(c)) {
                out.append(c);
            }
        }
        return out.toString().toLowerCase();
    }


	public static void keepComponentInsideScreen(int x, int y, Component c) {
		Dimension screenDim = c.getToolkit().getScreenSize();
		GraphicsConfiguration g = c.getGraphicsConfiguration();
		if (g != null) {
			Insets insets =	c.getToolkit().getScreenInsets(g);
	
			if (x + c.getWidth() > screenDim.width - insets.right) {
				x = (screenDim.width - insets.right) - c.getWidth();
			} else if (x < insets.left) {
				x = insets.left;
			}
	
			if (y + c.getHeight() > screenDim.height - insets.bottom) {
				y = (screenDim.height - insets.bottom) - c.getHeight();
			} else if (y < insets.top) {
				y = insets.top;
			}
	
			c.setLocation(x, y);
		} else {
			System.out.println("null");
		}
	}

    public static Point keepComponentInsideParent(Point l, Point parentPoint, Component c, Component parent) {
        int dx = parentPoint.x + parent.getWidth() - DEFAULT_INSETS.right;
        if (l.x + c.getWidth() > dx) {
            l.x = dx - c.getWidth();
        }

        int dy = parentPoint.y + parent.getHeight() - DEFAULT_INSETS.bottom;
        if (l.y + c.getHeight() > dy) {
            l.y = Math.max(10, dy - c.getHeight());
        }

        return l;
	}
}
