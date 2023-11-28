package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class VitosInquisitor extends CardImpl {

    public VitosInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B}, Sacrifice another creature or artifact: Put a +1/+1 counter on Vito's Inquisitor. It gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{B}"));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn)
                .setText("It gains menace until end of turn"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT_SHORT_TEXT));
        this.addAbility(ability);

    }

    private VitosInquisitor(final VitosInquisitor card) {
        super(card);
    }

    @Override
    public VitosInquisitor copy() {
        return new VitosInquisitor(this);
    }
}
