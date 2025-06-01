package mage.filter.predicate.card;

import mage.cards.Card;
import mage.cards.CardWithSpellOption;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.cards.mock.MockCard;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Special predicate to search cards in deck editor
 *
 * @author North, JayDi85
 */
public class CardTextPredicate implements Predicate<Card> {

    private final String text;
    private final boolean inNames;
    private final boolean inTypes;
    private final boolean inRules;
    private final boolean isUnique;
    private HashMap<String, Boolean> seenCards;
    private final Pattern pattern;
    private final Matcher matcher;
    private final List<String> textTokens;

    public CardTextPredicate(String text, boolean inNames, boolean inTypes, boolean inRules, boolean isUnique) {
        this.text = text;
        this.inNames = inNames;
        this.inTypes = inTypes;
        this.inRules = inRules;
        this.isUnique = isUnique;
        this.seenCards = new HashMap<>();

        // regexp to find texts inside "xxx" like
        // "123 345" → ["123", "345"]
        // "123 345 678" → ["123", "345", "678"]
        // "123 "345 678"" → ["123", "345 678"]
        this.textTokens = new ArrayList<>();
        this.pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
        this.matcher = this.pattern.matcher(text.toLowerCase(Locale.ENGLISH));
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // inside "xxx"
                this.textTokens.add(matcher.group(1));
            } else {
                // normal xxx
                this.textTokens.add(matcher.group(2));
            }
        }
    }

    @Override
    public boolean apply(Card input, Game game) {
        if (this.textTokens.isEmpty()) {
            return saveAndReturnUniqueFind(input);
        }

        // name: need all tokens
        if (inNames) {
            String fullName = input.getName();
            if (input instanceof MockCard) {
                fullName = ((MockCard) input).getFullName(true);
            } else if (input instanceof ModalDoubleFacedCard) {
                fullName = input.getName() + MockCard.MODAL_DOUBLE_FACES_NAME_SEPARATOR + ((ModalDoubleFacedCard) input).getRightHalfCard().getName();
            } else if (input instanceof CardWithSpellOption) {
                fullName = input.getName() + MockCard.CARD_WITH_SPELL_OPTION_NAME_SEPARATOR + ((CardWithSpellOption) input).getSpellCard().getName();
            }
            if (textHasTokens(fullName, true)) {
                return saveAndReturnUniqueFind(input);
            }
        }

        // rules: need all tokens
        if (inRules) {
            List<String> fullRules = new ArrayList<>(input.getRules(game));
            if (input instanceof SplitCard) {
                fullRules.addAll(((SplitCard) input).getLeftHalfCard().getRules(game));
                fullRules.addAll(((SplitCard) input).getRightHalfCard().getRules(game));
            }
            if (input instanceof ModalDoubleFacedCard) {
                fullRules.addAll(((ModalDoubleFacedCard) input).getLeftHalfCard().getRules(game));
                fullRules.addAll(((ModalDoubleFacedCard) input).getRightHalfCard().getRules(game));
            }
            if (input instanceof CardWithSpellOption) {
                fullRules.addAll(((CardWithSpellOption) input).getSpellCard().getRules(game));
            }
            if (textHasTokens(String.join("@", fullRules), true)) {
                return saveAndReturnUniqueFind(input);
            }
        }

        // types: need all tokens
        if (inTypes) {
            List<String> fullTypes = new ArrayList<>();
            fullTypes.addAll(input.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            fullTypes.addAll(input.getSuperType(game).stream().map(SuperType::toString).collect(Collectors.toList()));
            if (textHasTokens(String.join("@", fullTypes), true)) {
                return saveAndReturnUniqueFind(input);
            }
        }

        // not found
        return false;
    }

    private boolean saveAndReturnUniqueFind(Card card) {
        if (isUnique) {
            boolean found = !seenCards.containsKey(card.getName());
            seenCards.put(card.getName(), true);
            return found;
        } else {
            return true;
        }
    }

    private boolean textHasTokens(String text, boolean needAllTokens) {
        String searchingText = text + "@" + text.replace(", ", " ");
        searchingText += "@" + searchingText.toLowerCase(Locale.ENGLISH);

        boolean found = false;
        for (String token : this.textTokens) {
            if (searchingText.contains(token)) {
                found = true;
                if (!needAllTokens) {
                    break;
                }
            } else {
                if (needAllTokens) {
                    found = false;
                    break;
                }
            }
        }

        return found;
    }

    @Override
    public String toString() {
        return "CardText(" + text + ')';
    }
}
