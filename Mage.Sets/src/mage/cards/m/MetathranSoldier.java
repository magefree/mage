
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class MetathranSoldier extends CardImpl {

    public MetathranSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.METATHRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Metathran Soldier is unblockable.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private MetathranSoldier(final MetathranSoldier card) {
        super(card);
    }

    @Override
    public MetathranSoldier copy() {
        return new MetathranSoldier(this);
    }
}
