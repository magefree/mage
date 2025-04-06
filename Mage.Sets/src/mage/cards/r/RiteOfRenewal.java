package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;




/**
 * Rite of Renewal
 * author: @mikejcunn
 */
public final class RiteOfRenewal extends CardImpl {

    public RiteOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return up to two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
            .setText("Return up to two target permanent cards from your graveyard to your hand"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 2, new FilterCard("permanent cards from your graveyard")));

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect()
                .setText("Target player shuffles up to four target cards from their graveyard into their library"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 4, new FilterCard("cards from their graveyard")));
        // Exile Rite of Renewal.
        this.getSpellAbility().addEffect(new ExileSourceEffect().setText("Exile {this}"));
    }

    private RiteOfRenewal(final RiteOfRenewal card) {
        super(card);
    }

    @Override
    public RiteOfRenewal copy() {
        return new RiteOfRenewal(this);
    }
}