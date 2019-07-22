package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepeatedReverberation extends CardImpl {

    public RepeatedReverberation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // When you next cast an instant spell, cast a sorcery spell, or activate a loyalty ability this turn, copy that spell or ability twice. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new RepeatedReverberationTriggeredAbility()));
    }

    private RepeatedReverberation(final RepeatedReverberation card) {
        super(card);
    }

    @Override
    public RepeatedReverberation copy() {
        return new RepeatedReverberation(this);
    }
}

class RepeatedReverberationTriggeredAbility extends DelayedTriggeredAbility {

    RepeatedReverberationTriggeredAbility() {
        super(null, Duration.EndOfTurn);
    }

    private RepeatedReverberationTriggeredAbility(final RepeatedReverberationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RepeatedReverberationTriggeredAbility copy() {
        return new RepeatedReverberationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery()) {
            this.getEffects().clear();
            this.addEffect(
                    new CopyTargetSpellEffect(true)
                            .setTargetPointer(new FixedTarget(event.getTargetId(), game))
            );
            this.addEffect(
                    new CopyTargetSpellEffect(true)
                            .setTargetPointer(new FixedTarget(event.getTargetId(), game))
            );
            return true;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null && stackAbility.getStackAbility() instanceof LoyaltyAbility) {
            this.getEffects().clear();
            this.addEffect(
                    new RepeatedReverberationEffect()
                            .setTargetPointer(new FixedTarget(event.getTargetId(), game))
            );
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you next cast an instant spell, cast a sorcery spell, or activate a loyalty ability this turn, " +
                "copy that spell or ability twice. You may choose new targets for the copies.";
    }
}

class RepeatedReverberationEffect extends OneShotEffect {

    RepeatedReverberationEffect() {
        super(Outcome.Copy);
    }

    private RepeatedReverberationEffect(final RepeatedReverberationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(stackAbility.getStackAbility().getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
        game.informPlayers(sourcePermanent.getIdName() + ": " + controller.getLogName() + " copied loyalty ability twice");
        return true;
    }

    @Override
    public RepeatedReverberationEffect copy() {
        return new RepeatedReverberationEffect(this);
    }
}
