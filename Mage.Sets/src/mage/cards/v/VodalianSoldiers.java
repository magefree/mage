
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class VodalianSoldiers extends CardImpl {

    public VodalianSoldiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private VodalianSoldiers(final VodalianSoldiers card) {
        super(card);
    }

    @Override
    public VodalianSoldiers copy() {
        return new VodalianSoldiers(this);
    }
}
