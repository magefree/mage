
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.constants.Zone;
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
public final class TrapRunner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(BlockedPredicate.instance));
    }

    public TrapRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Target unblocked attacking creature becomes blocked. Activate this ability only during combat after blockers are declared.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new TrapRunnerEffect(), new TapSourceCost(), 
            new CompoundCondition("during combat after blockers are declared", new IsPhaseCondition(TurnPhase.COMBAT), AfterBlockersAreDeclaredCondition.instance));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public TrapRunner(final TrapRunner card) {
        super(card);
    }

    @Override
    public TrapRunner copy() {
        return new TrapRunner(this);
    }

}

class TrapRunnerEffect extends OneShotEffect {

    public TrapRunnerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target unblocked attacking creature becomes blocked";
    }

    public TrapRunnerEffect(final TrapRunnerEffect effect) {
        super(effect);
    }

    @Override
    public TrapRunnerEffect copy() {
        return new TrapRunnerEffect(this);
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
