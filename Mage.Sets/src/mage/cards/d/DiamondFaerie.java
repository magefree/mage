
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DiamondFaerie extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Snow creatures");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public DiamondFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}{U}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{S}: Snow creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1,
            Duration.EndOfTurn, filter), new ManaCostsImpl<>("{1}{S}")));
    }

    private DiamondFaerie(final DiamondFaerie card) {
        super(card);
    }

    @Override
    public DiamondFaerie copy() {
        return new DiamondFaerie(this);
    }
}
