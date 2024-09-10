package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterDefender extends FilterPermanentOrPlayer {

    public FilterDefender(Set<UUID> defenders) {
        super("player, planeswalker, or battle to attack");
        this.permanentFilter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
        this.permanentFilter.add(Predicates.or(
                defenders
                        .stream()
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toList())
        ));
        this.playerFilter.add(Predicates.or(
                defenders
                        .stream()
                        .map(PlayerIdPredicate::new)
                        .collect(Collectors.toList())
        ));
    }

    private FilterDefender(final FilterDefender filter) {
        super(filter);
    }

    @Override
    public FilterDefender copy() {
        return new FilterDefender(this);
    }
}
