
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class WarmindInfantry extends CardImpl {

    public WarmindInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Battalion - Whenever Warmind Infantry and at least two other creatures attack, Warmind Infantry gets +2/+0 until end of turn.
        this.addAbility(new BattalionAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));
    }

    private WarmindInfantry(final WarmindInfantry card) {
        super(card);
    }

    @Override
    public WarmindInfantry copy() {
        return new WarmindInfantry(this);
    }
}
