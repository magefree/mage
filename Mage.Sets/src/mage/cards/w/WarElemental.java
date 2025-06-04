package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.common.BloodthirstWatcher;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class WarElemental extends CardImpl {

    public WarElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When War Elemental enters the battlefield, sacrifice it unless an opponent was dealt damage this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(WarElementalCondition.instance)));

        // Whenever an opponent is dealt damage, put that many +1/+1 counters on War Elemental.
        this.addAbility(new WarElementalTriggeredAbility());

    }

    private WarElemental(final WarElemental card) {
        super(card);
    }

    @Override
    public WarElemental copy() {
        return new WarElemental(this);
    }
}

class WarElementalTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    WarElementalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY), false);
        setTriggerPhrase("Whenever an opponent is dealt damage, ");
    }

    private WarElementalTriggeredAbility(final WarElementalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarElementalTriggeredAbility copy() {
        return new WarElementalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant
        if (game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            this.getAllEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

}

enum WarElementalCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        BloodthirstWatcher watcher = game.getState().getWatcher(BloodthirstWatcher.class, source.getControllerId());
        return watcher != null &&  watcher.conditionMet();
    }

    @Override
    public String toString() {
        return "an opponent was dealt damage this turn";
    }
}
