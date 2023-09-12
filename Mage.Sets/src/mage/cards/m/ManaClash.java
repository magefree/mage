package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 * @author LevelX2
 */
public final class ManaClash extends CardImpl {

    public ManaClash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // You and target opponent each flip a coin. Mana Clash deals 1 damage to each player whose coin comes up tails.
        // Repeat this process until both players' coins come up heads on the same flip.
        this.getSpellAbility().addEffect(new ManaClashEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ManaClash(final ManaClash card) {
        super(card);
    }

    @Override
    public ManaClash copy() {
        return new ManaClash(this);
    }
}

class ManaClashEffect extends OneShotEffect {

    public ManaClashEffect() {
        super(Outcome.Detriment);
        this.staticText = "You and target opponent each flip a coin. {this} deals 1 damage to each player whose coin comes up tails. Repeat this process until both players' coins come up heads on the same flip";
    }

    private ManaClashEffect(final ManaClashEffect effect) {
        super(effect);
    }

    @Override
    public ManaClashEffect copy() {
        return new ManaClashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            boolean bothHeads = false;
            while (!bothHeads) {
                if (!targetOpponent.canRespond() || !controller.canRespond()) {
                    return false;
                }
                boolean controllerFlip = controller.flipCoin(source, game, false);
                boolean opponentFlip = targetOpponent.flipCoin(source, game, false);
                if (controllerFlip && opponentFlip) {
                    bothHeads = true;
                }
                if (!controllerFlip) {
                    controller.damage(1, source.getSourceId(), source, game);
                }
                if (!opponentFlip) {
                    targetOpponent.damage(1, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
