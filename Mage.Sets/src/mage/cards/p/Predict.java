package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Predict extends CardImpl {

    public Predict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Name a card, then target player puts the top card of their library into their graveyard. If that card is the named card, you draw two cards. Otherwise, you draw a card.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new PredictEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Predict(final Predict card) {
        super(card);
    }

    @Override
    public Predict copy() {
        return new Predict(this);
    }
}

class PredictEffect extends OneShotEffect {

    PredictEffect() {
        super(Outcome.DrawCard);
        this.staticText = ", then target player mills a card. If a card with the chosen name was milled this way, " +
                "you draw two cards. Otherwise, you draw a card.";
    }

    private PredictEffect(final PredictEffect effect) {
        super(effect);
    }

    @Override
    public PredictEffect copy() {
        return new PredictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (controller == null || targetPlayer == null || cardName == null || cardName.isEmpty()) {
            return false;
        }
        int toDraw = 1;
        for (Card card : targetPlayer.millCards(1, source, game).getCards(game)) {
            if (CardUtil.haveSameNames(card, cardName, game)) {
                toDraw = 2;
                break;
            }
        }
        controller.drawCards(toDraw, source, game);
        return true;
    }
}
