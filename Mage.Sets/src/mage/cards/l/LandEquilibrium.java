
package mage.cards.l;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class LandEquilibrium extends CardImpl {

    public LandEquilibrium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // If an opponent who controls at least as many lands as you do would put a land onto the battlefield, that player instead puts that land onto the battlefield then sacrifices a land.
        this.addAbility(new LandEquilibriumAbility());
    }

    private LandEquilibrium(final LandEquilibrium card) {
        super(card);
    }

    @Override
    public LandEquilibrium copy() {
        return new LandEquilibrium(this);
    }
}

class LandEquilibriumAbility extends TriggeredAbilityImpl {

    public LandEquilibriumAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_LAND, 1, ""), false);
    }

    private LandEquilibriumAbility(final LandEquilibriumAbility ability) {
        super(ability);
    }

    @Override
    public LandEquilibriumAbility copy() {
        return new LandEquilibriumAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int numMyLands = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, this.getControllerId(), game);
        int theirLands = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, event.getPlayerId(), game);
        if (numMyLands < theirLands) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "If an opponent who controls at least as many lands as you do would put a land onto the battlefield, that player instead puts that land onto the battlefield then sacrifices a land";
    }
}
