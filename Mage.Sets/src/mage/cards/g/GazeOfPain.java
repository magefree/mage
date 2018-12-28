package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class GazeOfPain extends CardImpl {

    public GazeOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Until end of turn, whenever a creature you control attacks and isn't blocked, you may choose to have it deal damage equal to its power to a target creature. If you do, it assigns no combat damage this turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GazeOfPainDelayedTriggeredAbility()));
    }

    public GazeOfPain(final GazeOfPain card) {
        super(card);
    }

    @Override
    public GazeOfPain copy() {
        return new GazeOfPain(this);
    }
}

class GazeOfPainDelayedTriggeredAbility extends DelayedTriggeredAbility {

    Set<UUID> creatures = new HashSet<>();

    public GazeOfPainDelayedTriggeredAbility() {
        super(new GazeOfPainEffect(), Duration.EndOfTurn, false, true);
    }

    public GazeOfPainDelayedTriggeredAbility(GazeOfPainDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getBlockers().isEmpty()) {
                for (UUID attackerId : combatGroup.getAttackers()) {
                    if (game.getPermanent(attackerId).getControllerId() == controllerId
                            && game.getPermanent(attackerId).isAttacking()) {
                        creatures.add(attackerId);
                    }
                }
            }
        }
        if (!creatures.isEmpty()) {
            game.getState().setValue("gazeOfPain", creatures);
            return true;
        }
        return false;
    }

    @Override
    public GazeOfPainDelayedTriggeredAbility copy() {
        return new GazeOfPainDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature you control attacks and isn't blocked, " + super.getRule();
    }
}

class GazeOfPainEffect extends OneShotEffect {

    Set<UUID> creatures = new HashSet<>();

    GazeOfPainEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may choose to have it deal damage equal to its power to a target creature. If you do, it assigns no combat damage this turn.";
    }

    GazeOfPainEffect(final GazeOfPainEffect effect) {
        super(effect);
    }

    @Override
    public GazeOfPainEffect copy() {
        return new GazeOfPainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        creatures = (Set<UUID>) game.getState().getValue("gazeOfPain");
        if (!creatures.isEmpty()) {
            for (UUID attackerId : creatures) {
                Permanent attacker = game.getPermanent(attackerId);
                if (controller != null
                        && attacker != null) {
                    if (controller.chooseUse(outcome, "Do you wish to deal damage equal to " + attacker.getName() + "'s power to a target creature?", source, game)) {
                        TargetCreaturePermanent target = new TargetCreaturePermanent();
                        if (target.canChoose(controller.getId(), game)
                                && controller.choose(Outcome.Detriment, target, source.getSourceId(), game)) {
                            Effect effect = new DamageTargetEffect(attacker.getPower().getValue());
                            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                            effect.apply(game, source);
                            ContinuousEffect effect2 = new AssignNoCombatDamageTargetEffect();
                            effect2.setTargetPointer(new FixedTarget(attackerId));
                            game.addEffect(effect2, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class AssignNoCombatDamageTargetEffect extends ReplacementEffectImpl {

    public AssignNoCombatDamageTargetEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
    }

    public AssignNoCombatDamageTargetEffect(final AssignNoCombatDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssignNoCombatDamageTargetEffect copy() {
        return new AssignNoCombatDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        return event.getSourceId().equals(targetPointer.getFirst(game, source))
                && damageEvent.isCombatDamage();
    }
}
