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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static java.lang.Math.min;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.*;
import mage.cards.*;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.client.util.audio.AudioManager;
import mage.client.util.sets.ConstructedFormats;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.constants.Rarity;
import mage.game.command.Emblem;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.view.CardView;
import mage.view.EmblemView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;
import org.mage.plugins.card.images.CardDownloadData;
import static org.mage.plugins.card.images.DownloadPictures.getTokenCardUrls;

/**
 * Mage book with cards and page flipping.
 *
 * @author nantuko
 */
public class MageBook extends JComponent {

    private static final long serialVersionUID = 1L;

    public static final String LAYOUT_3x3 = "small";

    public static final String LAYOUT_4x4 = "big";

    public MageBook(BigCard bigCard) {
        super();
        this.bigCard = bigCard;
        this.setsToDisplay = ConstructedFormats.getSetsByFormat(ConstructedFormats.getDefault());
        boolean selected3x3 = MageFrame.getPreferences().get(CollectionViewerPanel.LAYOYT_CONFIG_KEY, MageBook.LAYOUT_3x3).equals(MageBook.LAYOUT_3x3);
        this.conf = selected3x3 ? new _3x3Configuration() : new _4x4Configuration();
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setSize(conf.WIDTH, conf.HEIGHT);
        setPreferredSize(new Dimension(conf.WIDTH, conf.HEIGHT));
        setMinimumSize(new Dimension(conf.WIDTH, conf.HEIGHT));
        //setBorder(BorderFactory.createLineBorder(Color.green));

        jPanelLeft = getImagePanel(LEFT_PANEL_IMAGE_PATH, ImagePanelStyle.TILED);
        jPanelLeft.setPreferredSize(new Dimension(LEFT_RIGHT_PAGES_WIDTH, 0));
        jPanelLeft.setLayout(null);
        jPanelCenter = getImagePanel(CENTER_PANEL_IMAGE_PATH, ImagePanelStyle.SCALED);
        jPanelCenter.setLayout(new BorderLayout());
        jPanelRight = getImagePanel(RIGHT_PANEL_IMAGE_PATH, ImagePanelStyle.TILED);
        jPanelRight.setPreferredSize(new Dimension(LEFT_RIGHT_PAGES_WIDTH, 0));
        jPanelRight.setLayout(null);

        jLayeredPane = new JLayeredPane();
        jPanelCenter.add(jLayeredPane, BorderLayout.CENTER);

        Image image = ImageHelper.loadImage(LEFT_PAGE_BUTTON_IMAGE_PATH);
        pageLeft = new HoverButton(null, image, image, image, new Rectangle(64, 64));
        //pageLeft.setBorder(BorderFactory.createLineBorder(new Color(180, 50, 0), 3, true)); //debug
        pageLeft.setBounds(0, 0, 64, 64);
        pageLeft.setVisible(false);
        pageLeft.setObserver(() -> {
            currentPage--;
            if (currentPage == 0) {
                pageLeft.setVisible(false);
            }
            pageRight.setVisible(true);
            AudioManager.playPrevPage();
            showCardsOrTokens();
        });

        image = ImageHelper.loadImage(RIGHT_PAGE_BUTTON_IMAGE_PATH);
        pageRight = new HoverButton(null, image, image, image, new Rectangle(64, 64));
        pageRight.setBounds(conf.WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH - 64, 0, 64, 64);
        pageRight.setVisible(false);
        pageRight.setObserver(() -> {
            currentPage++;
            pageLeft.setVisible(true);
            pageRight.setVisible(false);
            AudioManager.playNextPage();
            showCardsOrTokens();
        });

        addSetTabs();

        setLayout(new BorderLayout());
        add(jPanelLeft, BorderLayout.LINE_START);
        add(jPanelCenter, BorderLayout.CENTER);
        add(jPanelRight, BorderLayout.LINE_END);

        int captionHeight = Math.max(30, pageLeft.getHeight()); // caption size = next-prev images

        // Top Panel (left page + (caption / stats) + right page
        jPanelTop = new JPanel();
        jPanelTop.setLayout(new BorderLayout());
        // jPanelTop.setBorder(BorderFactory.createLineBorder(new Color(180, 50, 150), 3, true)); // debug
        jPanelTop.setPreferredSize(new Dimension(captionHeight, captionHeight));
        jPanelCenter.add(jPanelTop, BorderLayout.NORTH);

        // page left
        pageRight.setPreferredSize(new Dimension(pageRight.getWidth(), pageRight.getHeight()));
        jPanelTop.add(pageRight, BorderLayout.EAST);
        // page right
        pageLeft.setPreferredSize(new Dimension(pageLeft.getWidth(), pageLeft.getHeight()));
        jPanelTop.add(pageLeft, BorderLayout.WEST);

        // Caption Panel
        jPanelCaption = new JPanel();
        jPanelCaption.setLayout(new BorderLayout());
        jPanelCaption.setOpaque(false);
        jPanelTop.add(jPanelCaption, BorderLayout.CENTER);

        // set's caption
        setCaption = new JLabel();
        setCaption.setHorizontalAlignment(SwingConstants.CENTER);
        //setCaption.setBorder(BorderFactory.createLineBorder(new Color(180, 50, 150), 3, true)); // debug
        setCaption.setFont(jLayeredPane.getFont().deriveFont(25f));
        setCaption.setText("EMPTY CAPTION");
        jPanelCaption.add(setCaption, BorderLayout.NORTH);

        // set's info
        setInfo = new JLabel();
        setInfo.setHorizontalAlignment(SwingConstants.CENTER);
        //setCaption.setBorder(BorderFactory.createLineBorder(new Color(180, 50, 150), 3, true)); // debug
        setInfo.setFont(jLayeredPane.getFont().deriveFont(17f));
        setInfo.setText("EMPTY STATS");
        jPanelCaption.add(setInfo, BorderLayout.SOUTH);

        cardDimensions = new CardDimensions(0.45d);
    }

