
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class MindstabThrull extends CardImpl {

    public MindstabThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Mindstab Thrull attacks and isn't blocked, you may sacrifice it. If you do, defending player discards three cards.
        Effect effect = new DiscardTargetEffect(3);
        effect.setText("defending player discards three cards");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(effect, new SacrificeSourceCost()), false, true));
    }

    private MindstabThrull(final MindstabThrull card) {
        super(card);
    }

    @Override
    public MindstabThrull copy() {
        return new MindstabThrull(this);
    }
}
