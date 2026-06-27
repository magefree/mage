package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.Hero32Token;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.ReachAbility;
import java.util.UUID;

/**
 * @author muz
 */
public final class PetAvengers extends CardImpl {

    public PetAvengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Power-up -- {6}{G}: Put a +1/+1 counter on this creature and create a 3/2 white Hero creature token with vigilance.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{6}{G}")
        );
        ability.addEffect(new CreateTokenEffect(new Hero32Token()).concatBy("and"));
        this.addAbility(ability);
    }

    private PetAvengers(final PetAvengers card) {
        super(card);
    }

    @Override
    public PetAvengers copy() {
        return new PetAvengers(this);
    }
}
