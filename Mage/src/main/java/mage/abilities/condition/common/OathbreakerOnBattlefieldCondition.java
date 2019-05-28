package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.filter.FilterMana;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.util.ManaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * For Oathbreaker game mode
 *
 * @author JayDi85
 */
public class OathbreakerOnBattlefieldCondition implements Condition {

    private UUID playerId;
    private FilterControlledPermanent filter;
    private String compatibleNames;

    public OathbreakerOnBattlefieldCondition(Game game, UUID playerId, UUID signatureSpellId, Set<UUID> oathbreakersToSearch) {
        this.playerId = playerId;
        this.filter = new FilterControlledPermanent("oathbreaker on battlefield");

        Card spell = game.getCard(signatureSpellId);
        FilterMana spellColors = spell != null ? spell.getColorIdentity() : null;

        // spell can be casted by any compatible oathbreakers
        List<PermanentIdPredicate> compatibleList = new ArrayList<>();
        List<String> compatibleNames = new ArrayList<>();
        if (oathbreakersToSearch != null && !oathbreakersToSearch.isEmpty()) {
            for (UUID id : oathbreakersToSearch) {
                Card commander = game.getCard(id);
                if (commander != null && ManaUtil.isColorIdentityCompatible(commander.getColorIdentity(), spellColors)) {
                    compatibleList.add(new PermanentIdPredicate(id));
                    compatibleNames.add(commander.getName());
                }
            }
        }
        this.compatibleNames = String.join("; ", compatibleNames);

        if (compatibleList.isEmpty()) {
            // random id to disable condition
            this.filter.add(new PermanentIdPredicate(UUID.randomUUID()));
        } else {
            // oathbreaker on battlefield
            this.filter.add(Predicates.or(compatibleList));
        }
    }

    public String getCompatibleNames() {
        return !this.compatibleNames.isEmpty() ? this.compatibleNames : "you haven't compatible oathbreaker";
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
