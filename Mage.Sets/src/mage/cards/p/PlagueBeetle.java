
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PlagueBeetle extends CardImpl {

    public PlagueBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new SwampwalkAbility());
    }

    private PlagueBeetle(final PlagueBeetle card) {
        super(card);
    }

    @Override
    public PlagueBeetle copy() {
        return new PlagueBeetle(this);
    }
}
