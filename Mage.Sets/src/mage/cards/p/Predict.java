package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
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

    public Predict(final Predict card) {
        super(card);
    }

    @Override
    public Predict copy() {
        return new Predict(this);
    }
}

class PredictEffect extends OneShotEffect {

    public PredictEffect() {
        super(Outcome.DrawCard);
        this.staticText = ", then target player puts the top card of their library into their graveyard. "
                + "If that card has the chosen name, you draw two cards. Otherwise, you draw a card.";
    }

    public PredictEffect(final PredictEffect effect) {
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
        if (controller != null && targetPlayer != null && cardName != null && !cardName.isEmpty()) {
            int amount = 1;
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
                if (card.getName().equals(cardName)) {
                    amount = 2;
                }
            }
            controller.drawCards(amount, game);
            return true;
        }
        return false;
    }

}
