
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WallOfSpears extends CardImpl {

    public WallOfSpears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private WallOfSpears(final WallOfSpears card) {
        super(card);
    }

    @Override
    public WallOfSpears copy() {
        return new WallOfSpears(this);
    }
}
