

package mage.cards.v;

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
 * @author Loki
 */
public final class ValeronOutlander extends CardImpl {

    public ValeronOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");


        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    private ValeronOutlander(final ValeronOutlander card) {
        super(card);
    }

    @Override
    public ValeronOutlander copy() {
        return new ValeronOutlander(this);
    }
}
