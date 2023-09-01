package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class TunnelVision extends CardImpl {

    public TunnelVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Name a card. Target player reveals cards from the top of their library until the named card is revealed.
        // If it is, that player puts the rest of the revealed cards into their graveyard and puts the named card on top of their library.
        // Otherwise, the player shuffles their library.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new TunnelVisionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TunnelVision(final TunnelVision card) {
        super(card);
    }

    @Override
    public TunnelVision copy() {
        return new TunnelVision(this);
    }
}

class TunnelVisionEffect extends OneShotEffect {

    public TunnelVisionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals cards from the top of their library "
                + "until a card with that name is revealed. If it is, that player puts "
                + "the rest of the revealed cards into their graveyard and "
                + "puts the card with the chosen name on top of their library. "
                + "Otherwise, the player shuffles";
    }

    private TunnelVisionEffect(final TunnelVisionEffect effect) {
        super(effect);
    }

    @Override
    public TunnelVisionEffect copy() {
        return new TunnelVisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (targetPlayer == null || cardName == null || cardName.isEmpty()) {
            return false;
        }

        Cards cardsToReveal = new CardsImpl();
        Card namedCard = null;

        for (Card card : targetPlayer.getLibrary().getCards(game)) {
            cardsToReveal.add(card);
            if (CardUtil.haveSameNames(card, cardName, game)) {
                namedCard = card;
                break;
            }
        }

        targetPlayer.revealCards(source, cardsToReveal, game);
        if (namedCard != null) {
            cardsToReveal.remove(namedCard);
            targetPlayer.moveCards(cardsToReveal, Zone.GRAVEYARD, source, game);
            targetPlayer.putCardsOnTopOfLibrary(new CardsImpl(namedCard), game, source, true);
        } else {
            targetPlayer.shuffleLibrary(source, game);
        }
        return true;
    }
}
