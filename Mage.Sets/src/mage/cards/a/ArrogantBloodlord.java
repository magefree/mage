
package mage.cards.a;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ArrogantBloodlord extends CardImpl {

    public ArrogantBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.
        this.addAbility(new ArrogantBloodlordTriggeredAbility());
    }

    private ArrogantBloodlord(final ArrogantBloodlord card) {
        super(card);
    }

    @Override
    public ArrogantBloodlord copy() {
        return new ArrogantBloodlord(this);
    }
}

class ArrogantBloodlordTriggeredAbility extends TriggeredAbilityImpl {

    ArrogantBloodlordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ArrogantBloodlordEffect());
    }

    ArrogantBloodlordTriggeredAbility(final ArrogantBloodlordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArrogantBloodlordTriggeredAbility copy() {
        return new ArrogantBloodlordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Permanent blocked = game.getPermanent(event.getTargetId());
        Permanent arrogantBloodlord = game.getPermanent(sourceId);
        if (blocker != null && !Objects.equals(blocker, arrogantBloodlord)
                && blocker.getPower().getValue() < 2
                && Objects.equals(blocked, arrogantBloodlord)) {
            return true;
        }
        return blocker != null && Objects.equals(blocker, arrogantBloodlord)
                && game.getPermanent(event.getTargetId()).getPower().getValue() < 2;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked by a creature with power 1 or less, destroy {this} at end of combat.";
    }
}

class ArrogantBloodlordEffect extends OneShotEffect {

    ArrogantBloodlordEffect() {
        super(Outcome.Detriment);
        staticText = "Destroy {this} at the end of combat";
    }

    ArrogantBloodlordEffect(final ArrogantBloodlordEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

    @Override
    public ArrogantBloodlordEffect copy() {
        return new ArrogantBloodlordEffect(this);
    }
}
