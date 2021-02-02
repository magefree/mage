
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
public final class WeiStrikeForce extends CardImpl {

    public WeiStrikeForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private WeiStrikeForce(final WeiStrikeForce card) {
        super(card);
    }

    @Override
    public WeiStrikeForce copy() {
        return new WeiStrikeForce(this);
    }
}
