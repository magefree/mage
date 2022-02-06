package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterArtifactSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchworkAutomaton extends CardImpl {

    private static final FilterSpell filter = new FilterArtifactSpell("an artifact spell");

    public PatchworkAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever you cast an artifact spell, put a +1/+1 counter on Patchwork Automaton.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private PatchworkAutomaton(final PatchworkAutomaton card) {
        super(card);
    }

    @Override
    public PatchworkAutomaton copy() {
        return new PatchworkAutomaton(this);
    }
}
