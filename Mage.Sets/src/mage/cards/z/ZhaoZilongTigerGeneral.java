
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class ZhaoZilongTigerGeneral extends CardImpl {

    public ZhaoZilongTigerGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Whenever Zhao Zilong, Tiger General blocks, it gets +1/+1 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn, "it")));
    }

    private ZhaoZilongTigerGeneral(final ZhaoZilongTigerGeneral card) {
        super(card);
    }

    @Override
    public ZhaoZilongTigerGeneral copy() {
        return new ZhaoZilongTigerGeneral(this);
    }
}
