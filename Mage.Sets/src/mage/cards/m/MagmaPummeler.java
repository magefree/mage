package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
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
import mage.game.permanent.Permanent;
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

        // If damage would be dealt to Magma Pummeler while it has a +1/+1 counter on it, prevent that damage and remove that many +1/+1 counters from it.
        // When one or more counters are removed from Magma Pummeler this way, it deals that much damage to any target.
        this.addAbility(new SimpleStaticAbility(new MagmaPummelerEffect()));
    }

    private MagmaPummeler(final MagmaPummeler card) {
        super(card);
    }

    @Override
    public MagmaPummeler copy() {
        return new MagmaPummeler(this);
    }
}

class MagmaPummelerEffect extends PreventDamageAndRemoveCountersEffect {

    public MagmaPummelerEffect() {
        super(true, true, true);
        staticText += ". When one or more counters are removed from {this} this way, it deals that much damage to any target";
    }

    private MagmaPummelerEffect(final MagmaPummelerEffect effect) {
        super(effect);
    }

    @Override
    public MagmaPummelerEffect copy() {
        return new MagmaPummelerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        int beforeCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
        super.replaceEvent(event, source, game);
        int countersRemoved = beforeCounters - permanent.getCounters(game).getCount(CounterType.P1P1);
        if (countersRemoved > 0) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new DamageTargetEffect(countersRemoved), false,
                    "{this} deals that much damage to any target"
            );
            ability.addTarget(new TargetAnyTarget());
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return false;
    }

}
