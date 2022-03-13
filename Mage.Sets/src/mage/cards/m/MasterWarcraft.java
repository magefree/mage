package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.combat.ChooseBlockersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ControlCombatRedundancyWatcher;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class MasterWarcraft extends CardImpl {

    public MasterWarcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R/W}{R/W}");

        // Cast Master Warcraft only before attackers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, null, BeforeAttackersAreDeclaredCondition.instance,
                "Cast this spell only before attackers are declared"
        ));

        // You choose which creatures attack this turn.
        this.getSpellAbility().addEffect(new MasterWarcraftChooseAttackersEffect());

        // You choose which creatures block this turn and how those creatures block.
        this.getSpellAbility().addEffect(new ChooseBlockersEffect(Duration.EndOfTurn).concatBy("<br>"));

        // (only the last resolved Master Warcraft spell's effects apply)
        this.getSpellAbility().addWatcher(new ControlCombatRedundancyWatcher());
    }

    private MasterWarcraft(final MasterWarcraft card) {
        super(card);
    }

    @Override
    public MasterWarcraft copy() {
        return new MasterWarcraft(this);
    }
}

class MasterWarcraftChooseAttackersEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that will attack this combat (creatures not chosen won't attack this combat)");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    MasterWarcraftChooseAttackersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures attack this turn";
    }

    private MasterWarcraftChooseAttackersEffect(final MasterWarcraftChooseAttackersEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftChooseAttackersEffect copy() {
        return new MasterWarcraftChooseAttackersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_ATTACKERS;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        ControlCombatRedundancyWatcher.addAttackingController(source.getControllerId(), duration, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!ControlCombatRedundancyWatcher.checkAttackingController(source.getControllerId(), game)) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Player attackingPlayer = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (controller == null || attackingPlayer == null || attackingPlayer.getAvailableAttackers(game).isEmpty()) {
            return false; // the attack declaration resumes for the active player as normal
        }
        Target target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (!controller.chooseTarget(Outcome.Benefit, target, source, game)) {
            return false; // the attack declaration resumes for the active player as normal
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source, game)) {

            // Choose creatures that will be attacking this combat
            if (target.getTargets().contains(permanent.getId())) {
                RequirementEffect effect = new AttacksIfAbleTargetEffect(Duration.EndOfCombat);
                effect.setText("");
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
                game.informPlayers(controller.getLogName() + " has decided that " + permanent.getLogName() + " attacks this combat if able");

                // All other creatures can't attack (unless they must attack)
            } else {
                boolean hasToAttack = false;
                for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(permanent, false, game).entrySet()) {
                    RequirementEffect effect2 = entry.getKey();
                    if (effect2.mustAttack(game)) {
                        hasToAttack = true;
                    }
                }
                if (!hasToAttack) {
                    RestrictionEffect effect = new CantAttackTargetEffect(Duration.EndOfCombat);
                    effect.setText("");
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return false; // the attack declaration resumes for the active player as normal
    }
}
