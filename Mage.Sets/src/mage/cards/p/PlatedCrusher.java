
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class PlatedCrusher extends CardImpl {

    public PlatedCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private PlatedCrusher(final PlatedCrusher card) {
        super(card);
    }

    @Override
    public PlatedCrusher copy() {
        return new PlatedCrusher(this);
    }
}
