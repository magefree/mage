package mage.cards.a;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class AngelicRenewal extends CardImpl {

    public AngelicRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a creature is put into your graveyard from the battlefield, you may sacrifice Angelic Renewal. If you do, return that card to the battlefield.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DoIfCostPaid(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false), new SacrificeSourceCost()), false,
                new FilterCreaturePermanent("a creature"), true, true));
    }

    private AngelicRenewal(final AngelicRenewal card) {
        super(card);
    }

    @Override
    public AngelicRenewal copy() {
        return new AngelicRenewal(this);
    }
}
