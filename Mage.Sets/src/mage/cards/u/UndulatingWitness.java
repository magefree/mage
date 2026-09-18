package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class UndulatingWitness extends CardImpl {

    public UndulatingWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}: This creature gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BoostSourceEffect(1, -1, Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}")
        ));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private UndulatingWitness(final UndulatingWitness card) {
        super(card);
    }

    @Override
    public UndulatingWitness copy() {
        return new UndulatingWitness(this);
    }
}
