
package mage.game.command.emblems;

import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public final class AurraSingBaneOfJediEmblem extends Emblem {

    //  Whenever a nontoken creature you control leaves the battlefied, discard a card.
    public AurraSingBaneOfJediEmblem() {
        this.setName("Emblem Aurra Sing, Bane of Jedi");
        getAbilities().add(new LeavesBattlefieldAllTriggeredAbility(Zone.COMMAND, new DiscardControllerEffect(1), StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false));
    }
}
