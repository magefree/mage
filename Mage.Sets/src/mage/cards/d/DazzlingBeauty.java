
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
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
 * @author LevelX2 & L_J
 */
public final class DazzlingBeauty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(BlockedPredicate.instance));
    }

    public DazzlingBeauty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Cast Dazzling Beauty only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.DECLARE_BLOCKERS, null, "Cast Dazzling Beauty only during the declare blockers step"));

        // Target unblocked attacking creature becomes blocked.
        this.getSpellAbility().addEffect(new DazzlingBeautyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    public DazzlingBeauty(final DazzlingBeauty card) {
        super(card);
    }

    @Override
    public DazzlingBeauty copy() {
        return new DazzlingBeauty(this);
    }
}

class DazzlingBeautyEffect extends OneShotEffect {

    public DazzlingBeautyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target unblocked attacking creature becomes blocked";
    }

    public DazzlingBeautyEffect(final DazzlingBeautyEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingBeautyEffect copy() {
        return new DazzlingBeautyEffect(this);
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
