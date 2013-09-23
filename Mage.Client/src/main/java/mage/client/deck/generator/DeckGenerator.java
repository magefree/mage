package mage.client.deck.generator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mage.Mana;
import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.ColorsChooser;
import mage.client.util.sets.ConstructedFormats;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.interfaces.rate.RateCallback;
import mage.utils.DeckBuilder;
import mage.view.TournamentTypeView;


/**
 * Generates random card pool and builds a deck.
 *
 * @author nantuko
 */
public class DeckGenerator {

    private static JDialog dlg;
    private static String selectedColors;
    private static JComboBox cbSets;
    private static JComboBox cbDeckSize;

    private static final int SPELL_CARD_POOL_SIZE = 180;

    private static final int DECK_LANDS = 17;
    private static final int MAX_NON_BASIC_SOURCE = DECK_LANDS / 2;

    private static final int MAX_TRIES = 4096;

    private static Deck deck = new Deck();
    private static final int ADDITIONAL_CARDS_FOR_3_COLOR_DECKS = 20;

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
        JLabel text3 = new JLabel("Choose sets:");
        cbSets = new JComboBox(ConstructedFormats.getTypes());
        cbSets.setSelectedIndex(0);
        cbSets.setPreferredSize(new Dimension(300, 25));
        cbSets.setMaximumSize(new Dimension(300, 25));
        cbSets.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(text3);
        jPanel.add(cbSets);
        p0.add(jPanel);

