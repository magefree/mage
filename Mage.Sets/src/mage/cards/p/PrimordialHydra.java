
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class PrimordialHydra extends CardImpl {

    private static final String staticText = "{this} has trample as long as it has ten or more +1/+1 counters on it";

    public PrimordialHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Primordial Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // At the beginning of your upkeep, double the number of +1/+1 counters on Primordial Hydra.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PrimordialHydraDoubleEffect(), TargetController.YOU, false));

        // Primordial Hydra has trample as long as it has ten or more +1/+1 counters on it.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), new SourceHasCounterCondition(CounterType.P1P1, 10, Integer.MAX_VALUE), staticText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public PrimordialHydra(final PrimordialHydra card) {
        super(card);
    }

    @Override
    public PrimordialHydra copy() {
        return new PrimordialHydra(this);
    }
}

class PrimordialHydraDoubleEffect extends OneShotEffect {

    PrimordialHydraDoubleEffect() {
        super(Outcome.BoostCreature);
        staticText = "double the number of +1/+1 counters on {this}";
    }

    PrimordialHydraDoubleEffect(final PrimordialHydraDoubleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PrimordialHydraDoubleEffect copy() {
        return new PrimordialHydraDoubleEffect(this);
    }
}
