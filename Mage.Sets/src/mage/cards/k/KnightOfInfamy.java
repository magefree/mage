
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KnightOfInfamy extends CardImpl {

    public KnightOfInfamy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // Exalted
        this.addAbility(new ExaltedAbility());
    }

    private KnightOfInfamy(final KnightOfInfamy card) {
        super(card);
    }

    @Override
    public KnightOfInfamy copy() {
        return new KnightOfInfamy(this);
    }
}
