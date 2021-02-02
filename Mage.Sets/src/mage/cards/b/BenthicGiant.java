
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BenthicGiant extends CardImpl {

    public BenthicGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private BenthicGiant(final BenthicGiant card) {
        super(card);
    }

    @Override
    public BenthicGiant copy() {
        return new BenthicGiant(this);
    }
}
