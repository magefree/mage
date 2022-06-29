
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
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
public final class ShimmeringBarrier extends CardImpl {

    public ShimmeringBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ShimmeringBarrier(final ShimmeringBarrier card) {
        super(card);
    }

    @Override
    public ShimmeringBarrier copy() {
        return new ShimmeringBarrier(this);
    }
}
