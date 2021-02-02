
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SellSwordBrute extends CardImpl {

    public SellSwordBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sell-Sword Brute dies, it deals 2 damage to you.
        this.addAbility(new DiesSourceTriggeredAbility(new DamageControllerEffect(2, "it"), false));
    }

    private SellSwordBrute(final SellSwordBrute card) {
        super(card);
    }

    @Override
    public SellSwordBrute copy() {
        return new SellSwordBrute(this);
    }
}
