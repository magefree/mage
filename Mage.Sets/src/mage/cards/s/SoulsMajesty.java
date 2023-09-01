

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SoulsMajesty extends CardImpl {

    public SoulsMajesty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");

        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new SoulsMajestyEffect());
    }

    private SoulsMajesty(final SoulsMajesty card) {
        super(card);
    }

    @Override
    public SoulsMajesty copy() {
        return new SoulsMajesty(this);
    }

    private static class SoulsMajestyEffect extends OneShotEffect {

        public SoulsMajestyEffect() {
            super(Outcome.DrawCard);
            staticText = "Draw cards equal to the power of target creature you control";
        }

        private SoulsMajestyEffect(final SoulsMajestyEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent target = game.getPermanent(source.getFirstTarget());
            Player player = game.getPlayer(source.getControllerId());
        if (player != null && target != null) {
            player.drawCards(target.getPower().getValue(), source, game);
            return true;
        }
        return false;
        }

        @Override
        public SoulsMajestyEffect copy() {
            return new SoulsMajestyEffect(this);
        }

    }
}
