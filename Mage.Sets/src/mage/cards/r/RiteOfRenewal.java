package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * Rite of Renewal
 * author: @mikejcunn
 */
public final class RiteOfRenewal extends CardImpl {

    public RiteOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return up to two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 2, new FilterCard("permanent cards from your graveyard")));

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new RiteOfRenewalEffect());
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

/**
 * Custom effect for Rite of Renewal: Target player shuffles up to four target cards from their graveyard into their library.
 */
class RiteOfRenewalEffect extends OneShotEffect {

    public RiteOfRenewalEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to four target cards from their graveyard into their library";
    }

    private RiteOfRenewalEffect(final RiteOfRenewalEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfRenewalEffect copy() {
        return new RiteOfRenewalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Get the target player
        Player targetPlayer = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }

        // Get the cards chosen by the second target (cards in the graveyard)
        Cards cards = new CardsImpl(source.getTargets().get(1).getTargets());
        if (!cards.isEmpty()) {
            targetPlayer.shuffleCardsToLibrary(cards, game, source);
            return true;
        }
        return false;
    }
}