/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.deckeditor.collection.viewer;

import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.cards.BigCard;
import mage.client.cards.CardsStorage;
import mage.client.components.HoverButton;
import mage.client.plugins.impl.Plugins;
import mage.client.util.AudioManager;
import mage.client.util.Command;
import mage.client.util.Config;
import mage.client.util.ImageHelper;
import mage.components.ImagePanel;
import mage.view.CardView;
import org.mage.card.arcane.GlowText;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;
import org.mage.card.arcane.ManaSymbols;

/**
 * Mage book with cards and page flipping.
 *
 * @author nantuko
 */
public class MageBook extends JComponent {

	public MageBook(BigCard bigCard) {
		super();
		this.bigCard = bigCard;
		initComponents();
	}

	private void initComponents() {
		setOpaque(false);
		setSize(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		//setBorder(BorderFactory.createLineBorder(Color.green));

		jPanelLeft = getImagePanel(LEFT_PANEL_IMAGE_PATH, ImagePanel.TILED);
		jPanelLeft.setPreferredSize(new Dimension(LEFT_RIGHT_PAGES_WIDTH, 0));
		jPanelLeft.setLayout(null);
		jPanelCenter = getImagePanel(CENTER_PANEL_IMAGE_PATH, ImagePanel.SCALED);
		jPanelCenter.setLayout(new BorderLayout());
		jPanelRight = getImagePanel(RIGHT_PANEL_IMAGE_PATH, ImagePanel.TILED);
		jPanelRight.setPreferredSize(new Dimension(LEFT_RIGHT_PAGES_WIDTH, 0));
		jPanelRight.setLayout(null);

		jLayeredPane = new JLayeredPane();
		jPanelCenter.add(jLayeredPane, BorderLayout.CENTER);

		Image image = ImageHelper.loadImage(LEFT_PAGE_BUTTON_IMAGE_PATH);
		pageLeft = new HoverButton(null, image, image, image, new Rectangle(64, 64));
		pageLeft.setBounds(0, 0, 64, 64);
		pageLeft.setVisible(false);
		pageLeft.setObserver(new Command() {
            @Override
			public void execute() {
				currentPage--;
				if (currentPage == 0) {
					pageLeft.setVisible(false);
				}
				pageRight.setVisible(true);
				AudioManager.playPrevPage();
				showCards();
			}
		});

		image = ImageHelper.loadImage(RIGHT_PAGE_BUTTON_IMAGE_PATH);
		pageRight = new HoverButton(null, image, image, image, new Rectangle(64, 64));
		pageRight.setBounds(WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH - 64, 0, 64, 64);
		pageRight.setVisible(false);
		pageRight.setObserver(new Command() {
            @Override
			public void execute() {
				currentPage++;
				pageLeft.setVisible(true);
				pageRight.setVisible(false);
				AudioManager.playNextPage();
				showCards();
			}
		});

		addSetTabs();

		setLayout(new BorderLayout());
		add(jPanelLeft, BorderLayout.LINE_START);
		add(jPanelCenter, BorderLayout.CENTER);
		add(jPanelRight, BorderLayout.LINE_END);

		cardDimensions = new CardDimensions(0.45d);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showCards();
			}
		});
	}

	private void addLeftRightPageButtons() {
		jLayeredPane.add(pageLeft, JLayeredPane.DEFAULT_LAYER, 0);
		jLayeredPane.add(pageRight, JLayeredPane.DEFAULT_LAYER, 1);
	}

	private void addSetTabs() {
		jPanelLeft.removeAll();
		jPanelRight.removeAll();
		Image image = ImageHelper.loadImage(LEFT_TAB_IMAGE_PATH);
		Image imageRight = ImageHelper.loadImage(RIGHT_TAB_IMAGE_PATH);
		int y = 0;
		int dy = 0;
		if (CardsStorage.getSetCodes().size() > 1) {
			dy = (HEIGHT - 120) / (CardsStorage.getSetCodes().size() - 1) + 1;
		}
		int count = 0;
		JPanel currentPanel = jPanelLeft;
		HoverButton currentTab = null;
		for (String set : CardsStorage.getSetCodes()) {
			HoverButton tab = new HoverButton(null, image, image, image, new Rectangle(39, 120));
			Image setImage = ManaSymbols.getSetSymbolImage(set);
			if (setImage != null) {
				tab.setOverlayImage(setImage);
			} else {
				System.out.println("Couldn't find: " + "/plugins/images/sets/" + set + "-C.jpg");
			}
			tab.setSet(set);
			tab.setBounds(0, y, 39, 120);
			final String _set = set;
			tab.setObserver(new Command() {
                @Override
				public void execute() {
					if (!currentSet.equals(_set) || currentPage != 0) {
						AudioManager.playAnotherTab();
						currentPage = 0;
						currentSet = _set;
						pageLeft.setVisible(false);
						pageRight.setVisible(false);
						addSetTabs();
						showCards();
					}
				}
			});
			currentPanel.add(tab, JLayeredPane.DEFAULT_LAYER + count++, 0);
			y += dy;
			if (set.equals(currentSet)) {
				currentPanel = jPanelRight;
				image = imageRight;
				currentTab = tab;
			}
		}
		jPanelLeft.revalidate();
		jPanelLeft.repaint();
		jPanelRight.revalidate();
		jPanelRight.repaint();
		if (currentTab != null) {
			currentTab.drawSet();
			currentTab.repaint();
		}
	}

	private void showCards() {
		jLayeredPane.removeAll();
		addLeftRightPageButtons();

		java.util.List<Card> cards = getCards(currentPage, currentSet);
		int size = cards.size();

		Rectangle rectangle = new Rectangle();
		rectangle.translate(OFFSET_X, OFFSET_Y);
		for (int i = 0; i < Math.min(CARDS_PER_PAGE / 2, size); i++) {
			addCard(new CardView(cards.get(i)), bigCard, null, rectangle);
			rectangle = CardPosition.translatePosition(i, rectangle);
		}

		// calculate the x offset of the second (right) page
		int second_page_x = (WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH) -
				(cardDimensions.frameWidth + CardPosition.GAP_X) * 3 + CardPosition.GAP_X - OFFSET_X;

		rectangle.setLocation(second_page_x, OFFSET_Y);
		for (int i = CARDS_PER_PAGE / 2; i < Math.min(CARDS_PER_PAGE, size); i++) {
			addCard(new CardView(cards.get(i)), bigCard, null, rectangle);
			rectangle = CardPosition.translatePosition(i - CARDS_PER_PAGE / 2, rectangle);
		}

		jLayeredPane.repaint();
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle) {
		if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		}
		final MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, cardDimension, gameId, true);
		cardImg.setBounds(rectangle);
		jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 10);
		cardImg.update(card);
		cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimensions.frameWidth, cardDimensions.frameHeight);

		boolean implemented = !card.getRarity().equals(mage.Constants.Rarity.NA);

		GlowText label = new GlowText();
		label.setGlow(implemented ? Color.green : NOT_IMPLEMENTED, 12, 0.0f);
		label.setText(implemented ? "Implemented" : "Not implemented");
		int dx = implemented ? 15 : 5;
		label.setBounds(rectangle.x + dx, rectangle.y + cardDimensions.frameHeight + 7, 110, 30);
		jLayeredPane.add(label);
	}

	private java.util.List<Card> getCards(int page, String set) {
		int start = page * CARDS_PER_PAGE;
		int end = (page + 1) * CARDS_PER_PAGE;
		java.util.List<Card> cards = CardsStorage.getAllCards(start, end, currentSet, false);
		if (cards.size() > CARDS_PER_PAGE) {
			pageRight.setVisible(true);
		}
		return cards;
	}

	private ImagePanel getImagePanel(String filename, int type) {
		try {
			InputStream is = this.getClass().getResourceAsStream(filename);

			if (is == null) {
				throw new FileNotFoundException("Couldn't find " + filename + " in resources.");
			}

			BufferedImage background = ImageIO.read(is);

			if (background == null) {
				throw new FileNotFoundException("Couldn't find " + filename + " in resources.");
			}

			return new ImagePanel(background, type);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Defines the position of the next card on the mage book
	 */
	private static class CardPosition {
		private CardPosition() {
		}

		public static Rectangle translatePosition(int index, Rectangle r) {
			Rectangle rect = new Rectangle(r);
			rect.translate((cardDimensions.frameWidth + GAP_X) * dx[index],
					(cardDimensions.frameHeight + GAP_Y) * dy[index]);
			return rect;
		}

		private static final int[] dx = {1, 1, -2, 1, 1, -2, 1, 1, 2, 1, -2, 1, 1, -2, 1, 1};
		private static final int[] dy = {0, 0, 1, 0, 0, 1, 0, 0, -2, 0, 1, 0, 0, 1, 0, 0};
		public static final int GAP_X = 17;
		public static final int GAP_Y = 45;
		private static int cardWidth;
		private static int cardHeight;
	}

	private JPanel jPanelLeft;
	private ImagePanel jPanelCenter;
	private JPanel jPanelRight;
	private JLayeredPane jLayeredPane;
	private BigCard bigCard;
	private HoverButton pageLeft;
	private HoverButton pageRight;

	private int currentPage = 0;
	private String currentSet = "ISD";

	private static CardDimensions cardDimensions = new CardDimensions(1.2d);
	private static final Logger log = Logger.getLogger(MageBook.class);
	private Dimension cardDimension;

	static private final String CENTER_PANEL_IMAGE_PATH = "/book_bg.jpg";
	static private final String RIGHT_PANEL_IMAGE_PATH = "/book_right.jpg";
	static private final String LEFT_PANEL_IMAGE_PATH = "/book_left.jpg";
	static private final String LEFT_PAGE_BUTTON_IMAGE_PATH = "/book_pager_left.png";
	static private final String RIGHT_PAGE_BUTTON_IMAGE_PATH = "/book_pager_right.png";
	static private final String LEFT_TAB_IMAGE_PATH = "/tab_left.png";
	static private final String RIGHT_TAB_IMAGE_PATH = "/tab_right.png";
	static private final int CARDS_PER_PAGE = 18;
	static private final int WIDTH = 950;
	static private final int HEIGHT = 650;
	static private final int OFFSET_X = 25;
	static private final int OFFSET_Y = 20;
	static private final int LEFT_RIGHT_PAGES_WIDTH = 40;
	static private final Color NOT_IMPLEMENTED = new Color(220, 220, 220, 150);
}
