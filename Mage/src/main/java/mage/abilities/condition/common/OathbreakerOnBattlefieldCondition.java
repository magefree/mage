package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * For Oathbreaker game mode
 *
 * @author JayDi85
 */
public class OathbreakerOnBattlefieldCondition implements Condition {

    private UUID playerId;
    private FilterControlledPermanent filter;

    public OathbreakerOnBattlefieldCondition(UUID playerId, List<UUID> oathbreakersToSearch) {
        this.playerId = playerId;
        this.filter = new FilterControlledPermanent("oathbreaker on battlefield");
        if (oathbreakersToSearch != null && !oathbreakersToSearch.isEmpty()) {
            // any commander on battlefield
            List<PermanentIdPredicate> idsList = new ArrayList<>();
            for (UUID id : oathbreakersToSearch) {
                idsList.add(new PermanentIdPredicate(id));
            }
            this.filter.add(Predicates.or(idsList));
        } else {
            // random id to disable condition
            this.filter.add(new PermanentIdPredicate(UUID.randomUUID()));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // source.getSourceId() is null for commander's effects
        int permanentsOnBattlefield = game.getBattlefield().count(this.filter, source.getSourceId(), playerId, game);
        return permanentsOnBattlefield > 0;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
