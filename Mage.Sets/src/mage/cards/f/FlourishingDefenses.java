
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author jeffwadsworth
 */
public final class FlourishingDefenses extends CardImpl {

    public FlourishingDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");

        // Whenever a -1/-1 counter is put on a creature, you may create a 1/1 green Elf Warrior creature token.
        this.addAbility(new FlourishingDefensesTriggeredAbility());

    }

    public FlourishingDefenses(final FlourishingDefenses card) {
        super(card);
    }

    @Override
    public FlourishingDefenses copy() {
        return new FlourishingDefenses(this);
    }
}

class FlourishingDefensesTriggeredAbility extends TriggeredAbilityImpl {

    FlourishingDefensesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ElfToken()), true);
    }

    FlourishingDefensesTriggeredAbility(final FlourishingDefensesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlourishingDefensesTriggeredAbility copy() {
        return new FlourishingDefensesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.M1M1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a -1/-1 counter is put on a creature, you may create a 1/1 green Elf Warrior creature token.";
    }
}