    private void addSetTabs() {
        jPanelLeft.removeAll();
        jPanelRight.removeAll();
        tabs.clear();
        Image image = ImageHelper.loadImage(LEFT_TAB_IMAGE_PATH);
        Image imageRight = ImageHelper.loadImage(RIGHT_TAB_IMAGE_PATH);
        int y = 0;
        int dy = 0;
        if (this.setsToDisplay.size() > 1) {
            dy = (conf.HEIGHT - 120) / (this.setsToDisplay.size() - 1) + 1;
        }
        int count = 0;
        JPanel currentPanel = jPanelLeft;
        HoverButton currentTab = null;
        for (String set : this.setsToDisplay) {
            HoverButton tab = new HoverButton(null, image, image, image, new Rectangle(39, 120));
            Image setImage = ManaSymbols.getSetSymbolImage(set);
            if (setImage != null) {
                tab.setOverlayImage(setImage);
            } else {
                System.out.println("Couldn't find symbol image: " + set + "-C.jpg");
            }
            tab.setSet(set);
            tab.setBounds(0, y, 39, 120);
            final String _set = set;
            final int _index = count;
            tab.setObserver(() -> {
                if (!currentSet.equals(_set) || currentPage != 0 || stateChanged) {
                    AudioManager.playAnotherTab();
                    synchronized (MageBook.this) {
                        selectedTab = _index;
                    }
                    currentPage = 0;
                    currentSet = _set;
                    pageLeft.setVisible(false);
                    pageRight.setVisible(false);
                    addSetTabs();
                    showCardsOrTokens();
                }
            });
            tabs.add(tab);
            currentPanel.add(tab, JLayeredPane.DEFAULT_LAYER + count++, 0);
            y += dy;
            if (set.equals(currentSet)) {
                currentPanel = jPanelRight;
                image = imageRight;
                currentTab = tab;
                selectedTab = count - 1;
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

    private void showCardsOrTokens() {
        stateChanged = false;
        if (showCardsOrTokens) {
            updateCardStats(currentSet, true);
            showCards();
        } else {
            updateCardStats(currentSet, false);
            int numTokens = showTokens();
            showEmblems(numTokens);
        }
    }

    public void showCards() {
        jLayeredPane.removeAll();

        // stats info
        updateCardStats(currentSet, true);

        List<CardInfo> cards = getCards(currentPage, currentSet);
        int size = cards.size();

        Rectangle rectangle = new Rectangle();
        rectangle.translate(OFFSET_X, OFFSET_Y);
        for (int i = 0; i < min(conf.CARDS_PER_PAGE / 2, size); i++) {
            Card card = cards.get(i).getMockCard();
            addCard(new CardView(card), bigCard, null, rectangle);

            rectangle = CardPosition.translatePosition(i, rectangle, conf);
        }

        // calculate the x offset of the second (right) page
        int second_page_x = (conf.WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH)
                - (cardDimensions.frameWidth + CardPosition.GAP_X) * conf.CARD_COLUMNS + CardPosition.GAP_X - OFFSET_X;

        rectangle.setLocation(second_page_x, OFFSET_Y);
        for (int i = conf.CARDS_PER_PAGE / 2; i < min(conf.CARDS_PER_PAGE, size); i++) {
            Card card = cards.get(i).getMockCard();
            addCard(new CardView(card), bigCard, null, rectangle);
            rectangle = CardPosition.translatePosition(i - conf.CARDS_PER_PAGE / 2, rectangle, conf);
        }

        jLayeredPane.repaint();
    }

    public int showTokens() {
        jLayeredPane.removeAll();
        List<Token> tokens = getTokens(currentPage, currentSet);
        if (tokens != null && tokens.size() > 0) {
            int size = tokens.size();
            Rectangle rectangle = new Rectangle();
            rectangle.translate(OFFSET_X, OFFSET_Y);
            for (int i = 0; i < min(conf.CARDS_PER_PAGE / 2, size); i++) {
                Token token = tokens.get(i);
                addToken(token, bigCard, null, rectangle);
                rectangle = CardPosition.translatePosition(i, rectangle, conf);
            }

            // calculate the x offset of the second (right) page
            int second_page_x = (conf.WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH)
                    - (cardDimensions.frameWidth + CardPosition.GAP_X) * conf.CARD_COLUMNS + CardPosition.GAP_X - OFFSET_X;

            rectangle.setLocation(second_page_x, OFFSET_Y);
            for (int i = conf.CARDS_PER_PAGE / 2; i < min(conf.CARDS_PER_PAGE, size); i++) {
                Token token = tokens.get(i);
                addToken(token, bigCard, null, rectangle);
                rectangle = CardPosition.translatePosition(i - conf.CARDS_PER_PAGE / 2, rectangle, conf);
            }

            jLayeredPane.repaint();
        }

        return tokens.size();
    }

    public void showEmblems(int numTokens) {
        List<Emblem> emblems = getEmblems(currentPage, currentSet, numTokens);
        // System.out.println ("Size of origins in " + currentSet + " = " + emblems.size());
        if (emblems != null && emblems.size() > 0) {
            int size = emblems.size();
            Rectangle rectangle = new Rectangle();
            rectangle.translate(OFFSET_X, OFFSET_Y);
            // calculate the x offset of the second (right) page
            int second_page_x = (conf.WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH)
                    - (cardDimensions.frameWidth + CardPosition.GAP_X) * conf.CARD_COLUMNS + CardPosition.GAP_X - OFFSET_X;

            // Already have numTokens tokens presented. Appending the emblems to the end of these.
            numTokens = numTokens % conf.CARDS_PER_PAGE;
            if (numTokens < conf.CARDS_PER_PAGE / 2) {
                for (int z = 0; z < numTokens && z < conf.CARDS_PER_PAGE / 2; z++) {
                    rectangle = CardPosition.translatePosition(z, rectangle, conf);
                }
            } else {
                rectangle.setLocation(second_page_x, OFFSET_Y);
                for (int z = 0; z < numTokens - conf.CARDS_PER_PAGE / 2; z++) {
                    rectangle = CardPosition.translatePosition(z, rectangle, conf);
                }
            }

            int lastI = 0;
            for (int i = 0; i < size && i + numTokens < conf.CARDS_PER_PAGE / 2; i++) {
                Emblem emblem = emblems.get(i);
                addEmblem(emblem, bigCard, null, rectangle);
                rectangle = CardPosition.translatePosition(i + numTokens, rectangle, conf);
                lastI++;
            }

            if (size + numTokens > conf.CARDS_PER_PAGE / 2) {
                for (int i = lastI; i < size && i + numTokens < conf.CARDS_PER_PAGE; i++) {
                    Emblem emblem = emblems.get(i);
                    addEmblem(emblem, bigCard, null, rectangle);
                    rectangle = CardPosition.translatePosition(i + numTokens - conf.CARDS_PER_PAGE / 2, rectangle, conf);
                }
            }

            jLayeredPane.repaint();
        }
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        final MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, cardDimension, gameId, true, true);
        cardImg.setBounds(rectangle);
        jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 10);
        cardImg.update(card);
        cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimensions.frameWidth, cardDimensions.frameHeight);

        cardImg.setCardCaptionTopOffset(8); // card caption below real card caption to see full name even with mana icons

        boolean implemented = card.getRarity() != Rarity.NA;

        // implemented label
        // old code, nowadays app load only implemented cards (JayDi85, 23.11.2017)
        /*
        GlowText label = new GlowText();
        label.setGlow(implemented ? Color.green : NOT_IMPLEMENTED, 12, 0.0f);
        label.setText(implemented ? "Implemented" : "Not implemented");
        int dx = implemented ? 15 : 5;
        label.setBounds(rectangle.x + dx, rectangle.y + cardDimensions.frameHeight + 7, 110, 30);
        jLayeredPane.add(label);
         */
        // card number label
        JLabel cardNumber = new JLabel();
        int dy = -5; // image panel have empty space in bottom (bug?), need to move label up
        cardNumber.setBounds(rectangle.x, rectangle.y + cardImg.getHeight() + dy, cardDimensions.frameWidth, 20);
        cardNumber.setHorizontalAlignment(SwingConstants.CENTER);
        //cardNumber.setBorder(BorderFactory.createLineBorder(new Color(180, 50, 150), 3, true));
        cardNumber.setFont(jLayeredPane.getFont().deriveFont(jLayeredPane.getFont().getStyle() | Font.BOLD));
        cardNumber.setText(card.getCardNumber());
        jLayeredPane.add(cardNumber);
    }

