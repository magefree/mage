package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ButchDeLoriaTunnelSnake extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("each other Rogue and/or Snake you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(SubType.ROGUE.getPredicate(), SubType.SNAKE.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("other Rogue and/or snake you control", xValue);

    public ButchDeLoriaTunnelSnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Tunnel Snakes Rule! -- Whenever Butch DeLoria, Tunnel Snake attacks, it gets +1/+1 until end of turn for each other Rogue and/or Snake you control.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("it gets +1/+1 until end of turn for each other Rogue and/or Snake you control")
        ).addHint(hint).withFlavorWord("Tunnel Snakes Rule!"));

        // {1}{B}: Put a menace counter on another target creature. It becomes a Rogue in addition to its other types.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.MENACE.createInstance()),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.ROGUE, false)
                .setText("It becomes a Rogue in addition to its other types"));
        this.addAbility(ability);
    }

    private ButchDeLoriaTunnelSnake(final ButchDeLoriaTunnelSnake card) {
        super(card);
    }

    @Override
    public ButchDeLoriaTunnelSnake copy() {
        return new ButchDeLoriaTunnelSnake(this);
    }
}
