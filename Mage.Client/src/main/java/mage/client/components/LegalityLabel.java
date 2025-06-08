package mage.client.components;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorError;
import org.unbescape.html.HtmlEscape;
import org.unbescape.html.HtmlEscapeLevel;
import org.unbescape.html.HtmlEscapeType;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Elandril, JayDi85
 */
public class LegalityLabel extends JLabel {

    protected static final Color COLOR_UNKNOWN = new Color(174, 174, 174);
    protected static final Color COLOR_LEGAL = new Color(117, 152, 110);
    protected static final Color COLOR_PARTLY_LEGAL = new Color(191, 176, 80);
    protected static final Color COLOR_NOT_LEGAL = new Color(191, 84, 74);
    protected static final Color COLOR_TEXT = new Color(255, 255, 255);
    protected static final Dimension DIM_MINIMUM = new Dimension(75, 25);
    protected static final Dimension DIM_MAXIMUM = new Dimension(150, 75);
    protected static final Dimension DIM_PREFERRED_1_OF_3 = new Dimension(75, 25);
    protected static final Dimension DIM_PREFERRED_2_OF_3 = new Dimension(DIM_PREFERRED_1_OF_3.width * 2 + 5, 25);
    protected static final Dimension DIM_PREFERRED_3_OF_3 = new Dimension(DIM_PREFERRED_1_OF_3.width * 3 + 5 * 2, 25);
    protected static final Dimension DIM_PREFERRED_1_OF_5 = new Dimension((DIM_PREFERRED_3_OF_3.width - 5 * 4) / 5, 25);

    protected static final int TOOLTIP_TABLE_WIDTH = 400; // size of the label's tooltip
    protected static final int TOOLTIP_MAX_ERRORS = 20; // max errors to show in tooltip

    protected Deck currentDeck;
    protected String errorMessage;
    protected DeckValidator validator;

    /**
     * Creates a <code>LegalityLabel</code> instance with the specified text
     * and the given DeckValidator.
     *
     * @param text      The text to be displayed by the label.
     * @param validator The DeckValidator to check against.
     */
    public LegalityLabel(String text, DeckValidator validator) {
        super(text);
        this.validator = validator;

        setBackground(COLOR_UNKNOWN);
        setForeground(COLOR_TEXT);
        setHorizontalAlignment(SwingConstants.CENTER);
        setMinimumSize(DIM_MINIMUM);
        setMaximumSize(DIM_MAXIMUM);
        setName(text); // NOI18N
        setOpaque(true);
        setPreferredSize(DIM_PREFERRED_1_OF_3);
    }

    /**
     * Creates a <code>LegalityLabel</code> instance with the given DeckValidator and uses its
     * shortName as the text.
     *
     * @param validator The DeckValidator to check against.
     */
    public LegalityLabel(DeckValidator validator) {
        this(validator.getShortName(), validator);
    }

    /**
     * Creates a <code>LegalityLabel</code> instance with no DeckValidator or text.
     * This is used by the Netbeans GUI Editor.
     */
    public LegalityLabel() {
        super();

        setBackground(COLOR_UNKNOWN);
        setForeground(COLOR_TEXT);
        setHorizontalAlignment(SwingConstants.CENTER);
        setMinimumSize(DIM_MINIMUM);
        setMaximumSize(DIM_MAXIMUM);
        setOpaque(true);
        setPreferredSize(DIM_PREFERRED_1_OF_3);
    }

    /**
     * Creates hide button to close legality panel (must be same size as label)
     */
    public static JButton createHideButton() {
        JButton button = new JButton("Hide");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMinimumSize(DIM_MINIMUM);
        button.setMaximumSize(DIM_MAXIMUM);
        button.setPreferredSize(DIM_PREFERRED_1_OF_3);
        return button;
    }

    protected String escapeHtml(String string) {
        return HtmlEscape.escapeHtml(string, HtmlEscapeType.HTML4_NAMED_REFERENCES_DEFAULT_TO_HEXA, HtmlEscapeLevel.LEVEL_0_ONLY_MARKUP_SIGNIFICANT_EXCEPT_APOS);
    }