    private void addToken(Token token, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        PermanentToken newToken = new PermanentToken(token, null, token.getOriginalExpansionSetCode(), null);
        PermanentView theToken = new PermanentView(newToken, null, null, null);
        theToken.setInViewerOnly(true);
        final MageCard cardImg = Plugins.instance.getMagePermanent(theToken, bigCard, cardDimension, gameId, true);
        cardImg.setBounds(rectangle);
        jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 10);
        cardImg.update(theToken);
        cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimensions.frameWidth, cardDimensions.frameHeight);
    }

    private void addEmblem(Emblem emblem, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        CardView cardView = new CardView(new EmblemView(emblem));
        addCard(cardView, bigCard, gameId, rectangle);
    }

    private List<CardInfo> getCards(int page, String set) {
        CardCriteria criteria = new CardCriteria();
        criteria.setCodes(set);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);
        cards.sort(new NaturalOrderCardNumberComparator());
        int start = page * conf.CARDS_PER_PAGE;
        int end = page * conf.CARDS_PER_PAGE + conf.CARDS_PER_PAGE;
        if (end > cards.size()) {
            end = cards.size();
        }
        if (cards.size() > end) {
            pageRight.setVisible(true);
        }
        return cards.subList(start, end);
    }

    private void updateCardStats(String setCode, boolean isCardsShow) {
        // sets do not have total cards number, it's a workaround

        ExpansionSet set = Sets.findSet(setCode);
        if (set != null) {
            setCaption.setText(set.getCode() + " - " + set.getName());
        } else {
            setCaption.setText("ERROR");
            setInfo.setText("ERROR");
            return;
        }

        if (!isCardsShow) {
            // tokens or emblems, stats not need
            setInfo.setText("");
            return;
        }

        // cards stats
        int startNumber = 9999;
        int endNumber = 0;

        List<ExpansionSet.SetCardInfo> cards = set.getSetCardInfo();

        // first run for numbers list
        LinkedList<Integer> haveNumbers = new LinkedList<>();
        for (ExpansionSet.SetCardInfo card : cards) {
            int cardNumber = card.getCardNumberAsInt();

            // skip xmage special numbers for cards (TODO: replace full art cards numbers from 180+20 to 180b, 180c and vice versa like scryfall)
            if (cardNumber > 500) {
                continue;
            }

            startNumber = min(startNumber, cardNumber);
            endNumber = Math.max(endNumber, cardNumber);
            haveNumbers.add(cardNumber);
        }

        // second run for empty numbers
        int countHave = haveNumbers.size();
        int countNotHave = 0;
        if (cards.size() > 0) {
            for (int i = startNumber; i <= endNumber; i++) {
                if (!haveNumbers.contains(i)) {
                    countNotHave++;
                }
            }
        }

        // result
        setInfo.setText(String.format("Have %d cards of %d", countHave, countHave + countNotHave));
        if (countNotHave > 0) {
            setInfo.setForeground(new Color(150, 0, 0));
        } else {
            setInfo.setForeground(jLayeredPane.getForeground());
        }
    }

    private List<Token> getTokens(int page, String set) {
        ArrayList<CardDownloadData> allTokens = getTokenCardUrls();
        ArrayList<Token> tokens = new ArrayList<>();

        for (CardDownloadData token : allTokens) {
            if (token.getSet().equals(set)) {
                try {
                    String className = token.getName();
                    className = className.replaceAll("[^a-zA-Z0-9]", "");
                    className = "mage.game.permanent.token." + className + "Token";
                    if (token.getTokenClassName() != null && token.getTokenClassName().length() > 0) {
                        if (token.getTokenClassName().toLowerCase(Locale.ENGLISH).matches(".*token.*")) {
                            className = token.getTokenClassName();
                            className = "mage.game.permanent.token." + className;
                        } else if (token.getTokenClassName().toLowerCase(Locale.ENGLISH).matches(".*emblem.*")) {
                            continue;
                        }
                    }
                    Class<?> c = Class.forName(className);
                    Constructor<?> cons = c.getConstructor();
                    Object newToken = cons.newInstance();
                    if (newToken != null && newToken instanceof mage.game.permanent.token.Token) {
                        ((Token) newToken).setExpansionSetCodeForImage(set);
                        ((Token) newToken).setOriginalExpansionSetCode(set);
                        ((Token) newToken).setTokenType(token.getType());
                        tokens.add((Token) newToken);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    // Swallow exception
                }
            }
        }
        int start = page * conf.CARDS_PER_PAGE;
        int end = page * conf.CARDS_PER_PAGE + conf.CARDS_PER_PAGE;
        if (end > tokens.size()) {
            end = tokens.size();
        }
        if (tokens.size() > end) {
            pageRight.setVisible(true);
        }
        return tokens.subList(start, end);
    }

    private List<Emblem> getEmblems(int page, String set, int numTokens) {
        ArrayList<CardDownloadData> allEmblems = getTokenCardUrls();
        ArrayList<Emblem> emblems = new ArrayList<>();

        for (CardDownloadData emblem : allEmblems) {
            if (emblem.getSet().equals(set)) {
                try {
                    String className = emblem.getName();
                    if (emblem.getTokenClassName() != null && emblem.getTokenClassName().length() > 0) {
                        if (emblem.getTokenClassName().toLowerCase(Locale.ENGLISH).matches(".*emblem.*")) {
                            className = emblem.getTokenClassName();
                            className = "mage.game.command.emblems." + className;
                        }
                    } else {
                        continue;
                    }
                    Class<?> c = Class.forName(className);
                    Constructor<?> cons = c.getConstructor();
                    Object newEmblem = cons.newInstance();
                    if (newEmblem != null && newEmblem instanceof mage.game.command.Emblem) {
                        ((Emblem) newEmblem).setExpansionSetCodeForImage(set);

                        emblems.add((Emblem) newEmblem);
                    }
                } catch (ClassNotFoundException ex) {
                    // Swallow exception
                } catch (NoSuchMethodException ex) {
                    // Swallow exception
                } catch (SecurityException ex) {
                    // Swallow exception
                } catch (InstantiationException ex) {
                    // Swallow exception
                } catch (IllegalAccessException ex) {
                    // Swallow exception
                } catch (IllegalArgumentException ex) {
                    // Swallow exception
                } catch (InvocationTargetException ex) {
                    // Swallow exception
                }
            }
        }
        int start = 0;
        int end = emblems.size();

        if ((page + 1) * conf.CARDS_PER_PAGE < numTokens + emblems.size()) {
            end = (page + 1) * conf.CARDS_PER_PAGE - numTokens;
            pageRight.setVisible(true);
        }

        if (emblems.size() > conf.CARDS_PER_PAGE) {
            pageLeft.setVisible(true);
            pageRight.setVisible(true);
        }
        return emblems.subList(start, end);
    }

    private ImagePanel getImagePanel(String filename, ImagePanelStyle type) {
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

    public void updateDispayedSets(String format) {
        this.setsToDisplay = ConstructedFormats.getSetsByFormat(format);
        if (this.setsToDisplay.isEmpty()) {
            // display all
            this.setsToDisplay = ExpansionRepository.instance.getSetCodes();
        }
        addSetTabs();
        tabs.get(0).execute();
    }

    public void next() {
        synchronized (this) {
            selectedTab++;
            if (selectedTab >= tabs.size()) {
                selectedTab = 0;
            }
            tabs.get(selectedTab).execute();
        }
    }

    public void prev() {
        synchronized (this) {
            selectedTab--;
            if (selectedTab < 0) {
                selectedTab = tabs.size() - 1;
            }
            tabs.get(selectedTab).execute();
        }
    }

    public void cardsOrTokens(boolean showCards) {
        synchronized (this) {
            selectedTab = 0;
            showCardsOrTokens = !showCardsOrTokens;
            stateChanged = true;
            tabs.get(selectedTab).execute();
        }
    }

    public void updateSize(String size) {
        switch (size) {
            case LAYOUT_3x3:
                this.conf = new _3x3Configuration();
                break;
            case LAYOUT_4x4:
                this.conf = new _4x4Configuration();
                break;
            default:
                return;
        }
        currentPage = 0;
        pageLeft.setVisible(false);
        setSize(conf.WIDTH, conf.HEIGHT);
        setPreferredSize(new Dimension(conf.WIDTH, conf.HEIGHT));
        setMinimumSize(new Dimension(conf.WIDTH, conf.HEIGHT));
        addSetTabs();
        showCards();
    }

    /**
     * Defines the position of the next card on the mage book
     */
    private static final class CardPosition {

        private CardPosition() {
        }

        public static Rectangle translatePosition(int index, Rectangle r, Configuration conf) {
            Rectangle rect = new Rectangle(r);
            rect.translate((cardDimensions.frameWidth + GAP_X) * conf.dx[index],
                    (cardDimensions.frameHeight + GAP_Y) * conf.dy[index]);
            return rect;
        }

        public static final int GAP_X = 17;
        public static final int GAP_Y = 45;
    }

    abstract static class Configuration {

        public int CARDS_PER_PAGE;
        public int CARD_ROWS;
        public int CARD_COLUMNS;
        public int WIDTH;
        public int HEIGHT;

        public int[] dx;
        public int[] dy;
    }

    class _3x3Configuration extends Configuration {

        _3x3Configuration() {
            this.WIDTH = 950;
            this.HEIGHT = 650 + 50; // + for caption
            CARD_ROWS = 3;
            CARD_COLUMNS = 3;
            this.CARDS_PER_PAGE = 18;
            this.dx = new int[]{1, 1, -2, 1, 1, -2, 1, 1, 2, 1, -2, 1, 1, -2, 1, 1};
            this.dy = new int[]{0, 0, 1, 0, 0, 1, 0, 0, -2, 0, 1, 0, 0, 1, 0, 0};
        }
    }

    class _4x4Configuration extends Configuration {

        _4x4Configuration() {
            this.WIDTH = 1250;
            this.HEIGHT = 850 + 50; // + for caption
            CARD_ROWS = 4;
            CARD_COLUMNS = 4;
            this.CARDS_PER_PAGE = 32;
            this.dx = new int[]{1, 1, 1, -3, 1, 1, 1, -3, 1, 1, 1, -3, 1, 1, 1, -3};
            this.dy = new int[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1};
        }
    }

    private JPanel jPanelTop;
    private JPanel jPanelCaption;
    private JLabel setCaption;
    private JLabel setInfo;
    private JPanel jPanelLeft;
    private ImagePanel jPanelCenter;
    private JPanel jPanelRight;
    private JLayeredPane jLayeredPane;
    private final BigCard bigCard;
    private HoverButton pageLeft;
    private HoverButton pageRight;

    private int currentPage = 0;
    private String currentSet = "RTR";
    private int currentCardsInSet = 0;
    private int currentCardsNotInSet = 0;

    private static CardDimensions cardDimensions = new CardDimensions(1.2d);
    private static final Logger log = Logger.getLogger(MageBook.class);
    private Dimension cardDimension;
    private java.util.List<String> setsToDisplay = new ArrayList<>();
    private final java.util.List<HoverButton> tabs = new ArrayList<>();
    private int selectedTab;
    private boolean showCardsOrTokens = true;
    private boolean stateChanged = false;

    private static final String CENTER_PANEL_IMAGE_PATH = "/book_bg.jpg";
    private static final String RIGHT_PANEL_IMAGE_PATH = "/book_right.jpg";
    private static final String LEFT_PANEL_IMAGE_PATH = "/book_left.jpg";
    private static final String LEFT_PAGE_BUTTON_IMAGE_PATH = "/book_pager_left.png";
    private static final String RIGHT_PAGE_BUTTON_IMAGE_PATH = "/book_pager_right.png";
    private static final String LEFT_TAB_IMAGE_PATH = "/tab_left.png";
    private static final String RIGHT_TAB_IMAGE_PATH = "/tab_right.png";
    private static final int OFFSET_X = 25;
    private static final int OFFSET_Y = 20;
    private static final int LEFT_RIGHT_PAGES_WIDTH = 40;
    private static final Color NOT_IMPLEMENTED = new Color(220, 220, 220, 150);

    private Configuration conf;
}
