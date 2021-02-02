
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class DuskriderFalcon extends CardImpl {

    public DuskriderFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    private DuskriderFalcon(final DuskriderFalcon card) {
        super(card);
    }

    @Override
    public DuskriderFalcon copy() {
        return new DuskriderFalcon(this);
    }
}
