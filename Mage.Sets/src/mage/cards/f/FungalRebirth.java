package mage.cards.f;

import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class FungalRebirth extends CardImpl {

    public FungalRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Return target permanent card from your graveyard to your hand. If a creature died this turn, create two 1/1 green Saproling creature tokens.
        getSpellAbility().addEffect(
                new ReturnFromGraveyardToHandTargetEffect().setText("Return target permanent card from your graveyard to your hand")
        );
        getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SaprolingToken(), 2),
                MorbidCondition.instance,
                "If a creature died this turn, create two 1/1 green Saproling creature tokens"));
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));
        getSpellAbility().addHint(MorbidHint.instance);
    }

    private FungalRebirth(final FungalRebirth card) {
        super(card);
    }

    @Override
    public FungalRebirth copy() {
        return new FungalRebirth(this);
    }
}
