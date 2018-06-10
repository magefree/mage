
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SkullRend extends CardImpl {

    public SkullRend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{R}");


        // Skull Rend deals 2 damage to each opponent. Those players each discard two cards at random.
        this.getSpellAbility().addEffect(new SkullRendEffect());

    }

    public SkullRend(final SkullRend card) {
        super(card);
    }

    @Override
    public SkullRend copy() {
        return new SkullRend(this);
    }

    private static class SkullRendEffect extends OneShotEffect {

        public SkullRendEffect() {
            super(Outcome.Damage);
            staticText = "{this} deals 2 damage to each opponent. Those players each discard two cards at random";
        }

        public SkullRendEffect(final SkullRendEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (!Objects.equals(playerId, source.getControllerId())) {
                        Player opponent = game.getPlayer(playerId);
                        if (opponent != null) {
                            // damage
                            opponent.damage(2, source.getSourceId(), game, false, true);
                            // discard 2 cards at random
                            int amount = Math.min(2, opponent.getHand().size());
                            for (int i = 0; i < amount; i++) {
                                Card card = opponent.getHand().getRandom(game);
                                if (card != null) {
                                    opponent.discard(card, source, game);
                                }
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public SkullRendEffect copy() {
            return new SkullRendEffect(this);
        }
    }
}