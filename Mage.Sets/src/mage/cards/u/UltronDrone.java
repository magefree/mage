package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.RobotVillainToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class UltronDrone extends CardImpl {

    public UltronDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Power-up -- {6}: Put two +1/+1 counters on this creature and create a 2/2 colorless Robot Villain artifact creature token.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{6}")
        );
        ability.addEffect(new CreateTokenEffect(new RobotVillainToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private UltronDrone(final UltronDrone card) {
        super(card);
    }

    @Override
    public UltronDrone copy() {
        return new UltronDrone(this);
    }
}
