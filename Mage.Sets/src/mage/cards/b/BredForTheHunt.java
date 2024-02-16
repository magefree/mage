
package mage.cards.b;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;



public final class BredForTheHunt extends CardImpl {

    public BredForTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{U}");


        // Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, you may draw a card.
        this.addAbility(new BredForTheHuntTriggeredAbility());
    }

    private BredForTheHunt(final BredForTheHunt card) {
        super(card);
    }

    @Override
    public BredForTheHunt copy() {
        return new BredForTheHunt(this);
    }
}

class BredForTheHuntTriggeredAbility extends TriggeredAbilityImpl {

    public BredForTheHuntTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.optional = true;
    }

    private BredForTheHuntTriggeredAbility(final BredForTheHuntTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BredForTheHuntTriggeredAbility copy() {
        return new BredForTheHuntTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(getControllerId()) && creature.getCounters(game).getCount(CounterType.P1P1) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, you may draw a card.";
    }

}
