package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DestructiveRevelry extends CardImpl {

    public DestructiveRevelry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Destroy target artifact or enchantment. Destructive Revelry deals 2 damage to that permanent's controller.
        this.getSpellAbility().addEffect(new DestructiveRevelryEffect());
        Target target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT);
        this.getSpellAbility().addTarget(target);
    }

    private DestructiveRevelry(final DestructiveRevelry card) {
        super(card);
    }

    @Override
    public DestructiveRevelry copy() {
        return new DestructiveRevelry(this);
    }
}

class DestructiveRevelryEffect extends OneShotEffect {

    public DestructiveRevelryEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment. {this} deals 2 damage to that permanent's controller";
    }

    private DestructiveRevelryEffect(final DestructiveRevelryEffect effect) {
        super(effect);
    }

    @Override
    public DestructiveRevelryEffect copy() {
        return new DestructiveRevelryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.destroy(source, game, false);
            Player permController = game.getPlayer(permanent.getControllerId());
            if (permController != null) {
                permController.damage(2, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
