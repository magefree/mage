
package mage.cards.a;

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
public final class AncientOfTheEquinox extends CardImpl {

    public AncientOfTheEquinox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private AncientOfTheEquinox(final AncientOfTheEquinox card) {
        super(card);
    }

    @Override
    public AncientOfTheEquinox copy() {
        return new AncientOfTheEquinox(this);
    }
}
