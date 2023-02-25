package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author MTGfan
 */
public final class NettlingImp extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(new ControlledFromStartOfControllerTurnPredicate());
        filter.add(TargetController.ACTIVE.getControllerPredicate());
        filter.setMessage("non-Wall creature the active player has controlled continuously since the beginning of the turn.");
    }

    public NettlingImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn),
                new TapSourceCost(), new NettlingImpTurnCondition(),
                "{T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. "
                + "That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. "
                + "Activate only during an opponent's turn, before attackers are declared.");
        ability.addEffect(new NettlingImpDelayedDestroyEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability, new AttackedThisTurnWatcher());

    }

    private NettlingImp(final NettlingImp card) {
        super(card);
    }

    @Override
    public NettlingImp copy() {
        return new NettlingImp(this);
    }
}

class NettlingImpTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        return activePlayer != null && activePlayer.hasOpponent(source.getControllerId(), game) && game.getPhase().getStep().getType().getIndex() < 5;
    }

    @Override
    public String toString() {
        return "";
    }
}

class NettlingImpDelayedDestroyEffect extends OneShotEffect {

    public NettlingImpDelayedDestroyEffect() {
        super(Outcome.Detriment);
        this.staticText = "If it doesn't, destroy it at the beginning of the next end step";
    }

    public NettlingImpDelayedDestroyEffect(final NettlingImpDelayedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public NettlingImpDelayedDestroyEffect copy() {
        return new NettlingImpDelayedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DestroyTargetEffect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY, new InvertCondition(TargetAttackedThisTurnCondition.instance));
        delayedAbility.getTargets().addAll(source.getTargets());
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
