
package mage.cards.k;

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
 * @author Alvin
 */
public final class KnightOfTheSkywardEye extends CardImpl {

    public KnightOfTheSkywardEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{G}: Knight of the Skyward Eye gets +3/+3 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")));

    }

    private KnightOfTheSkywardEye(final KnightOfTheSkywardEye card) {
        super(card);
    }

    @Override
    public KnightOfTheSkywardEye copy() {
        return new KnightOfTheSkywardEye(this);
    }
}
