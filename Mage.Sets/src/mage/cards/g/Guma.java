

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class Guma extends CardImpl {

    public Guma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private Guma(final Guma card) {
        super(card);
    }

    @Override
    public Guma copy() {
        return new Guma(this);
    }

}
