
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class EscapedNull extends CardImpl {

    public EscapedNull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(5, 0, Duration.EndOfTurn).setText("it gets +5/+0 until end of turn"), false, false));
    }

    private EscapedNull(final EscapedNull card) {
        super(card);
    }

    @Override
    public EscapedNull copy() {
        return new EscapedNull(this);
    }
}
