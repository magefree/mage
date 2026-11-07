package mage.game.draft;

import mage.cards.Card;

import java.util.List;

/**
 * Draft: source of suggested picks for human players (example: card ratings, a third party service or a LLM model)
 * <p>
 * All suggestions of all sources go to the player (each source can add any amount of cards), the first one of the
 * first source is used by autopick on pick timeout (if the player didn't mark a card)
 * <p>
 * Warning, it runs in a separated thread with a time limit (a late answer is ignored), so it must be thread safe;
 * it gets own copies of the cards, so it can't change the draft
 * <p>
 * WARNING, must be fast and runs under 1 second (if you need more time then increase both pick timeout and suggest timeout)
 * - DraftOptions
 * - DraftPickSuggesters.SUGGEST_TIMEOUT_MS
 * 
 * @author JayDi85
 */
public interface DraftPickSuggester {

    /**
     * Short name for GUI (example: "XMage ratings")
     */
    String getName();

    /**
     * @param booster current booster
     * @param picks   all picked cards of the player
     * @return suggested cards from the booster, best first (can be empty)
     */
    List<DraftPickSuggest> suggest(List<Card> booster, List<Card> picks);
}