    protected String formatInvalidTooltip(java.util.List<DeckValidatorError> sortedErrorsList) {
        return sortedErrorsList.stream()
                .reduce("<html><body>"
                                + "<p>Deck is <span style='color:#BF544A;font-weight:bold;'>INVALID</span></p>"
                                + "<u>The following problems have been found (click to select problem cards):</u>"
                                + "<br>"
                                + "<table style=\"table-layout: fixed; width: " + TOOLTIP_TABLE_WIDTH + "px\">",
                        (str, error) -> String.format("%s<tr><td style=\"word-wrap: break-word\"><b>%s</b></td><td style=\"word-wrap: break-word\">%s</td></tr>", str, escapeHtml(error.getGroup()), escapeHtml(error.getMessage())), String::concat)
                + "</table>"
                + "</body></html>";
    }

    protected String formatPartlyValidTooltip(java.util.List<DeckValidatorError> sortedErrorsList) {
        return sortedErrorsList.stream()
                .reduce("<html><body>"
                                + "<p>Deck is <span style='color:#b8860b;font-weight:bold;'>PARTLY VALID</span></p>"
                                + "<u>The following problems have been found (click to select problem cards):</u>"
                                + "<br>"
                                + "<table style=\"table-layout: fixed; width: " + TOOLTIP_TABLE_WIDTH + "px\">",
                        (str, error) -> String.format("%s<tr><td style=\"word-wrap: break-word\"><b>%s</b></td><td style=\"word-wrap: break-word\">%s</td></tr>", str, escapeHtml(error.getGroup()), escapeHtml(error.getMessage())), String::concat)
                + "</table>"
                + "</body></html>";
    }

    private String appendErrorMessage(String string) {
        if (errorMessage.isEmpty())
            return string;
        if (string.contains("<html>")) {
            return string.replaceFirst("((</body>)?</html>)", String.format("<br><br>The following errors occurred while loading the deck:<br>%s$1", escapeHtml(errorMessage)));
        }
        return string.concat("\n\nThe following errors occurred while loading the deck:\n" + errorMessage);
    }

    public void showState(Color color) {
        setBackground(color);
    }

    public void showState(Color color, String tooltip, boolean useErrors) {
        setBackground(color);
        if (useErrors) {
            setToolTipText(appendErrorMessage(tooltip));
        } else {
            setToolTipText(tooltip);
        }
    }

    public void showStateInfo(String tooltip) {
        showState(COLOR_LEGAL, tooltip, false);
    }

    public void showStateUnknown(String tooltip) {
        showState(COLOR_UNKNOWN, tooltip, true);
    }

    public void showStateLegal(String tooltip) {
        showState(COLOR_LEGAL, tooltip, true);
    }

    public void showStatePartlyLegal(String tooltip) {
        showState(COLOR_PARTLY_LEGAL, tooltip, true);
    }

    public void showStateNotLegal(String tooltip) {
        showState(COLOR_NOT_LEGAL, tooltip, true);
    }

    public void validateDeck(Deck deck) {
        errorMessage = "";
        currentDeck = deck;
        if (deck == null) {
            showStateUnknown("<html><body><b>No deck loaded!</b></body></html>");
            return;
        }
        if (validator == null) {
            showStateUnknown("<html><body><b>No deck type selected!</b></body></html>");
            return;
        }
        try {
            if (validator.validate(deck)) {
                showStateLegal("<html><body>Deck is <span style='color:green;font-weight:bold;'>VALID</span></body></html>");
            } else if (validator.isPartlyValid()) {
                showStatePartlyLegal(formatPartlyValidTooltip(validator.getErrorsListSorted(TOOLTIP_MAX_ERRORS)));
            } else {
                showStateNotLegal(formatInvalidTooltip(validator.getErrorsListSorted(TOOLTIP_MAX_ERRORS)));
            }
        } catch (Exception e) {
            showStateUnknown(String.format("<html><body><b>Deck could not be validated!</b><br>The following error occurred while validating this deck:<br>%s</body></html>", escapeHtml(e.getMessage())));
        }
    }

    public java.util.List<String> selectCards() {
        if (this.validator == null) {
            return Collections.emptyList();
        }
        return this.validator.getErrorsList().stream()
                .map(DeckValidatorError::getCardName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
