package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousSquirrel extends CardImpl {

    public RavenousSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}");

        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice an artifact or creature, put a +1/+1 counter on Ravenous Squirrel.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT
        ));

        // {1}{B}{G}, Sacrifice an artifact or creature: You gain 1 life and draw a card.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new ManaCostsImpl<>("{1}{B}{G}"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private RavenousSquirrel(final RavenousSquirrel card) {
        super(card);
    }

    @Override
    public RavenousSquirrel copy() {
        return new RavenousSquirrel(this);
    }
}
