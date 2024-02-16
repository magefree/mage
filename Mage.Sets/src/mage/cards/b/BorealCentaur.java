
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LoneFox
 */
public final class BorealCentaur extends CardImpl {

    public BorealCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.CENTAUR, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {S}: Boreal Centaur gets +1/+1 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{S}")));
    }

    private BorealCentaur(final BorealCentaur card) {
        super(card);
    }

    @Override
    public BorealCentaur copy() {
        return new BorealCentaur(this);
    }
}
