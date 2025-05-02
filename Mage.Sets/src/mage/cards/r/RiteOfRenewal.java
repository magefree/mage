package mage.cards.r;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiteOfRenewal extends CardImpl {

    public RiteOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return up to two target permanent cards from your graveyard to your hand. Target player shuffles up to four target cards from their graveyard into their library. Exile Rite of Renewal.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_PERMANENTS
        ));
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(4, 1));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private RiteOfRenewal(final RiteOfRenewal card) {
        super(card);
    }

    @Override
    public RiteOfRenewal copy() {
        return new RiteOfRenewal(this);
    }
}
