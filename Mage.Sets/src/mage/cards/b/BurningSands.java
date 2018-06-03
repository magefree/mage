
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class BurningSands extends CardImpl {

    private final UUID originalId;

    public BurningSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Whenever a creature dies, that creature's controller sacrifices a land.
        Ability ability = new DiesCreatureTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that creature's controller"), false, false, true);
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            UUID creatureId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            Permanent creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD);
            if (creature != null) {
                ability.getEffects().get(0).setTargetPointer(new FixedTarget(creature.getControllerId()));
            }
        }
    }

    public BurningSands(final BurningSands card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public BurningSands copy() {
        return new BurningSands(this);
    }
}
