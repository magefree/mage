
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.common.TargetSacrifice;

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

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a green or white creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)
        ));
    }

    SelfInflictedWoundEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target opponent sacrifices a green or white creature. If that player does, they lose 2 life";
    }

    private SelfInflictedWoundEffect(final SelfInflictedWoundEffect effect) {
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
        TargetSacrifice target = new TargetSacrifice(filter);
        if (target.canChoose(targetOpponent.getId(), source, game)) {
            targetOpponent.choose(Outcome.Sacrifice, target, source, game);
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
