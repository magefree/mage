package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfAnyNumberCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class IntrepidAdversary extends CardImpl {

    public IntrepidAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Intrepid Adversary enters the battlefield, you may pay {1}{W} any number of times.
        // When you pay this cost once or more times, put that many valor counters on Intrepid Adversary.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfAnyNumberCostPaid(
                new IntrepidAdversaryEffect(), new ManaCostsImpl<>("{1}{W}")
        )));

        // Creatures you control get +1/+1 for each valor counter on Intrepid Adversary.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                IntrepidAdversaryValue.instance, IntrepidAdversaryValue.instance, Duration.WhileOnBattlefield
        )));
    }

    private IntrepidAdversary(final IntrepidAdversary card) {
        super(card);
    }

    @Override
    public IntrepidAdversary copy() {
        return new IntrepidAdversary(this);
    }
}

class IntrepidAdversaryEffect extends OneShotEffect {

    public IntrepidAdversaryEffect() {
        super(Outcome.Benefit);
        staticText = "put that many valor counters on {this}";
    }

    private IntrepidAdversaryEffect(final IntrepidAdversaryEffect effect) {
        super(effect);
    }

    @Override
    public IntrepidAdversaryEffect copy() {
        return new IntrepidAdversaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer timesPaid = (Integer) getValue("timesPaid");
        if (timesPaid == null || timesPaid <= 0) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.VALOR.createInstance(timesPaid)),
                false, staticText
        );
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

enum IntrepidAdversaryValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return 0;
        }
        return permanent.getCounters(game).getCount(CounterType.VALOR);
    }

    @Override
    public IntrepidAdversaryValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "valor counter on {this}";
    }

    @Override
    public String toString() {
        return "1";
    }
}
