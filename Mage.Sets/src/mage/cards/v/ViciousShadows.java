
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class ViciousShadows extends CardImpl {

    public ViciousShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{6}{R}");


        // Whenever a creature dies, you may have Vicious Shadows deal damage to target player equal to the number of cards in that player's hand.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(CardsInTargetHandCount.instance), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ViciousShadows(final ViciousShadows card) {
        super(card);
    }

    @Override
    public ViciousShadows copy() {
        return new ViciousShadows(this);
    }
}
