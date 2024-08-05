

package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * FAQ 2013/01/11
 * <p>
 * 702.99. Extort
 * <p>
 * 702.99a Extort is a triggered ability. "Extort" means "Whenever you cast a spell,
 * you may pay White or Black Mana. If you do, each opponent loses 1 life and you gain
 * life equal to the total life lost this way."
 * <p>
 * 702.99b If a permanent has multiple instances of extort, each triggers separately.
 *
 * @author LevelX2
 */
public class ExtortAbility extends TriggeredAbilityImpl {

    public ExtortAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new LoseLifeOpponentsYouGainLifeLostEffect(1),
                new ManaCostsImpl<>("{W/B}"),
                "Pay {W/B} to Extort?"));
    }

    protected ExtortAbility(final ExtortAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Extort <i>(Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)</i>";
    }

    @Override
    public ExtortAbility copy() {
        return new ExtortAbility(this);
    }
}
