package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.PlayerAttackedStepWatcher;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class HarshJustice extends CardImpl {

    public HarshJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Cast Harsh Justice only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // This turn, whenever an attacking creature deals combat damage to you, it deals that much damage to its controller.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new HarshJusticeTriggeredAbility()));
    }

    private HarshJustice(final HarshJustice card) {
        super(card);
    }

    @Override
    public HarshJustice copy() {
        return new HarshJustice(this);
    }
}

class HarshJusticeTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public HarshJusticeTriggeredAbility() {
        super(new HarshJusticeEffect(), Duration.EndOfTurn, false);
        setTriggerPhrase("This turn, whenever an attacking creature deals combat damage to you, ");
    }

    public HarshJusticeTriggeredAbility(final HarshJusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HarshJusticeTriggeredAbility copy() {
        return new HarshJusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent damagePermanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
        if (controller != null && damagePermanent != null) {
            if (damageEvent.isCombatDamage() && controller.getId().equals(damageEvent.getTargetId()) && filter.match(damagePermanent, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", damageEvent.getAmount());
                    effect.setValue("sourceId", damageEvent.getSourceId());
                }
                return true;
            }
        }
        return false;
    }
}

class HarshJusticeEffect extends OneShotEffect {

    public HarshJusticeEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to its controller";
    }

    public HarshJusticeEffect(final HarshJusticeEffect effect) {
        super(effect);
    }

    @Override
    public HarshJusticeEffect copy() {
        return new HarshJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) this.getValue("damage");
        UUID sourceId = (UUID) this.getValue("sourceId");
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && damage > 0 && sourceId != null) {
            Permanent targetObject = game.getPermanentOrLKIBattlefield(sourceId);
            if (targetObject != null) {
                Player controller = game.getPlayer(targetObject.getControllerId());
                if (controller != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + targetObject.getLogName() + " deals " + damage + " damage to " + controller.getLogName());
                    controller.damage(damage, sourceId, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
