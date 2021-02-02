
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class DauthiMindripper extends CardImpl {

    public DauthiMindripper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Dauthi Mindripper attacks and isn't blocked, you may sacrifice it. If you do, defending player discards three cards.
        Effect effect = new DiscardTargetEffect(3);
        effect.setText("defending player discards three cards");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(effect, new SacrificeSourceCost()), false, true));
    }

    private DauthiMindripper(final DauthiMindripper card) {
        super(card);
    }

    @Override
    public DauthiMindripper copy() {
        return new DauthiMindripper(this);
    }
}
