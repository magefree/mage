
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ElvishLookout extends CardImpl {

    public ElvishLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
    }

    private ElvishLookout(final ElvishLookout card) {
        super(card);
    }

    @Override
    public ElvishLookout copy() {
        return new ElvishLookout(this);
    }
}
