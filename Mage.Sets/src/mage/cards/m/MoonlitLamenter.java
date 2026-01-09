package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class MoonlitLamenter extends CardImpl {

    public MoonlitLamenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // This creature enters with a -1/-1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(1)),
                "with a -1/-1 counter on it"
        ));

        // {1}{W}, Remove a counter from this creature: Draw a card. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new RemoveCountersSourceCost(1));
        this.addAbility(ability);
    }

    private MoonlitLamenter(final MoonlitLamenter card) {
        super(card);
    }

    @Override
    public MoonlitLamenter copy() {
        return new MoonlitLamenter(this);
    }
}
