package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author MTGfan
 */
public final class NettlingImp extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature the active player has controlled continuously since the beginning of the turn");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(new ControlledFromStartOfControllerTurnPredicate());
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public NettlingImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AttacksIfAbleTargetEffect(Duration.EndOfTurn)
                        .setText("choose target non-Wall creature the active player has controlled " +
                                "continuously since the beginning of the turn. That creature attacks this turn if able"),
                new TapSourceCost(), NettlingImpTurnCondition.instance
        );
        ability.addEffect(new NettlingImpDelayedDestroyEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private NettlingImp(final NettlingImp card) {
        super(card);
    }

    @Override
    public NettlingImp copy() {
        return new NettlingImp(this);
    }
}

enum NettlingImpTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return OpponentsTurnCondition.instance.apply(game, source) && !game.getTurn().isDeclareAttackersStepStarted();
    }

    @Override
    public String toString() {
        return "during an opponent's turn, before attackers are declared";
    }
}

class NettlingImpDelayedDestroyEffect extends OneShotEffect {

    NettlingImpDelayedDestroyEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy it at the beginning of the next end step if it didn't attack this turn";
    }

    private NettlingImpDelayedDestroyEffect(final NettlingImpDelayedDestroyEffect effect) {
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
