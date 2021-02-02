
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class SynapseSliver extends CardImpl {

    public SynapseSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a player, its controller may draw a card.
        Effect effect = new DrawCardTargetEffect(1);
        effect.setText("its controller may draw a card");
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(effect, 
                        new FilterCreaturePermanent(SubType.SLIVER, "a Sliver"),
                        true, SetTargetPointer.PLAYER, true));
    }

    private SynapseSliver(final SynapseSliver card) {
        super(card);
    }

    @Override
    public SynapseSliver copy() {
        return new SynapseSliver(this);
    }
}
