
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class OonasBlackguard extends CardImpl {

    public OonasBlackguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Each other Rogue creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OonasBlackguardReplacementEffect()));

        // Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, that player discards a card.
        this.addAbility(new OonasBlackguardTriggeredAbility());

    }

    private OonasBlackguard(final OonasBlackguard card) {
        super(card);
    }

    @Override
    public OonasBlackguard copy() {
        return new OonasBlackguard(this);
    }
}

class OonasBlackguardReplacementEffect extends ReplacementEffectImpl {

    OonasBlackguardReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other Rogue creature you control enters the battlefield with an additional +1/+1 counter on it";
    }

    private OonasBlackguardReplacementEffect(final OonasBlackguardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && creature.hasSubtype(SubType.ROGUE, game)
                && !event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public OonasBlackguardReplacementEffect copy() {
        return new OonasBlackguardReplacementEffect(this);
    }
}

class OonasBlackguardTriggeredAbility extends TriggeredAbilityImpl {

    public OonasBlackguardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1), false);
    }

    private OonasBlackguardTriggeredAbility(final OonasBlackguardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OonasBlackguardTriggeredAbility copy() {
        return new OonasBlackguardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(getControllerId()) && creature.getCounters(game).getCount(CounterType.P1P1) > 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with a +1/+1 counter on it deals combat damage to a player, that player discards a card.";
    }

}
