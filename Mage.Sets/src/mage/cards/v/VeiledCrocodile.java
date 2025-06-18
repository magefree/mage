package mage.cards.v;

import mage.abilities.StateTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VeiledCrocodile extends CardImpl {

    public VeiledCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When a player has no cards in hand, if Veiled Crocodile is an enchantment, Veiled Crocodile becomes a 4/4 Crocodile creature.
        this.addAbility(new VeiledCrocodileStateTriggeredAbility());
    }

    private VeiledCrocodile(final VeiledCrocodile card) {
        super(card);
    }

    @Override
    public VeiledCrocodile copy() {
        return new VeiledCrocodile(this);
    }
}

class VeiledCrocodileStateTriggeredAbility extends StateTriggeredAbility {

    public VeiledCrocodileStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(
                        4, 4, "4/4 Crocodile creature", SubType.CROCODILE
                ), null, Duration.Custom
        ));
        this.withInterveningIf(SourceIsEnchantmentCondition.instance);
        this.withRuleTextReplacement(true);
        this.setTriggerPhrase("When a player has no cards in hand, ");
    }

    private VeiledCrocodileStateTriggeredAbility(final VeiledCrocodileStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VeiledCrocodileStateTriggeredAbility copy() {
        return new VeiledCrocodileStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getState()
                .getPlayersInRange(getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .anyMatch(Set::isEmpty);
    }
}
