package mage.filter;

import mage.cards.Card;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Works with cards only. For objects like commanders you must override your canTarget method.
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCard extends FilterObject<Card> {

    private static final long serialVersionUID = 1L;
    protected List<ObjectSourcePlayerPredicate<ObjectSourcePlayer<Card>>> extraPredicates = new ArrayList<>();

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
        return match(card, null, playerId, game);
    }

    public boolean match(Card card, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(card, game)) {
            return false;
        }
        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer<Card>(card, sourceId, playerId), game);
    }

    public final void add(ObjectSourcePlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }

        checkPredicateIsSuitableForCardFilter(predicate);

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

    public static void checkPredicateIsSuitableForCardFilter(Predicate predicate) {
        // card filter can't contain controller predicate (only permanents on battlefield have controller)
        List<Predicate> list = new ArrayList<>();
        Predicates.collectAllComponents(predicate, list);
        if (list.stream().anyMatch(p -> p instanceof TargetController.ControllerPredicate)) {
            throw new IllegalArgumentException("Card filter doesn't support controller predicate");
        }
    }

    public FilterCard withMessage(String message) {
        this.setMessage(message);
        return this;
    }
}
