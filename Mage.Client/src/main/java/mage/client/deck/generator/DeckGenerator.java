package mage.client.deck.generator;
 
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.cards.CardsStorage;
import mage.client.util.gui.ColorsChooser;
import mage.client.util.sets.ConstructedFormats;
import mage.interfaces.rate.RateCallback;
import mage.sets.Sets;
import mage.utils.CardUtil;
import mage.utils.DeckBuilder;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
 
/**
 * Generates random card pool and builds a deck.
 *
 * @author nantuko
 */
public class DeckGenerator {
 
    private static JDialog dlg;
    private static String selectedColors;
    private static JComboBox formats;
 
    private static final int SPELL_CARD_POOL_SIZE = 180;
 
    private static final int DECK_LANDS = 16;
    private static final int MAX_NON_BASIC_SOURCE = DECK_LANDS / 2;
 
    private static final boolean GENERATE_RANDOM_BASIC_LAND = true;
    private static final int MAX_TRIES = 4096;
 
    private static Deck deck = new Deck();
    private static final int ADDITIONAL_CARDS_FOR_3_COLOR_DECKS = 20;
 
    private static String colors = "GWUBR";
 
    /**
     * Opens color chooser dialog. Generates deck.
     * Saves generated deck and use it as selected deck to play.
     *
     * @return
     */
    public static String generateDeck() {
        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));
 
        JLabel text = new JLabel("Choose color for your deck: ");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        p0.add(text);
 
        p0.add(Box.createVerticalStrut(5));
        String chosen = MageFrame.getPreferences().get("genDeckColor", "u");
        final ColorsChooser colorsChooser = new ColorsChooser(chosen);
        p0.add(colorsChooser);
 
        p0.add(Box.createVerticalStrut(5));
        JLabel text2 = new JLabel("(X - random color)");
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p0.add(text2);
 
        p0.add(Box.createVerticalStrut(5));
        JPanel jPanel = new JPanel();
        JLabel text3 = new JLabel("Choose format:");
        formats = new JComboBox(ConstructedFormats.getTypes());
        formats.setSelectedIndex(0);
        formats.setPreferredSize(new Dimension(300, 25));
        formats.setMaximumSize(new Dimension(300, 25));
        formats.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(text3);
        jPanel.add(formats);
        p0.add(jPanel);
 
        final JButton btnGenerate = new JButton("Ok");
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGenerate.setEnabled(false);
                colorsChooser.setEnabled(false);
                selectedColors = (String) colorsChooser.getSelectedItem();
                dlg.setVisible(false);
                MageFrame.getPreferences().put("genDeckColor", selectedColors);
            }
        });
        final JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                selectedColors = null;
            }
        });
        Object[] options = {btnGenerate, btnCancel};
        JOptionPane optionPane = new JOptionPane(p0, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        dlg = optionPane.createDialog("Generating deck");
        dlg.setVisible(true);
        dlg.dispose();
 
        if (selectedColors != null) {
            buildDeck();
            try {
                File tmp = File.createTempFile("tempDeck" + UUID.randomUUID().toString(), ".dck");
                tmp.createNewFile();
                deck.setName("Generated-Deck-" + UUID.randomUUID());
                Sets.saveDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
                //JOptionPane.showMessageDialog(null, "Deck has been generated.");
                return tmp.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Couldn't generate deck. Try once again.");
            }
        }
 
        return selectedColors;
    }
 
    /**
     * Generates card pool
     */
    protected static void buildDeck() {
        List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
        selectedColors = selectedColors.toUpperCase();
 
        String format = (String)formats.getSelectedItem();
        List<String> setsToUse = ConstructedFormats.getSetsByFormat(format);
        if (setsToUse.isEmpty()) {
            // use all
            setsToUse = CardsStorage.getSetCodes();
        }
 
        if (selectedColors.contains("X")) {
            selectedColors = getRandomColors(selectedColors);
        }
 
        for (int i = 0; i < selectedColors.length(); i++) {
            char c = selectedColors.charAt(i);
            allowedColors.add(ColoredManaSymbol.lookup(c));
        }
 
        int cardPoolSize = SPELL_CARD_POOL_SIZE;
        if (selectedColors.length() > 2) {
            cardPoolSize += ADDITIONAL_CARDS_FOR_3_COLOR_DECKS;
        }
        List<Card> spellCardPool = generateSpellCardPool(cardPoolSize, allowedColors, setsToUse);
        List<Card> landCardPool = generateNonBasicLandCardPool(MAX_NON_BASIC_SOURCE, allowedColors, setsToUse);
 
        System.out.println("deck generator card pool: spells=" + spellCardPool.size() + ", lands=" + landCardPool.size());
 
        final List<String> setsToUseFinal = setsToUse;
 
        deck = DeckBuilder.buildDeck(spellCardPool, allowedColors, landCardPool, new RateCallback() {
            @Override
            public int rateCard(Card card) {
                return CardsStorage.rateCard(card);
            }
            @Override
            public Card getBestBasicLand(ColoredManaSymbol color) {
                int tries = 100;
                Card land;
                do {
                    land = DeckGenerator.getBestBasicLand(color);
                    tries--;
                    if (tries < 0) break;
                } while (!setsToUseFinal.contains(land.getExpansionSetCode()));
                return land;
            }
        });
    }
 
    private static String getRandomColors(String _selectedColors) {
        StringBuilder generatedColors = new StringBuilder();
        Set<String> colors = new HashSet<String>();
        for (int i = 0; i < _selectedColors.length(); i++) {
            String color = getRandomColor() + "";
            int retry = 100;
            while (colors.contains(color)) {
                color = getRandomColor() + "";
                retry--;
                if (retry <= 0) break;
            }
            generatedColors.append(color);
            colors.add(color);
        }
        return generatedColors.toString();
    }
 
    private static char getRandomColor() {
        Random r = new Random();
        return colors.charAt(r.nextInt(colors.length()));
    }
 
    /**
     * Generates card pool of cardsCount cards that have manacost of allowed colors.
     *
     * @param cardsCount
     * @param allowedColors
     * @return
     */
    private static List<Card> generateSpellCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors, List<String> setsToUse) {
        List<Card> spellCardPool = new ArrayList<Card>();
 
        int count = 0;
        List<Card> cardPool = new ArrayList<Card>();
        for (Card card : CardsStorage.getAllCards()) {
            if (setsToUse.contains(card.getExpansionSetCode())) {
                cardPool.add(card);
            }
        }
        int cardPoolCount = cardPool.size();
        Random random = new Random();
        if (cardPoolCount > 0) {
            int tries = 0;
            while (count < cardsCount) {
                Card card = cardPool.get(random.nextInt(cardPoolCount));
                if (!card.getCardType().contains(CardType.LAND)) {
                    if (cardFitsChosenColors(card, allowedColors)) {
                        spellCardPool.add(card);
                        count++;
                    }
                }
                tries++;
                if (tries > MAX_TRIES) { // to avoid infinite loop
                    throw new IllegalStateException("Not enough cards for chosen colors to generate deck: " + selectedColors);
                }
            }
        } else {
            throw new IllegalStateException("Not enough cards to generate deck.");
        }
 
        return spellCardPool;
    }
 
    /**
     * Check that card can be played using chosen (allowed) colors.
     *
     * @param card
     * @param allowedColors
     * @return
     */
    private static boolean cardFitsChosenColors(Card card, List<ColoredManaSymbol> allowedColors) {
        for (String symbol : card.getManaCost().getSymbols()) {
            boolean found = false;
            symbol = symbol.replace("{", "").replace("}", "");
            if (isColoredMana(symbol)) {
                for (ColoredManaSymbol allowed : allowedColors) {
                    if (symbol.contains(allowed.toString())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        return true;
    }
 
    /**
     * Generates card pool of land cards that can produce allowed colors.
     *
     * @param landsCount
     * @param allowedColors
     * @return
     */
    private static List<Card> generateNonBasicLandCardPool(int landsCount, List<ColoredManaSymbol> allowedColors, List<String> setsToUse) {
        List<Card> nonBasicLandCardPool = new ArrayList<Card>();
 
        int count = 0;
        List<Card> landCards = new ArrayList<Card>();
        for (Card land : CardsStorage.getNonBasicLandCards()) {
            if (setsToUse.contains(land.getExpansionSetCode())) {
                landCards.add(land);
            }
        }
        int allCount = landCards.size();
        Random random = new Random();
        if (allCount > 0) {
            int tries = 0;
            while (count < landsCount) {
                Card card = landCards.get(random.nextInt(allCount));
                if (!CardUtil.isBasicLand(card)) {
                    if (cardCardProduceChosenColors(card, allowedColors)) {
                        nonBasicLandCardPool.add(card);
                        count++;
                    }
                }
                tries++;
                if (tries > MAX_TRIES) { // to avoid infinite loop
                    // return what have been found
                    return nonBasicLandCardPool;
                }
            }
        }
 
        return nonBasicLandCardPool;
    }
 
    /**
     * Checks that chosen card can produce mana of specific color.
     *
     * @param card
     * @param allowedColors
     * @return
     */
    private static boolean cardCardProduceChosenColors(Card card, List<ColoredManaSymbol> allowedColors) {
        int score = 0;
        for (Mana mana : card.getMana()) {
            for (ColoredManaSymbol color : allowedColors) {
                score += mana.getColor(color);
            }
        }
        if (score > 1) {
            return true;
        }
        return false;
    }
 
    /**
     * Get random basic land that can produce specified color mana.
     * Random here means random set and collector id for the same mana producing land.
     *
     * @param color
     * @return
     */
    private static Card getBestBasicLand(ColoredManaSymbol color) {
        if (color.equals(ColoredManaSymbol.G)) {
            return Sets.findCard("Forest", GENERATE_RANDOM_BASIC_LAND);
        }
        if (color.equals(ColoredManaSymbol.R)) {
            return Sets.findCard("Mountain", GENERATE_RANDOM_BASIC_LAND);
        }
        if (color.equals(ColoredManaSymbol.B)) {
            return Sets.findCard("Swamp", GENERATE_RANDOM_BASIC_LAND);
        }
        if (color.equals(ColoredManaSymbol.U)) {
            return Sets.findCard("Island", GENERATE_RANDOM_BASIC_LAND);
        }
        if (color.equals(ColoredManaSymbol.W)) {
            return Sets.findCard("Plains", GENERATE_RANDOM_BASIC_LAND);
        }
 
        return null;
    }
 
    protected static boolean isColoredMana(String symbol) {
        return symbol.equals("W") || symbol.equals("G") || symbol.equals("U") || symbol.equals("B") || symbol.equals("R") || symbol.contains("/");
    }
 
    public static void main(String[] args) {
        Card selesnyaGuildMage = null;
        for (Card card : CardsStorage.getAllCards()) {
            if (card.getName().equals("Selesnya Guildmage")) {
                selesnyaGuildMage = card;
                break;
            }
        }
        if (selesnyaGuildMage == null) {
            throw new RuntimeException("Couldn't find card: Selesnya Guildmage");
        }
        List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
        allowedColors.add(ColoredManaSymbol.lookup('B'));
        allowedColors.add(ColoredManaSymbol.lookup('R'));
        System.out.println(DeckGenerator.cardFitsChosenColors(selesnyaGuildMage, allowedColors));
 
        allowedColors.clear();
        allowedColors = new ArrayList<ColoredManaSymbol>();
        allowedColors.add(ColoredManaSymbol.lookup('G'));
        System.out.println(DeckGenerator.cardFitsChosenColors(selesnyaGuildMage, allowedColors));
 
        allowedColors.clear();
        allowedColors = new ArrayList<ColoredManaSymbol>();
        allowedColors.add(ColoredManaSymbol.lookup('W'));
        System.out.println(DeckGenerator.cardFitsChosenColors(selesnyaGuildMage, allowedColors));
    }
}