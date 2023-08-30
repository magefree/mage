
package mage.cards.t;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThopterSpyNetwork extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterArtifactCreaturePermanent("artifact creatures");

    public ThopterSpyNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // At the beginning of your upkeep, if you control an artifact, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new ThopterSpyNetworkUpkeepTriggeredAbility());

        // Whenever one or more artifact creatures you control deals combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
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