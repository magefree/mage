package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MaelstromDjinn extends CardImpl {

    public MaelstromDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{U}")));

        // When Maelstrom Djinn is turned face up, put two time counters on it and it gains vanishing.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.TIME.createInstance(2)).setText("put two time counters on it")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new VanishingAbility(0), Duration.WhileOnBattlefield
        ).setText("and it gains vanishing"));
        this.addAbility(ability);
    }

    private MaelstromDjinn(final MaelstromDjinn card) {
        super(card);
    }

    @Override
    public MaelstromDjinn copy() {
        return new MaelstromDjinn(this);
    }
}
