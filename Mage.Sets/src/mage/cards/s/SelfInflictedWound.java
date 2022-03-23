
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class SelfInflictedWound extends CardImpl {

    public SelfInflictedWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target opponent sacrifices a green or white creature. If that player does, they lose 2 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SelfInflictedWoundEffect());        
        
    }

    private SelfInflictedWound(final SelfInflictedWound card) {
        super(card);
    }

    @Override
    public SelfInflictedWound copy() {
        return new SelfInflictedWound(this);
    }
}

class SelfInflictedWoundEffect extends OneShotEffect {

    SelfInflictedWoundEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target opponent sacrifices a green or white creature. If that player does, they lose 2 life";
    }

    SelfInflictedWoundEffect(SelfInflictedWoundEffect effect) {
        super(effect);
    }

    @Override
    public SelfInflictedWoundEffect copy() {
        return new SelfInflictedWoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getTargets().getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetOpponent == null || controller == null) {
            return false;
        }
        FilterControlledPermanent filter = new FilterControlledPermanent("a green or white creature");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

        if (target.canChoose(targetOpponent.getId(), source, game)) {
            targetOpponent.chooseTarget(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                if (permanent.sacrifice(source, game)) {
                    targetOpponent.loseLife(2, game, source, false);
                }                
            }
            
        }
        return true;
    }
}
