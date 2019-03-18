
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class IchorclawMyr extends CardImpl {

    public IchorclawMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(InfectAbility.getInstance());
        // Whenever Ichorclaw Myr becomes blocked, it gets +2/+2 until end of turn.
        this.addAbility(new BecomesBlockedTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    public IchorclawMyr(final IchorclawMyr card) {
        super(card);
    }

    @Override
    public IchorclawMyr copy() {
        return new IchorclawMyr(this);
    }
}
