
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class WeiScout extends CardImpl {

    public WeiScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private WeiScout(final WeiScout card) {
        super(card);
    }

    @Override
    public WeiScout copy() {
        return new WeiScout(this);
    }
}
