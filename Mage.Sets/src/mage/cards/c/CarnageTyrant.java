
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class CarnageTyrant extends CardImpl {

    public CarnageTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Carnage Tyrant can't be countered.
        this.addAbility(new CantBeCounteredAbility());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

    }

    public CarnageTyrant(final CarnageTyrant card) {
        super(card);
    }

    @Override
    public CarnageTyrant copy() {
        return new CarnageTyrant(this);
    }
}
