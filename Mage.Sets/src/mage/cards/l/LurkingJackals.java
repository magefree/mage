package mage.cards.l;

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
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new LurkingJackalsToken(), null, Duration.Custom));
        setTriggerPhrase("When an opponent has 10 or less life, if {this} is an enchantment, ");
    }

    public LurkingJackalsStateTriggeredAbility(final LurkingJackalsStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LurkingJackalsStateTriggeredAbility copy() {
        return new LurkingJackalsStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()) != null) {
            for (UUID opponentId : game.getOpponents(getControllerId())) {
                if (game.getPlayer(opponentId).getLife() <= 10) {
                    return true;
                }
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

class LurkingJackalsToken extends TokenImpl {

    public LurkingJackalsToken() {
        super("Dog", "3/2 Jackal creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.JACKAL);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    public LurkingJackalsToken(final LurkingJackalsToken token) {
        super(token);
    }

    @Override
    public LurkingJackalsToken copy() {
        return new LurkingJackalsToken(this);
    }
}
