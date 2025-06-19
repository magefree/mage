package mage.client.components;

import mage.cards.decks.Deck;
import mage.client.util.GUISizeHelper;
import mage.deck.Commander;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inject power level info inside validation panel
 *
 * @author JayDi85
 */
public class EdhPowerLevelLegalityLabel extends LegalityLabel {

    private final Commander commanderDeckType = new Commander();
    private final List<String> foundPowerCards = new ArrayList<>();

    public EdhPowerLevelLegalityLabel() {
        super("EDH Power Level: ?", null);
        setPreferredSize(DIM_PREFERRED_3_OF_3);
    }

    @Override
    public List<String> selectCards() {
        // choose cards with power level
        return this.foundPowerCards;
    }

    @Override
    public void validateDeck(Deck deck) {
        // find and save power level and card hints

        List<String> foundInfo = new ArrayList<>();
        int level = this.commanderDeckType.getEdhPowerLevel(deck, foundPowerCards, foundInfo);
        this.setText(String.format("EDH Power Level: %d", level));

        // sort by score "+5 from xxx"
        Pattern pattern = Pattern.compile("\\+(\\d+)");
        foundInfo.sort((o1, o2) -> {
            Matcher matcher = pattern.matcher(o1);
            int score1 = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
            matcher = pattern.matcher(o2);
            int score2 = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
            if (score1 != score2) {
                return Integer.compare(score2, score1);
            }
            return o1.compareTo(o2);
        });

        showStateInfo(formatCardsInfoTooltip(level, foundInfo));
    }

    private String formatCardsInfoTooltip(int level, List<String> foundInfo) {
        // use 60% font for better and compatible list
        int infoFontSize = Math.round(GUISizeHelper.cardTooltipFont.getSize() * 0.6f);
        int maxLimit = 25;
        String extraInfo = this.foundPowerCards.size() <= maxLimit ? "" : String.format("<li style=\"margin-bottom: 2px;\">and %d more cards</li>", this.foundPowerCards.size() - maxLimit);
        return foundInfo.stream()
                .limit(maxLimit)
                .reduce("<html><body>"
                                + "<p>EDH Power Level: <span style='color:#b8860b;font-weight:bold;'>" + level + "</span></p>"
                                + "<br>"
                                + "<u>Found <span style='font-weight:bold;'>" + this.foundPowerCards.size() + "</span> cards with power levels (click to select it)</u>"
                                + "<br>"
                                + "<ul style=\"font-size: " + infoFontSize + "px; width: " + TOOLTIP_TABLE_WIDTH + "px; padding-left: 10px; margin: 0;\">",
                        (str, info) -> str + String.format("<li style=\"margin-bottom: 2px;\">%s</li>", info), String::concat)
                + extraInfo
                + "</ul>"
                + "</body></html>";
    }
}
