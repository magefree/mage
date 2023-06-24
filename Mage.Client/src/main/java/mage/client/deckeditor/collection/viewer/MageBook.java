package mage.client.deckeditor.collection.viewer;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.CardDimensions;
import mage.cards.ExpansionSet;
import mage.cards.MageCard;
import mage.cards.Sets;
import mage.cards.repository.*;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.HoverButton;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.ImageHelper;
import mage.client.util.NaturalOrderCardNumberComparator;
import mage.client.util.audio.AudioManager;
import mage.client.util.sets.ConstructedFormats;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.draft.RateCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.view.*;
import org.apache.log4j.Logger;
import org.mage.card.arcane.ManaSymbols;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

/**
 * Card viewer (mage book) with cards and page flipping
 *
 * @author nantuko, JayDi85
 */
public class MageBook extends JComponent {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(MageBook.class);

    public static final String LAYOUT_3X3 = "small";
    public static final String LAYOUT_4X4 = "big";
    private static final int CARD_CAPTION_OFFSET_Y = 8; // apply offset to see card names with mana icons at the same time

    public MageBook(BigCard bigCard) {
        super();
        this.bigCard = bigCard;
        this.setsToDisplay = ConstructedFormats.getSetsByFormat(ConstructedFormats.getDefault());
        boolean selected3x3 = MageFrame.getPreferences().get(CollectionViewerPanel.LAYOYT_CONFIG_KEY, MageBook.LAYOUT_3X3).equals(MageBook.LAYOUT_3X3);
        this.conf = selected3x3 ? new _3x3Configuration() : new _4x4Configuration();
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setSize(conf.WIDTH, conf.HEIGHT);
        setPreferredSize(new Dimension(conf.WIDTH, conf.HEIGHT));
        setMinimumSize(new Dimension(conf.WIDTH, conf.HEIGHT));

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
        setCaption.setFont(jLayeredPane.getFont().deriveFont(25f));
        setCaption.setText("EMPTY CAPTION");
        jPanelCaption.add(setCaption, BorderLayout.NORTH);

        // set's info
        setInfo = new JLabel();
        setInfo.setHorizontalAlignment(SwingConstants.CENTER);
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

    public void showCardsOrTokens() {
        stateChanged = false;
        updateCardStats(currentSet, showCardsOrTokens);

        List<Object> items;
        if (showCardsOrTokens) {
            // cards
            items = loadCards();
        } else {
            items = loadTokens();
        }
        showItems(items);
    }

    public List<Object> loadTokens() {
        List<Object> res = new ArrayList<>();

        // tokens
        List<TokenInfo> allTokens = TokenRepository.instance.getByType(TokenType.TOKEN)
                .stream()
                .filter(token -> token.getSetCode().equals(currentSet))
                .collect(Collectors.toList());
        allTokens.forEach(token -> {
            TokenImpl newToken = TokenImpl.createTokenByClassName(token.getFullClassFileName());
            if (newToken != null) {
                newToken.setExpansionSetCode(currentSet);
                newToken.setImageNumber(token.getImageNumber());
                res.add(newToken);
            }
        });

        // emblems
        List<TokenInfo> allEmblems = TokenRepository.instance.getByType(TokenType.EMBLEM)
                .stream()
                .filter(token -> token.getSetCode().equals(currentSet))
                .collect(Collectors.toList());
        allEmblems.forEach(token -> {
            try {
                Class<?> c = Class.forName(token.getFullClassFileName());
                Constructor<?> cons = c.getConstructor();
                Object newEmblem = cons.newInstance();
                if (newEmblem instanceof Emblem) {
                    ((Emblem) newEmblem).setExpansionSetCode(currentSet);
                    res.add(newEmblem);
                }
            } catch (Exception e) {
                // ignore error
            }
        });

        // planes
        List<TokenInfo> allPlanes = TokenRepository.instance.getByType(TokenType.PLANE)
                .stream()
                .filter(token -> token.getSetCode().equals(currentSet))
                .collect(Collectors.toList());
        allPlanes.forEach(token -> {
            try {
                Class<?> c = Class.forName(token.getFullClassFileName());
                Constructor<?> cons = c.getConstructor();
                Object newPlane = cons.newInstance();
                if (newPlane instanceof Plane) {
                    ((Plane) newPlane).setExpansionSetCode(currentSet);
                    res.add(newPlane);
                }
            } catch (Exception e) {
                // ignore error
            }
        });

        // dungeons
        List<TokenInfo> allDungeons = TokenRepository.instance.getByType(TokenType.DUNGEON)
                .stream()
                .filter(token -> token.getSetCode().equals(currentSet))
                .collect(Collectors.toList());
        allDungeons.forEach(token -> {
            try {
                Class<?> c = Class.forName(token.getFullClassFileName());
                Constructor<?> cons = c.getConstructor();
                Object newDungeon = cons.newInstance();
                if (newDungeon instanceof Dungeon) {
                    ((Dungeon) newDungeon).setExpansionSetCode(currentSet);
                    res.add(newDungeon);
                }
            } catch (Exception e) {
                // ignore error
            }
        });

        return res;
    }

    public void showItems(List<Object> allItems) {
        jLayeredPane.removeAll();

        int start = currentPage * conf.CARDS_PER_PAGE;
        int end = Math.min(allItems.size(), start + conf.CARDS_PER_PAGE);
        List<Object> needItems = allItems.subList(start, end); // end is exclusive from sublist

        // visible pages
        pageRight.setVisible(allItems.size() > end);
        pageLeft.setVisible(currentPage > 0);

        if (!needItems.isEmpty()) {
            // start position
            int size = needItems.size();
            Rectangle rectangle = new Rectangle();
            rectangle.translate(OFFSET_X, OFFSET_Y);

            // left side
            for (int i = 0; i < min(conf.CARDS_PER_PAGE / 2, size); i++) {
                addItem(needItems.get(i), rectangle);
                rectangle = CardPosition.translatePosition(i, rectangle, conf);
            }

            // calculate the x offset of the second (right) page
            int second_page_x = (conf.WIDTH - 2 * LEFT_RIGHT_PAGES_WIDTH)
                    - (cardDimensions.getFrameWidth() + CardPosition.GAP_X) * conf.CARD_COLUMNS + CardPosition.GAP_X - OFFSET_X;

            // right side
            rectangle.setLocation(second_page_x, OFFSET_Y);
            for (int i = conf.CARDS_PER_PAGE / 2; i < min(conf.CARDS_PER_PAGE, size); i++) {
                addItem(needItems.get(i), rectangle);
                rectangle = CardPosition.translatePosition(i - conf.CARDS_PER_PAGE / 2, rectangle, conf);
            }

            jLayeredPane.repaint();
        }
    }

    private void addItem(Object item, Rectangle position) {
        if (item instanceof CardView) {
            addCard((CardView) item, bigCard, null, position, true);
        } else if (item instanceof Token) {
            addToken((Token) item, bigCard, null, position);
        } else if (item instanceof Emblem) {
            addEmblem((Emblem) item, bigCard, null, position);
        } else if (item instanceof Dungeon) {
            addDungeon((Dungeon) item, bigCard, null, position);
        } else if (item instanceof Plane) {
            addPlane((Plane) item, bigCard, null, position);
        } else {
            logger.error("ERROR, unknown extra item type in mage viewer: " + item.getClass().getCanonicalName(), new Throwable());
        }
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle, boolean asCard) {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        }
        final MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, new CardIconRenderSettings(), cardDimension, gameId, true, true, PreferencesDialog.getRenderMode(), true);
        cardImg.setCardContainerRef(jLayeredPane);
        cardImg.update(card);
        cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimensions.getFrameWidth(), cardDimensions.getFrameHeight());
        jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 10);

        // card caption must be below real card caption to see full name even with mana icons
        cardImg.setCardCaptionTopOffset(CARD_CAPTION_OFFSET_Y);

        if (asCard) {
            // card number label
            JLabel cardNumber = new JLabel();
            int dy = -5; // image panel have empty space in bottom (bug?), need to move label up
            cardNumber.setBounds(rectangle.x, rectangle.y + cardImg.getCardLocation().getCardHeight() + dy, cardDimensions.getFrameWidth(), 20);
            cardNumber.setHorizontalAlignment(SwingConstants.CENTER);
            cardNumber.setFont(jLayeredPane.getFont().deriveFont(jLayeredPane.getFont().getStyle() | Font.BOLD));
            cardNumber.setText(card.getCardNumber());
            jLayeredPane.add(cardNumber);

            // draft rating label
            JLabel draftRating = new JLabel();
            dy = -5 * 2 + cardNumber.getHeight(); // under card number
            draftRating.setBounds(rectangle.x, rectangle.y + cardImg.getCardLocation().getCardHeight() + dy, cardDimensions.getFrameWidth(), 20);
            draftRating.setHorizontalAlignment(SwingConstants.CENTER);
            draftRating.setFont(jLayeredPane.getFont().deriveFont(jLayeredPane.getFont().getStyle() | Font.BOLD));
            if (card.getOriginalCard() != null) {
                // card
                draftRating.setText("draft rating: " + RateCard.rateCard(card.getOriginalCard(), null));
            } else {
                // token
                draftRating.setText("");
            }
            jLayeredPane.add(draftRating);
        }
    }

    private void addToken(Token token, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        }
        PermanentToken newToken = new PermanentToken(token, null, null);
        newToken.removeSummoningSickness();
        PermanentView theToken = new PermanentView(newToken, null, null, null);
        theToken.setInViewerOnly(true);
        final MageCard cardImg = Plugins.instance.getMagePermanent(theToken, bigCard, new CardIconRenderSettings(), cardDimension, gameId, true, PreferencesDialog.getRenderMode(), true);
        cardImg.setCardContainerRef(jLayeredPane);
        jLayeredPane.add(cardImg, JLayeredPane.DEFAULT_LAYER, 10);
        cardImg.update(theToken);
        cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimensions.getFrameWidth(), cardDimensions.getFrameHeight());
    }

    private void addEmblem(Emblem emblem, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        CardView cardView = new CardView(new EmblemView(emblem));
        addCard(cardView, bigCard, gameId, rectangle, false);
    }

    private void addDungeon(Dungeon dungeon, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        CardView cardView = new CardView(new DungeonView(dungeon));
        addCard(cardView, bigCard, gameId, rectangle, false);
    }

    private void addPlane(Plane plane, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        CardView cardView = new CardView(new PlaneView(plane));
        addCard(cardView, bigCard, gameId, rectangle, false);
    }

    private List<Object> loadCards() {
        CardCriteria criteria = new CardCriteria();
        criteria.setCodes(currentSet);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);
        cards.sort(new NaturalOrderCardNumberComparator());
        List<Object> res = new ArrayList<>();
        cards.forEach(card -> res.add(new CardView(card.getMockCard())));
        return res;
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
        List<Integer> haveNumbers = set
                .getSetCardInfo()
                .stream()
                .map(ExpansionSet.SetCardInfo::getCardNumberAsInt)
                .collect(Collectors.toList());

        int startNumber = haveNumbers
                .stream()
                .min(Integer::compareTo)
                .orElse(9999);
        int endNumber = haveNumbers
                .stream()
                .max(Integer::compareTo)
                .orElse(0);

        // second run for empty numbers
        int countHave = haveNumbers.size();
        int countNotHave = IntStream
                .range(startNumber, endNumber + 1)
                .map(x -> haveNumbers.contains(x) ? 0 : 1)
                .sum();

        // result
        setInfo.setText(String.format("%d cards of %d are available", countHave, countHave + countNotHave));
        if (countNotHave > 0) {
            setInfo.setForeground(new Color(150, 0, 0));
        } else {
            setInfo.setForeground(jLayeredPane.getForeground());
        }
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
            case LAYOUT_3X3:
                this.conf = new _3x3Configuration();
                break;
            case LAYOUT_4X4:
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
        showCardsOrTokens();
    }

    /**
     * Defines the position of the next card on the mage book
     */
    private static final class CardPosition {

        private CardPosition() {
        }

        public static Rectangle translatePosition(int index, Rectangle r, Configuration conf) {
            Rectangle rect = new Rectangle(r);
            rect.translate((cardDimensions.getFrameWidth() + GAP_X) * conf.dx[index],
                    (cardDimensions.getFrameHeight() + GAP_Y) * conf.dy[index]);
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
