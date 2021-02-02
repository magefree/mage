
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class HedronScrabbler extends CardImpl {

    public HedronScrabbler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private HedronScrabbler(final HedronScrabbler card) {
        super(card);
    }

    @Override
    public HedronScrabbler copy() {
        return new HedronScrabbler(this);
    }
}