        String prefSet = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, null);
        if (prefSet != null) {
            cbSets.setSelectedItem(prefSet);
        }

        p0.add(Box.createVerticalStrut(5));
        JPanel jPanel2 = new JPanel();
        JLabel textDeckSize = new JLabel("Deck size:");
        cbDeckSize = new JComboBox(new String[]{"40","60"});
        cbDeckSize.setSelectedIndex(0);
        cbDeckSize.setPreferredSize(new Dimension(300, 25));
        cbDeckSize.setMaximumSize(new Dimension(300, 25));
        cbDeckSize.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel2.add(textDeckSize);
        jPanel2.add(cbDeckSize);
        p0.add(jPanel2);

        String prefSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, "60");
        if (prefSet != null) {
            cbDeckSize.setSelectedItem(prefSize);
        }

        final JButton btnGenerate = new JButton("Ok");
        btnGenerate.addActionListener(new ActionListener() {
            @Override
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
            @Override
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
            // save values to prefs
             PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, cbDeckSize.getSelectedItem().toString());
             PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, cbSets.getSelectedItem().toString());

            // build deck
            buildDeck();
            try {
                File tmp = File.createTempFile("tempDeck" + UUID.randomUUID().toString(), ".dck");
                tmp.createNewFile();
                deck.setName("Generated-Deck-" + UUID.randomUUID());
                Sets.saveDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
                //JOptionPane.showMessageDialog(null, "Deck has been generated.");
                return tmp.getAbsolutePath();
            } catch (Exception e) {
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
        selectedColors = selectedColors != null ? selectedColors.toUpperCase() : getRandomColors("X");

        String format = (String) cbSets.getSelectedItem();
        List<String> setsToUse = ConstructedFormats.getSetsByFormat(format);
        if (setsToUse.isEmpty()) {
            // use all
            setsToUse = ExpansionRepository.instance.getSetCodes();
        }

        int deckSize = Integer.parseInt(cbDeckSize.getSelectedItem().toString());
        if (deckSize < 40) {
            deckSize = 40;
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

        // System.out.println("deck generator card pool: spells=" + spellCardPool.size() + ", lands=" + landCardPool.size());

        final List<String> setsToUseFinal = setsToUse;

        deck = DeckBuilder.buildDeck(spellCardPool, allowedColors, setsToUseFinal, landCardPool, deckSize, new RateCallback() {
            @Override
            public int rateCard(Card card) {
                return 6;
            }

            @Override
            public Card getBestBasicLand(ColoredManaSymbol color, List<String> setsToUse) {
                return DeckGenerator.getBestBasicLand(color, setsToUseFinal);
            }
        });
    }

    private static String getRandomColors(String _selectedColors) {
        Random random = new Random();
        List<Character> availableColors = new ArrayList();
        availableColors.add('R');
        availableColors.add('G');
        availableColors.add('B');
        availableColors.add('U');
        availableColors.add('W');

        StringBuilder generatedColors = new StringBuilder();
        int randomColors = 0;
        for (int i = 0; i < _selectedColors.length(); i++) {
            char currentColor = _selectedColors.charAt(i);
            if (currentColor != 'X') {
                generatedColors.append(currentColor);
                availableColors.remove(new Character(currentColor));
            } else {
                randomColors++;
            }
        }

        for (int i = 0; i < randomColors && !availableColors.isEmpty(); i++) {
            int index = random.nextInt(availableColors.size());
            generatedColors.append(availableColors.remove(index));
        }

        return generatedColors.toString();
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

        CardCriteria spellCriteria = new CardCriteria();
        spellCriteria.setCodes(setsToUse.toArray(new String[0]));
        spellCriteria.notTypes(CardType.LAND);

        List<CardInfo> cardPool = CardRepository.instance.findCards(spellCriteria);
        int cardPoolCount = cardPool.size();
        Random random = new Random();
        if (cardPoolCount > 0) {
            int tries = 0;
            int count = 0;
            while (count < cardsCount) {
                Card card = cardPool.get(random.nextInt(cardPoolCount)).getMockCard();
                if (cardFitsChosenColors(card, allowedColors)) {
                    spellCardPool.add(card);
                    count++;
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

        CardCriteria landCriteria = new CardCriteria();
        landCriteria.setCodes(setsToUse.toArray(new String[0]));
        landCriteria.types(CardType.LAND);
        landCriteria.notSupertypes("Basic");
        List<CardInfo> landCards = CardRepository.instance.findCards(landCriteria);

        int allCount = landCards.size();
        Random random = new Random();
        if (allCount > 0) {
            int tries = 0;
            int count = 0;
            while (count < landsCount) {
                Card card = landCards.get(random.nextInt(allCount)).getMockCard();
                if (cardCardProduceChosenColors(card, allowedColors)) {
                    nonBasicLandCardPool.add(card);
                    count++;
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
    private static Card getBestBasicLand(ColoredManaSymbol color, List<String> setsToUse) {
        String cardName = "";
        switch(color) {
            case G:
                cardName = "Forest";
                break;
            case W:
                cardName = "Plains";
                break;
            case R:
                cardName = "Mountain";
                break;
            case B:
                cardName = "Swamp";
                break;
            case U:
                cardName = "Island";
                break;
        }

        List<String> landSets = new LinkedList<String>();

        // decide from which sets basic lands are taken from
        for (String setCode :setsToUse) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
            if (expansionInfo.hasBasicLands()) {
                landSets.add(expansionInfo.getCode());
            }
        }

        // if sets have no basic land, take land from block
        if (landSets.isEmpty()) {
            for (String setCode :setsToUse) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                ExpansionInfo [] blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                for (ExpansionInfo blockSet: blockSets) {
                    if (blockSet.hasBasicLands()) {
                        landSets.add(blockSet.getCode());
                    }
                }
            }
        }
        // if still no set with lands found, take one by random
        if (landSets.isEmpty()) {
            // if sets have no basic lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            Random generator = new Random();
            List<ExpansionInfo> basicLandSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate();
            if (basicLandSets.size() > 0) {
                landSets.add(basicLandSets.get(generator.nextInt(basicLandSets.size())).getCode());
            }
        }

        if (landSets.isEmpty()) {
            throw new IllegalArgumentException("No set with basic land was found");
        }

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.rarities(Rarity.LAND).name(cardName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        if (cards.isEmpty() && !setsToUse.isEmpty()) {
            cards = CardRepository.instance.findCards(cardName);
        }

        int randomInt = new Random().nextInt(cards.size());
        return cards.get(randomInt).getMockCard();

    }

    protected static boolean isColoredMana(String symbol) {
        return symbol.equals("W") || symbol.equals("G") || symbol.equals("U") || symbol.equals("B") || symbol.equals("R") || symbol.contains("/");
    }
}
