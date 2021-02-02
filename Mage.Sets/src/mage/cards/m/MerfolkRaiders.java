
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class MerfolkRaiders extends CardImpl {

    public MerfolkRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Phasing
        this.addAbility(PhasingAbility.getInstance());
    }

    private MerfolkRaiders(final MerfolkRaiders card) {
        super(card);
    }

    @Override
    public MerfolkRaiders copy() {
        return new MerfolkRaiders(this);
    }
}
