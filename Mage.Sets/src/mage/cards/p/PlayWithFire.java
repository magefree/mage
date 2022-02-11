package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlayWithFire extends CardImpl {

    public PlayWithFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Play with Fire deals 2 damage to any target. If a player is dealt damage this way, scry 1.
        this.getSpellAbility().addEffect(new PlayWithFireEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private PlayWithFire(final PlayWithFire card) {
        super(card);
    }

    @Override
    public PlayWithFire copy() {
        return new PlayWithFire(this);
    }
}

class PlayWithFireEffect extends OneShotEffect {

    PlayWithFireEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to any target. If a player is dealt damage this way, scry 1. " +
                "<i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>";
    }

    private PlayWithFireEffect(final PlayWithFireEffect effect) {
        super(effect);
    }

    @Override
    public PlayWithFireEffect copy() {
        return new PlayWithFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            return permanent.damage(2, source.getSourceId(), source, game) > 0;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || player.damage(2, source.getSourceId(), source, game) < 1) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.scry(1, source, game);
        }
        return true;
    }
}
