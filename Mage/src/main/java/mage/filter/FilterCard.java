package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.cards.Card;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCard extends FilterObject<Card> {

    private static final long serialVersionUID = 1L;
    protected List<ObjectPlayerPredicate<ObjectPlayer<Card>>> extraPredicates = new ArrayList<>();

    public FilterCard() {
        super("card");
    }

    public FilterCard(String name) {
        super(name);
    }

    public FilterCard(FilterCard filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    //20130711 708.6c
    /* If anything performs a comparison involving multiple characteristics or
     * values of one or more split cards in any zone other than the stack or
     * involving multiple characteristics or values of one or more fused split
     * spells, each characteristic or value is compared separately. If each of
     * the individual comparisons would return a “yes” answer, the whole
     * comparison returns a “yes” answer. The individual comparisons may involve
     * different halves of the same split card.
     */
    @Override
    public boolean match(Card card, Game game) {
        if (card == null) {
            return false;
        }
        return super.match(card, game);
    }

    public boolean match(Card card, UUID playerId, Game game) {
        if (!this.match(card, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectPlayer(card, playerId), game);
    }

    public boolean match(Card card, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(card, game)) {
            return false;
        }
        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(card, sourceId, playerId), game);
    }

    public final void add(ObjectPlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
    }

    public Set<Card> filter(Set<Card> cards, Game game) {
        return cards.stream().filter(card -> match(card, game)).collect(Collectors.toSet());

    }

    public boolean hasPredicates() {
        return !predicates.isEmpty();
    }

    @Override
    public FilterCard copy() {
        return new FilterCard(this);
    }
}
