
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class IronBarbHellion extends CardImpl {

    public IronBarbHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.HELLION);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Iron-Barb Hellion can't block.
        this.addAbility(new CantBlockAbility());
    }

    private IronBarbHellion(final IronBarbHellion card) {
        super(card);
    }

    @Override
    public IronBarbHellion copy() {
        return new IronBarbHellion(this);
    }
}
