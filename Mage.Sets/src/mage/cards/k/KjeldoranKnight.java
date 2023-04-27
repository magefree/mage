
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author L_J
 */
public final class KjeldoranKnight extends CardImpl {

    public KjeldoranKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // {1}{W}: Kjeldoran Knight gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}")));

        // {W}{W}: Kjeldoran Knight gets +0/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{W}{W}")));
    }

    private KjeldoranKnight(final KjeldoranKnight card) {
        super(card);
    }

    @Override
    public KjeldoranKnight copy() {
        return new KjeldoranKnight(this);
    }
}
