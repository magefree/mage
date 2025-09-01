package mage.cards.l;

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

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LurkingJackals extends CardImpl {

    public LurkingJackals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // When an opponent has 10 or less life, if Lurking Jackals is an enchantment, it becomes a 3/2 Hound creature.
        this.addAbility(new LurkingJackalsStateTriggeredAbility());
    }

    private LurkingJackals(final LurkingJackals card) {
        super(card);
    }

    @Override
    public LurkingJackals copy() {
        return new LurkingJackals(this);
    }
}

class LurkingJackalsStateTriggeredAbility extends StateTriggeredAbility {

    public LurkingJackalsStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new CreatureToken(
                3, 2, "3/2 Jackal creature", SubType.JACKAL
        ), null, Duration.Custom));
        this.withInterveningIf(SourceIsEnchantmentCondition.instance);
        this.withRuleTextReplacement(true);
        this.setTriggerPhrase("When an opponent has 10 or less life, ");
    }

    private LurkingJackalsStateTriggeredAbility(final LurkingJackalsStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LurkingJackalsStateTriggeredAbility copy() {
        return new LurkingJackalsStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getOpponents(getControllerId())
                .stream()
                .map(game::getPlayer)
                .mapToInt(Player::getLife)
                .anyMatch(x -> x <= 10);
    }
}
