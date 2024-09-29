package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestartSequence extends CardImpl {

    public RestartSequence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Freerunning {1}{B}
        this.addAbility(new FreerunningAbility("{1}{B}"));

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private RestartSequence(final RestartSequence card) {
        super(card);
    }

    @Override
    public RestartSequence copy() {
        return new RestartSequence(this);
    }
}
