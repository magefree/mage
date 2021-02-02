
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SkyspearCavalry extends CardImpl {

    public SkyspearCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private SkyspearCavalry(final SkyspearCavalry card) {
        super(card);
    }

    @Override
    public SkyspearCavalry copy() {
        return new SkyspearCavalry(this);
    }
}
