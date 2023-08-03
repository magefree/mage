
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WhirlpoolWhelm extends CardImpl {

    public WhirlpoolWhelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Clash with an opponent, then return target creature to its owner's hand. If you win, you may put that creature on top of its owner's library instead.
        this.getSpellAbility().addEffect(new WhirlpoolWhelmEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WhirlpoolWhelm(final WhirlpoolWhelm card) {
        super(card);
    }

    @Override
    public WhirlpoolWhelm copy() {
        return new WhirlpoolWhelm(this);
    }
}

class WhirlpoolWhelmEffect extends OneShotEffect {

    public WhirlpoolWhelmEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Clash with an opponent, then return target creature to its owner's hand. If you win, you may put that creature on top of its owner's library instead";
    }

    public WhirlpoolWhelmEffect(final WhirlpoolWhelmEffect effect) {
        super(effect);
    }

    @Override
    public WhirlpoolWhelmEffect copy() {
        return new WhirlpoolWhelmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            boolean topOfLibrary = false;
            if (new ClashEffect().apply(game, source)) {
                topOfLibrary = controller.chooseUse(outcome, "Put " + creature.getLogName() + " to top of its owner's library instead?", source, game);
            }
            if (topOfLibrary) {
                controller.moveCardToLibraryWithInfo(creature, source, game, Zone.BATTLEFIELD, true, true);
            } else {
                controller.moveCards(creature, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
