package mage.cards.d;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class DreadReturn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public DreadReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Flashback-Sacrifice three creatures.
        this.addAbility(new FlashbackAbility(this, new SacrificeTargetCost(new TargetControlledPermanent(3, filter))));
    }

    private DreadReturn(final DreadReturn card) {
        super(card);
    }

    @Override
    public DreadReturn copy() {
        return new DreadReturn(this);
    }
}
