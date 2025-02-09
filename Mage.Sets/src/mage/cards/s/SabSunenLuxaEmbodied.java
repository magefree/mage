package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SabSunenLuxaEmbodied extends CardImpl {

    public SabSunenLuxaEmbodied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Sab-Sunen can't attack or block unless it has an even number of counters on it.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockUnlessConditionSourceEffect(SabSunenLuxaEmbodiedCondition.EVEN)));

        // At the beginning of your first main phase, put a +1/+1 counter on Sab-Sunen. Then if it has an odd number of counters on it, draw two cards.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2), SabSunenLuxaEmbodiedCondition.ODD,
                "Then if it has an odd number of counters on it, draw two cards"
        ));
        this.addAbility(ability);
    }

    private SabSunenLuxaEmbodied(final SabSunenLuxaEmbodied card) {
        super(card);
    }

    @Override
    public SabSunenLuxaEmbodied copy() {
        return new SabSunenLuxaEmbodied(this);
    }
}

enum SabSunenLuxaEmbodiedCondition implements Condition {
    EVEN(0),
    ODD(1);
    private final int parity;

    SabSunenLuxaEmbodiedCondition(int parity) {
        this.parity = parity;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CountersSourceCount.ANY.calculate(game, source, null) % 2 == parity;
    }

    @Override
    public String toString() {
        return "it has an " + (parity == 0 ? "even" : "odd") + " number of counters on it";
    }
}
