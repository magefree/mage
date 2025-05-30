package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedCounterRemovedValue;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801, Susucr
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

    // This is not standard for the codebase, but we need to trigger only once if multiple
    // source deal damage at the same time.
    // To achieve that, we store the delayedId's is created on first instance,
    // and its inner value gets modified if not triggered yet.
    private UUID reflexiveId;

    MagmaPummelerPreventionEffect() {
        super(true, true, true);
        staticText = "If damage would be dealt to {this} while it has a +1/+1 counter on it, "
                + "prevent that damage and remove that many +1/+1 counters from it. "
                + "When one or more counters are removed from {this} this way, it deals that much damage to any target.";
        this.reflexiveId = null;
    }

    private MagmaPummelerPreventionEffect(final MagmaPummelerPreventionEffect effect) {
        super(effect);
        this.reflexiveId = effect.reflexiveId;
    }

    @Override
    public PreventDamageAndRemoveCountersEffect copy() {
        return new MagmaPummelerPreventionEffect(this);
    }

    @Override
    protected void onDamagePrevented(GameEvent event, Ability source, Game game, int amountRemovedInTotal, int amountRemovedThisTime) {
        super.onDamagePrevented(event, source, game, amountRemovedInTotal, amountRemovedThisTime);

        if (amountRemovedInTotal == amountRemovedThisTime && amountRemovedInTotal > 0) {
            // First instance of damage prevention, we create a new reflexive ability.
            ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                    new DamageTargetEffect(SavedCounterRemovedValue.MUCH), false,
                    "When one or more counters are removed from {this} this way, it deals that much damage to any target."
            );
            reflexive.addTarget(new TargetAnyTarget());
            reflexiveId = game.fireReflexiveTriggeredAbility(reflexive, source, true);
        }
        if (reflexiveId != null) {
            // Set the amount of counters removed to the latest known info.
            DelayedTriggeredAbility reflexive = game.getState().getDelayed().get(reflexiveId).orElse(null);
            if (reflexive instanceof ReflexiveTriggeredAbility) {
                reflexive.getEffects().setValue(SavedCounterRemovedValue.VALUE_KEY, amountRemovedInTotal);
            } else {
                reflexiveId = null;
            }
        }
    }
}