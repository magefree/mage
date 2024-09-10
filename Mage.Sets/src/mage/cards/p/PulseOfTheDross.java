
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
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
 * @author emerald000 & L_J
 */
public final class PulseOfTheDross extends CardImpl {

    public PulseOfTheDross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Target player reveals three cards from their hand and you choose one of them. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(3));
        this.getSpellAbility().addEffect(new PulseOfTheDrossReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PulseOfTheDross(final PulseOfTheDross card) {
        super(card);
    }

    @Override
    public PulseOfTheDross copy() {
        return new PulseOfTheDross(this);
    }
}

class PulseOfTheDrossReturnToHandEffect extends OneShotEffect {

    PulseOfTheDrossReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if that player has more cards in hand than you, return {this} to its owner's hand";
    }

    private PulseOfTheDrossReturnToHandEffect(final PulseOfTheDrossReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheDrossReturnToHandEffect copy() {
        return new PulseOfTheDrossReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) { 
                if (player.getHand().size() > controller.getHand().size()) {
                    Card card = game.getCard(source.getSourceId());
                    controller.moveCards(card, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
