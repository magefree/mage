package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxiomEngraver extends CardImpl {

    public AxiomEngraver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Axiom Engraver enters the battlefield with two oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(2)),
                "with two oil counters on it"
        ));

        // {T}, Remove an oil counter from Axiom Engraver, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private AxiomEngraver(final AxiomEngraver card) {
        super(card);
    }

    @Override
    public AxiomEngraver copy() {
        return new AxiomEngraver(this);
    }
}
