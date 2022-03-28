
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Styxo
 */
public final class Rumination extends CardImpl {

    public Rumination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Draw three cards, then put a card from your hand on top of your library.
        this.getSpellAbility().addEffect(new RuminationEffect());
    }

    private Rumination(final Rumination card) {
        super(card);
    }

    @Override
    public Rumination copy() {
        return new Rumination(this);
    }

    static class RuminationEffect extends OneShotEffect {

        public RuminationEffect() {
            super(Outcome.DrawCard);
            staticText = "Draw three cards, then put a card from your hand on top of your library.";
        }

        public RuminationEffect(final RuminationEffect effect) {
            super(effect);
        }

        @Override
        public RuminationEffect copy() {
            return new RuminationEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.drawCards(3, source, game);
                putOnLibrary(player, source, game);
                return true;
            }
            return false;
        }

        private boolean putOnLibrary(Player player, Ability source, Game game) {
            TargetCardInHand target = new TargetCardInHand();
            if (target.canChoose(player.getId(), source, game)) {
                player.chooseTarget(Outcome.ReturnToHand, target, source, game);
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
                }
            }
            return false;
        }
    }
}
