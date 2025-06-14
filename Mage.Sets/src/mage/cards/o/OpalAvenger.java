package mage.cards.o;

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

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OpalAvenger extends CardImpl {

    public OpalAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When you have 10 or less life, if Opal Avenger is an enchantment, Opal Avenger becomes a 3/5 Soldier creature.
        this.addAbility(new OpalAvengerStateTriggeredAbility());
    }

    private OpalAvenger(final OpalAvenger card) {
        super(card);
    }

    @Override
    public OpalAvenger copy() {
        return new OpalAvenger(this);
    }
}

class OpalAvengerStateTriggeredAbility extends StateTriggeredAbility {

    OpalAvengerStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 5, "3/5 Soldier creature", SubType.SOLDIER), null, Duration.Custom
        ));
        this.withInterveningIf(SourceIsEnchantmentCondition.instance);
        this.withRuleTextReplacement(true);
        this.setTriggerPhrase("When you have 10 or less life, ");
    }

    private OpalAvengerStateTriggeredAbility(final OpalAvengerStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OpalAvengerStateTriggeredAbility copy() {
        return new OpalAvengerStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Optional
                .ofNullable(getControllerId())
                .map(game::getPlayer)
                .map(Player::getLife)
                .filter(x -> x <= 10)
                .isPresent();
    }
}
