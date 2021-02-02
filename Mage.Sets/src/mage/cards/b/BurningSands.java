
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BurningSands extends CardImpl {

    public BurningSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever a creature dies, that creature's controller sacrifices a land.
        Ability ability = new DiesCreatureTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_LAND, 1, "that creature's controller"
        ), false, false, true);
        ability.setTargetAdjuster(BurningSandsAdjuster.instance);
        this.addAbility(ability);
    }

    private BurningSands(final BurningSands card) {
        super(card);
    }

    @Override
    public BurningSands copy() {
        return new BurningSands(this);
    }
}

enum BurningSandsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID creatureId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature != null) {
            ability.getEffects().get(0).setTargetPointer(new FixedTarget(creature.getControllerId()));
        }
    }
}