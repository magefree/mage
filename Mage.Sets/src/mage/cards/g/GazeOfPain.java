package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GazeOfPain extends CardImpl {

    public GazeOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Until end of turn, whenever a creature you control attacks and isn't blocked, you may choose to have it deal damage equal to its power to a target creature. If you do, it assigns no combat damage this turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GazeOfPainDelayedTriggeredAbility()));
    }

    private GazeOfPain(final GazeOfPain card) {
        super(card);
    }

    @Override
    public GazeOfPain copy() {
        return new GazeOfPain(this);
    }
}

class GazeOfPainDelayedTriggeredAbility extends DelayedTriggeredAbility {

    GazeOfPainDelayedTriggeredAbility() {
        super(null, Duration.EndOfTurn, false, true);
        this.addTarget(new TargetCreaturePermanent());
    }

    private GazeOfPainDelayedTriggeredAbility(final GazeOfPainDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getControllerId(event.getTargetId()))) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new GazeOfPainEffect(new MageObjectReference(event.getTargetId(), game)));
        this.addEffect(new AssignNoCombatDamageTargetEffect().setTargetPointer(new FixedTarget(event.getTargetId(), game)));
        return true;
    }

    @Override
    public GazeOfPainDelayedTriggeredAbility copy() {
        return new GazeOfPainDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature you control attacks and isn't blocked, " +
                "you may choose to have it deal damage equal to its power to a target creature. " +
                "If you do, it assigns no combat damage this turn.";
    }
}

class GazeOfPainEffect extends OneShotEffect {

    private final MageObjectReference mor;

    GazeOfPainEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private GazeOfPainEffect(final GazeOfPainEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public GazeOfPainEffect copy() {
        return new GazeOfPainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = mor.getPermanent(game);
        Permanent targeted = game.getPermanent(source.getFirstTarget());
        return player != null
                && creature != null
                && targeted != null
                && targeted.damage(creature.getPower().getValue(), creature.getId(), source, game) > 0;
    }
}
