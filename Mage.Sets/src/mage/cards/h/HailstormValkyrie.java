package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HailstormValkyrie extends CardImpl {

    public HailstormValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {S}{S}: Hailstorm Valkyrie gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{S}{S}")
        ));
    }

    private HailstormValkyrie(final HailstormValkyrie card) {
        super(card);
    }

    @Override
    public HailstormValkyrie copy() {
        return new HailstormValkyrie(this);
    }
}
