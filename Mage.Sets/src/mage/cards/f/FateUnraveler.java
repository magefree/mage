
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class FateUnraveler extends CardImpl {

    public FateUnraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HAG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever an opponent draws a card, Fate Unraveler deals 1 damage to that player.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DamageTargetEffect(1, true, "that player"), false, true));
    }

    private FateUnraveler(final FateUnraveler card) {
        super(card);
    }

    @Override
    public FateUnraveler copy() {
        return new FateUnraveler(this);
    }
}
