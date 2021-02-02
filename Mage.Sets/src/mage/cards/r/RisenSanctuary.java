
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RisenSanctuary extends CardImpl {

    public RisenSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private RisenSanctuary(final RisenSanctuary card) {
        super(card);
    }

    @Override
    public RisenSanctuary copy() {
        return new RisenSanctuary(this);
    }
}
