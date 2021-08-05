package mage.cards.f;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatesReversal extends CardImpl {

    public FatesReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return up to one target creature card from your graveyard to your hand. Venture into the dungeon.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
        this.getSpellAbility().addEffect(new VentureIntoTheDungeonEffect().concatBy("."));
    }

    private FatesReversal(final FatesReversal card) {
        super(card);
    }

    @Override
    public FatesReversal copy() {
        return new FatesReversal(this);
    }
}
