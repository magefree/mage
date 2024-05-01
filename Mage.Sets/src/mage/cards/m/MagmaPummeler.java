package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedCounterRemovedValue;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaPummeler extends CardImpl {

    public MagmaPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Magma Pummeler enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // If damage would be dealt to Magma Pummeler while it has a +1/+1 counter on it, prevent that damage and remove that many +1/+1 counters from it. When one or more counters are removed from Magma Pummeler this way, it deals that much damage to any target.
        Ability ability = new SimpleStaticAbility(new MagmaPummelerPreventionEffect());
        ability.addSubAbility(new MagmaPummelerTriggeredAbility());
        this.addAbility(ability, PreventDamageAndRemoveCountersEffect.createWatcher());
    }

    private MagmaPummeler(final MagmaPummeler card) {
        super(card);
    }

    @Override
    public MagmaPummeler copy() {
        return new MagmaPummeler(this);
    }
}

class MagmaPummelerPreventionEffect extends PreventDamageAndRemoveCountersEffect {

    MagmaPummelerPreventionEffect() {
        super(true, true, true);
    }

    private MagmaPummelerPreventionEffect(final MagmaPummelerPreventionEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageAndRemoveCountersEffect copy() {
        return new MagmaPummelerPreventionEffect(this);
    }

    @Override
    protected void additionalEffect(GameEvent event, Ability source, Game game, int amountRemovedInTotal, int amountRemovedThisTime) {
        super.additionalEffect(event, source, game, amountRemovedInTotal, amountRemovedThisTime);
        // This is not the most elegant, but ensure we only trigger at most once per DAMAGED_BATCH_COULD_HAVE_FIRED,
        // with the correct number of counters removed (the last time we stored value it will be the total)
        source.getSubAbilities()
                .stream()
                .filter(MagmaPummelerTriggeredAbility.class::isInstance)
                .forEach(ability -> ((MagmaPummelerTriggeredAbility) ability).setStoredValue(amountRemovedInTotal));
    }
}

/**
 * Having a trigger from a replacement effect is a weird setup for a trigger.
 * We manage with the DAMAGED_BATCH_COULD_HAVE_FIRED special event, and the stored
 * amount in storedValue, that gets updated by the Replacement Effect with the correct
 * total on checkTrigger.
 */
class MagmaPummelerTriggeredAbility extends TriggeredAbilityImpl {

    private int storedValue;

    void setStoredValue(int storedValue) {
        this.storedValue = storedValue;
    }

    MagmaPummelerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedCounterRemovedValue.MUCH));
        setTriggerPhrase("When one or more counters are removed from {this} this way, ");
        addTarget(new TargetAnyTarget());
        storedValue = 0;
    }

    private MagmaPummelerTriggeredAbility(final MagmaPummelerTriggeredAbility ability) {
        super(ability);
        this.storedValue = ability.storedValue;
    }

    @Override
    public MagmaPummelerTriggeredAbility copy() {
        return new MagmaPummelerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_COULD_HAVE_FIRED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (storedValue <= 0) {
            return false;
        }
        getEffects().setValue(SavedCounterRemovedValue.VALUE_KEY, storedValue);
        // We do not want to loop trigger. So clearing the saved amount.
        setStoredValue(0);
        return true;
    }
}