package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SkullRend extends CardImpl {

    public SkullRend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Skull Rend deals 2 damage to each opponent. Those players each discard two cards at random.
        this.getSpellAbility().addEffect(new SkullRendEffect());
    }

    private SkullRend(final SkullRend card) {
        super(card);
    }

    @Override
    public SkullRend copy() {
        return new SkullRend(this);
    }

    private static class SkullRendEffect extends OneShotEffect {

        private SkullRendEffect() {
            super(Outcome.Damage);
            staticText = "{this} deals 2 damage to each opponent. Those players each discard two cards at random";
        }

        private SkullRendEffect(final SkullRendEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent == null) {
                    continue;
                }
                opponent.damage(2, source.getSourceId(), game);
                opponent.discard(2, true, source, game);
            }
            return true;
        }

        @Override
        public SkullRendEffect copy() {
            return new SkullRendEffect(this);
        }
    }
}
