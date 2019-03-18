
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AngelicRenewal extends CardImpl {

    public AngelicRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // Whenever a creature is put into your graveyard from the battlefield, you may sacrifice Angelic Renewal. If you do, return that card to the battlefield.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DoIfCostPaid(
            new ReturnToBattlefieldUnderOwnerControlTargetEffect(), new SacrificeSourceCost()), false,
            new FilterCreaturePermanent("a creature"), true, true));
    }

    public AngelicRenewal(final AngelicRenewal card) {
        super(card);
    }

    @Override
    public AngelicRenewal copy() {
        return new AngelicRenewal(this);
    }
}
