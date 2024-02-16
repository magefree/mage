

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WallOfDenial extends CardImpl {

    public WallOfDenial (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.WALL);


        this.power = new MageInt(0);
        this.toughness = new MageInt(8);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private WallOfDenial(final WallOfDenial card) {
        super(card);
    }

    @Override
    public WallOfDenial copy() {
        return new WallOfDenial(this);
    }

}
