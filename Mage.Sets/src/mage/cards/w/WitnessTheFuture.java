package mage.cards.w;

import java.util.UUID;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

/**
 *
 * @author weirddan455
 */
public final class WitnessTheFuture extends CardImpl {

    public WitnessTheFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Target player shuffles up to four target cards from their graveyard into their library.
        // You look at the top four cards of your library, then put one of those cards into your hand
        // and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(4));
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM).concatBy("You"));
    }

    private WitnessTheFuture(final WitnessTheFuture card) {
        super(card);
    }

    @Override
    public WitnessTheFuture copy() {
        return new WitnessTheFuture(this);
    }
}
