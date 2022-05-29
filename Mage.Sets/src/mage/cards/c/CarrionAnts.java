
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class CarrionAnts extends CardImpl {

    public CarrionAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {1}: Carrion Ants gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}")));
        
    }

    private CarrionAnts(final CarrionAnts card) {
        super(card);
    }

    @Override
    public CarrionAnts copy() {
        return new CarrionAnts(this);
    }
}
