
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CurtainOfLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(new AttackingPredicate());
        filter.add(Predicates.not(new BlockedPredicate()));
    }

    public CurtainOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cast Curtain of Light only during combat after blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, AfterBlockersAreDeclaredCondition.instance));

        // Target unblocked attacking creature becomes blocked.
        this.getSpellAbility().addEffect(new CurtainOfLightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public CurtainOfLight(final CurtainOfLight card) {
        super(card);
    }

    @Override
    public CurtainOfLight copy() {
        return new CurtainOfLight(this);
    }
}

class CurtainOfLightEffect extends OneShotEffect {

    public CurtainOfLightEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target unblocked attacking creature becomes blocked";
    }

    public CurtainOfLightEffect(final CurtainOfLightEffect effect) {
        super(effect);
    }

    @Override
    public CurtainOfLightEffect copy() {
        return new CurtainOfLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            CombatGroup combatGroup = game.getCombat().findGroup(permanent.getId());
            if (combatGroup != null) {
                combatGroup.setBlocked(true, game);
                game.informPlayers(permanent.getLogName() + " has become blocked");
                return true;
            }
        }
        return false;
    }
}
