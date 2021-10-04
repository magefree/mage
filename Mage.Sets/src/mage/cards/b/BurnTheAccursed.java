package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurnTheAccursed extends CardImpl {

    public BurnTheAccursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Burn the Accused deals 5 damage to target creature and 2 damage to that creature's controller. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BurnTheAccursedEffect());
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BurnTheAccursed(final BurnTheAccursed card) {
        super(card);
    }

    @Override
    public BurnTheAccursed copy() {
        return new BurnTheAccursed(this);
    }
}

class BurnTheAccursedEffect extends OneShotEffect {

    BurnTheAccursedEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to target creature and 2 damage to that creature's controller.";
    }

    private BurnTheAccursedEffect(final BurnTheAccursedEffect effect) {
        super(effect);
    }

    @Override
    public BurnTheAccursedEffect copy() {
        return new BurnTheAccursedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(5, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(2, source.getSourceId(), source, game);
        }
        return true;
    }
}
