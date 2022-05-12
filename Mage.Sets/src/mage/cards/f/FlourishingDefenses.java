package mage.cards.f;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FlourishingDefenses extends CardImpl {

    public FlourishingDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a -1/-1 counter is put on a creature, you may create a 1/1 green Elf Warrior creature token.
        this.addAbility(new FlourishingDefensesTriggeredAbility());

    }

    private FlourishingDefenses(final FlourishingDefenses card) {
        super(card);
    }

    @Override
    public FlourishingDefenses copy() {
        return new FlourishingDefenses(this);
    }
}

class FlourishingDefensesTriggeredAbility extends TriggeredAbilityImpl {

    FlourishingDefensesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ElfWarriorToken()), true);
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
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.M1M1.getName())) {
            Permanent permanent = Optional
                    .ofNullable(game.getPermanentOrLKIBattlefield(event.getTargetId()))
                    .orElse(game.getPermanentEntering(event.getTargetId()));

            return permanent != null && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a -1/-1 counter is put on a creature, you may create a 1/1 green Elf Warrior creature token.";
    }
}
