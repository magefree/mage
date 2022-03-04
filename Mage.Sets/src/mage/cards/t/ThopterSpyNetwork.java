
package mage.cards.t;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThopterSpyNetwork extends CardImpl {

    public ThopterSpyNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // At the beginning of your upkeep, if you control an artifact, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new ThopterSpyNetworkUpkeepTriggeredAbility());

        // Whenever one or more artifact creatures you control deals combat damage to a player, draw a card.
        this.addAbility(new ThopterSpyNetworkDamageTriggeredAbility());
    }

    private ThopterSpyNetwork(final ThopterSpyNetwork card) {
        super(card);
    }

    @Override
    public ThopterSpyNetwork copy() {
        return new ThopterSpyNetwork(this);
    }
}

class ThopterSpyNetworkUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    ThopterSpyNetworkUpkeepTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken(), 1), false);
    }

    private ThopterSpyNetworkUpkeepTriggeredAbility(final ThopterSpyNetworkUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterSpyNetworkUpkeepTriggeredAbility copy() {
        return new ThopterSpyNetworkUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(new FilterArtifactPermanent(), this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control an artifact, create a 1/1 colorless Thopter artifact creature token with flying.";
    }
}

class ThopterSpyNetworkDamageTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    ThopterSpyNetworkDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private ThopterSpyNetworkDamageTriggeredAbility(final ThopterSpyNetworkDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterSpyNetworkDamageTriggeredAbility copy() {
        return new ThopterSpyNetworkDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.isControlledBy(controllerId)
                        && creature.isArtifact(game) && !damagedPlayerIds.contains(event.getTargetId())) {
                    damagedPlayerIds.add(event.getTargetId());
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.";
    }
}
