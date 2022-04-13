package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class BodyDropper extends CardImpl {

    public BodyDropper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice another creature, put a +1/+1 counter on Body Dropper.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // {B}{R}, Sacrifice another creature: Body Dropper gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{B}{R}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private BodyDropper(final BodyDropper card) {
        super(card);
    }

    @Override
    public BodyDropper copy() {
        return new BodyDropper(this);
    }
}
