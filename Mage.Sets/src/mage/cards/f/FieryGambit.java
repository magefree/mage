package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FieryGambit extends CardImpl {

    public FieryGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Flip a coin until you lose a flip or choose to stop flipping. If you lose a flip, Fiery Gambit has no effect. If you win one or more flips, Fiery Gambit deals 3 damage to target creature. If you win two or more flips, Fiery Gambit deals 6 damage to each opponent. If you win three or more flips, draw nine cards and untap all lands you control.
        this.getSpellAbility().addEffect(new FieryGambitEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FieryGambit(final FieryGambit card) {
        super(card);
    }

    @Override
    public FieryGambit copy() {
        return new FieryGambit(this);
    }
}

class FieryGambitEffect extends OneShotEffect {

    public FieryGambitEffect() {
        super(Outcome.Benefit);
        this.staticText = "Flip a coin until you lose a flip or choose to stop flipping. If you lose a flip, Fiery Gambit has no effect. If you win one or more flips, Fiery Gambit deals 3 damage to target creature. If you win two or more flips, Fiery Gambit deals 6 damage to each opponent. If you win three or more flips, draw nine cards and untap all lands you control";
    }

    private FieryGambitEffect(final FieryGambitEffect effect) {
        super(effect);
    }

    @Override
    public FieryGambitEffect copy() {
        return new FieryGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int flipsWon = 0;
            boolean controllerStopped = false;
            while (controller.flipCoin(source, game, true)) {
                ++flipsWon;
                if (!controller.chooseUse(outcome, "You won " + flipsWon + (flipsWon == 1 ? " flip." : " flips.") +
                        " Flip another coin?", source, game)) {
                    controllerStopped = true;
                    break;
                }

                // AI workaround to stop flips on good result
                if (controller.isComputer() && flipsWon >= 3) {
                    controllerStopped = true;
                    break;
                }
            }
            if (controllerStopped) {
                Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(3, source.getSourceId(), source, game, false, true);
                }
                if (flipsWon > 1) {
                    new DamagePlayersEffect(6, TargetController.OPPONENT).apply(game, source);
                }
                if (flipsWon > 2) {
                    controller.drawCards(9, source, game);
                    new UntapAllLandsControllerEffect().apply(game, source);
                }
            } else {
                game.informPlayers(sourceObject.getIdName() + " had no effect");
            }
            return true;
        }
        return false;
    }
}
