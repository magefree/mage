package mage.server.draft;

import mage.cards.Card;
import mage.cards.RateCard;
import mage.constants.ColoredManaSymbol;
import mage.game.draft.DraftPickSuggest;
import mage.game.draft.DraftPickSuggester;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Draft: suggested picks by card ratings, the same logic as draft bots use (deck colors by own picks, ratings from
 * resources/ratings files or rarity based scores for cards without ratings)
 *
 * @author JayDi85
 */
public class RatingDraftPickSuggester implements DraftPickSuggester {

    private static final int MAX_SUGGESTS = 3;

    @Override
    public String getName() {
        return "XMage ratings";
    }

    @Override
    public List<DraftPickSuggest> suggest(List<Card> booster, List<Card> picks) {
        List<ColoredManaSymbol> colors = RateCard.chooseDeckColors(picks);
        Map<UUID, Integer> scores = new HashMap<>();
        booster.forEach(card -> scores.put(card.getId(), RateCard.rateCard(card, colors)));

        // same score - booster's order (stable sort)
        List<Card> sorted = new ArrayList<>(booster);
        sorted.sort((c1, c2) -> Integer.compare(scores.get(c2.getId()), scores.get(c1.getId())));

        String colorsInfo = colors.isEmpty()
                ? "deck colors not chosen yet"
                : "deck colors " + colors.stream().map(ColoredManaSymbol::toString).collect(Collectors.joining());
        List<DraftPickSuggest> res = new ArrayList<>();
        for (Card card : sorted.subList(0, Math.min(MAX_SUGGESTS, sorted.size()))) {
            String source = RateCard.getRatingSource(card.getName(), card.getExpansionSetCode());
            String info = source != null
                    ? "rating " + RateCard.getBaseCardScore(card) + " of 100 by " + source
                    : "no ratings, score " + RateCard.rateCard(card, Collections.emptyList()) + " by rarity, type and removal";
            if (RateCard.isOffColorCard(card, colors)) {
                info += ", off-color";
            }
            res.add(new DraftPickSuggest(card.getId(), info + ", " + colorsInfo));
        }
        return res;
    }
}
