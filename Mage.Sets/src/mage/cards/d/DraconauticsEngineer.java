package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DinDragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DraconauticsEngineer extends CardImpl {

    public DraconauticsEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exhaust -- {R}: Other creatures you control gain haste until end of turn. Put a +1/+1 counter on this creature.
        Ability ability = new ExhaustAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        ), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);

        // Exhaust -- {3}{R}: Create a 4/4 red Dinosaur Dragon creature token with flying.
        this.addAbility(new ExhaustAbility(new CreateTokenEffect(new DinDragonToken()), new ManaCostsImpl<>("{3}{R}")));
    }

    private DraconauticsEngineer(final DraconauticsEngineer card) {
        super(card);
    }

    @Override
    public DraconauticsEngineer copy() {
        return new DraconauticsEngineer(this);
    }
}
