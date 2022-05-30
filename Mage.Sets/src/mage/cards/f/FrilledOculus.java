
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
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
 * @author Plopman
 */
public final class FrilledOculus extends CardImpl {

    public FrilledOculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}{G}: Frilled Oculus gets +2/+2 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
    }

    private FrilledOculus(final FrilledOculus card) {
        super(card);
    }

    @Override
    public FrilledOculus copy() {
        return new FrilledOculus(this);
    }
}
