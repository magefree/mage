package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
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
 * @author LevelX2
 */
public final class LashOut extends CardImpl {

    public LashOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");


        // Lash Out deals 3 damage to target creature. Clash with an opponent. If you win, Lash Out deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new LashOutEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LashOut(final LashOut card) {
        super(card);
    }

    @Override
    public LashOut copy() {
        return new LashOut(this);
    }
}

class LashOutEffect extends OneShotEffect {

    public LashOutEffect() {
        super(Outcome.Damage);
        this.staticText = "Lash Out deals 3 damage to target creature. Clash with an opponent. If you win, Lash Out deals 3 damage to that creature's controller";
    }

    public LashOutEffect(final LashOutEffect effect) {
        super(effect);
    }

    @Override
    public LashOutEffect copy() {
        return new LashOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            creature.damage(3, source.getSourceId(), source, game, false, true);
            if (new ClashEffect().apply(game, source)) {
                Player creaturesController = game.getPlayer(creature.getControllerId());
                if (creaturesController != null) {
                    creaturesController.damage(3, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
