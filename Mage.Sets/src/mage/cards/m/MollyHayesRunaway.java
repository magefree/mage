package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MollyHayesRunaway extends CardImpl {

    public MollyHayesRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Power-up -- {5}{R}: Put two +1/+1 counters on Molly Hayes. Exile the top card of your library. Until the end of your next turn, you may play that card.
        Ability ability = new PowerUpAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{5}{R}"));
        ability.addEffect(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn));
        this.addAbility(ability);
    }

    private MollyHayesRunaway(final MollyHayesRunaway card) {
        super(card);
    }

    @Override
    public MollyHayesRunaway copy() {
        return new MollyHayesRunaway(this);
    }
}
