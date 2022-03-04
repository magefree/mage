package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class GlacialGrasp extends CardImpl {

    public GlacialGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Tap target creature. Its controller mills two cards. That creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new GlacialGraspEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private GlacialGrasp(final GlacialGrasp card) {
        super(card);
    }

    @Override
    public GlacialGrasp copy() {
        return new GlacialGrasp(this);
    }
}

class GlacialGraspEffect extends OneShotEffect {

    GlacialGraspEffect() {
        super(Outcome.Benefit);
        staticText = "Tap target creature. Its controller mills two cards. " +
                "That creature doesn't untap during its controller's next untap step.";
    }

    private GlacialGraspEffect(final GlacialGraspEffect effect) {
        super(effect);
    }

    @Override
    public GlacialGraspEffect copy() {
        return new GlacialGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.tap(source, game);
        game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect(), source);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(2, source, game);
        return true;
    }
}
