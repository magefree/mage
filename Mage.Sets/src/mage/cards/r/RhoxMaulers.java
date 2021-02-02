
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RhoxMaulers extends CardImpl {

    public RhoxMaulers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Renown 2
        this.addAbility(new RenownAbility(2));
    }

    private RhoxMaulers(final RhoxMaulers card) {
        super(card);
    }

    @Override
    public RhoxMaulers copy() {
        return new RhoxMaulers(this);
    }
}
