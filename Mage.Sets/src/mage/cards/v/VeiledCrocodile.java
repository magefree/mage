package mage.cards.v;

import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

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
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new VeilCrocodileToken(), "", Duration.Custom, true, false));
        setTriggerPhrase("When a player has no cards in hand, if {this} is an enchantment, ");
    }

    public VeiledCrocodileStateTriggeredAbility(final VeiledCrocodileStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VeiledCrocodileStateTriggeredAbility copy() {
        return new VeiledCrocodileStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (getSourcePermanentIfItStillExists(game) != null) {
            return getSourcePermanentIfItStillExists(game).isEnchantment(game);
        }
        return false;
    }

    @Override
    public boolean canTrigger(Game game) {
        //20100716 - 603.8
        return !Boolean.TRUE.equals(game.getState().getValue(getSourceId().toString() + "triggered"));
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId, triggeringEvent);
    }

    @Override
    public boolean resolve(Game game) {
        //20100716 - 603.8
        boolean result = super.resolve(game);
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
        return result;
    }

    @Override
    public void counter(Game game) {
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
    }
}

class VeilCrocodileToken extends TokenImpl {

    public VeilCrocodileToken() {
        super("Crocodile", "4/4 Crocodile creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CROCODILE);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public VeilCrocodileToken(final VeilCrocodileToken token) {
        super(token);
    }

    public VeilCrocodileToken copy() {
        return new VeilCrocodileToken(this);
    }
}
