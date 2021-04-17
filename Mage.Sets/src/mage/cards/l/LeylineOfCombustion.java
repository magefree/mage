package mage.cards.l;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfCombustion extends CardImpl {

    public LeylineOfCombustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // If Leyline of Combustion is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Whenever you and/or at least one permanent you control becomes the target of a spell or ability an opponent controls, Leyline of Combustion deals 2 damage to that player.
        this.addAbility(new LeylineOfCombustionTriggeredAbility());
    }

    private LeylineOfCombustion(final LeylineOfCombustion card) {
        super(card);
    }

    @Override
    public LeylineOfCombustion copy() {
        return new LeylineOfCombustion(this);
    }
}

class LeylineOfCombustionTriggeredAbility extends TriggeredAbilityImpl {

    LeylineOfCombustionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private LeylineOfCombustionTriggeredAbility(final LeylineOfCombustionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeylineOfCombustionTriggeredAbility copy() {
        return new LeylineOfCombustionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        if (sourceObject == null) {
            return false;
        }
        Player targetter = game.getPlayer(event.getPlayerId());
        if (targetter == null || !targetter.hasOpponent(this.controllerId, game)) {
            return false;
        }
        if (!event.getTargetId().equals(this.controllerId)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || !permanent.isControlledBy(this.controllerId)) {
                return false;
            }
        }
        // If a spell targets you and/or a permanent you control multiple times,
        // or if a spell targets you and one or more permanents you control,
        // Leyline of Combustion’s triggered ability triggers once.
        Set<UUID> sourceObjects = (Set<UUID>) game.getState().getValue("sourceObjects" + this.id);
        if (sourceObjects == null) {
            sourceObjects = new HashSet<>();
        }
        if (!sourceObjects.add(sourceObject.getId())) {
            return false;
        }
        game.getState().setValue("sourceObjects" + this.id, sourceObjects);
        this.getEffects().clear();
        Effect effect = new DamageTargetEffect(2);
        effect.setTargetPointer(new FixedTarget(event.getPlayerId(), game));
        this.addEffect(effect);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you and/or at least one permanent you control " +
                "becomes the target of a spell or ability an opponent controls, " +
                "{this} deals 2 damage to that player.";
    }
}
