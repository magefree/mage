

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ayratn
 */
public final class MirranCrusader extends CardImpl {

    public MirranCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.GREEN));
    }

    private MirranCrusader(final MirranCrusader card) {
        super(card);
    }

    @Override
    public MirranCrusader copy() {
        return new MirranCrusader(this);
    }

}
