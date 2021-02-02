
package mage.cards.s;

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
 * @author anonymous
 */
public final class ScalebanesElite extends CardImpl {

    public ScalebanesElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    private ScalebanesElite(final ScalebanesElite card) {
        super(card);
    }

    @Override
    public ScalebanesElite copy() {
        return new ScalebanesElite(this);
    }
}
