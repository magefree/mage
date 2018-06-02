
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class DreadReturn extends CardImpl {

    public DreadReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        // Flashback-Sacrifice three creatures.
        this.addAbility(new FlashbackAbility(new SacrificeTargetCost(new TargetControlledCreaturePermanent(3, 3, new FilterControlledCreaturePermanent("three creatures"), true)), TimingRule.SORCERY));
    }

    public DreadReturn(final DreadReturn card) {
        super(card);
    }

    @Override
    public DreadReturn copy() {
        return new DreadReturn(this);
    }
}
