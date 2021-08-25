package mage.cards.m;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.ChooseBlockersEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ControlCombatRedundancyWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Melee extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            BeforeBlockersAreDeclaredCondition.instance,
            new IsPhaseCondition(TurnPhase.COMBAT),
            MyTurnCondition.instance
    );
    private static final Hint hint = new ConditionHint(condition, "Can be cast");

    public Melee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Cast Melee only during your turn and only during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, null, condition,
                "Cast this spell only during your turn and only during combat before blockers are declared"
        ).addHint(hint));

        // You choose which creatures block this combat and how those creatures block.
        // (only the last resolved Melee spell's blocking effect applies)
        this.getSpellAbility().addEffect(new ChooseBlockersEffect(Duration.EndOfCombat));
        this.getSpellAbility().addWatcher(new ControlCombatRedundancyWatcher());

        // Whenever a creature attacks and isn't blocked this combat, untap it and remove it from combat.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new MeleeTriggeredAbility()));
    }

    private Melee(final Melee card) {
        super(card);
    }

    @Override
    public Melee copy() {
        return new Melee(this);
    }
}

class MeleeTriggeredAbility extends DelayedTriggeredAbility {

    public MeleeTriggeredAbility() {
        super(new UntapTargetEffect(), Duration.EndOfCombat, false);
        this.addEffect(new RemoveFromCombatTargetEffect());
    }

    public MeleeTriggeredAbility(MeleeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(event.getTargetId())) {
                    this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MeleeTriggeredAbility copy() {
        return new MeleeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks and isn't blocked this combat, untap it and remove it from combat.";
    }
}
