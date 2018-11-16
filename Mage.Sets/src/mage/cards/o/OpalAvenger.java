package mage.cards.o;

import java.util.UUID;
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

/**
 *
 * @author jeffwadsworth
 */
public final class OpalAvenger extends CardImpl {

    public OpalAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When you have 10 or less life, if Opal Avenger is an enchantment, Opal Avenger becomes a 3/5 Soldier creature.
        this.addAbility(new OpalAvengerStateTriggeredAbility());
    }

    public OpalAvenger(final OpalAvenger card) {
        super(card);
    }

    @Override
    public OpalAvenger copy() {
        return new OpalAvenger(this);
    }
}

class OpalAvengerStateTriggeredAbility extends StateTriggeredAbility {

    public OpalAvengerStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new OpalAvengerToken(), "", Duration.Custom, true, false));
    }

    public OpalAvengerStateTriggeredAbility(final OpalAvengerStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OpalAvengerStateTriggeredAbility copy() {
        return new OpalAvengerStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getState().getPlayer(getControllerId()).getLife() <= 10;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return this.getSourcePermanentIfItStillExists(game).getCardType().contains(CardType.ENCHANTMENT);
    }

    @Override
    public boolean canTrigger(Game game) {
        //20100716 - 603.8
        Boolean triggered = (Boolean) game.getState().getValue(getSourceId().toString() + "triggered");
        if (triggered == null) {
            triggered = Boolean.FALSE;
        }
        return !triggered;
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId);
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

    @Override
    public String getRule() {
        return new StringBuilder("When you have 10 or less life, if {this} is an enchantment, ").append(super.getRule()).toString();
    }

}

class OpalAvengerToken extends TokenImpl {

    public OpalAvengerToken() {
        super("Soldier", "3/5 Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(3);
        toughness = new MageInt(5);
    }

    public OpalAvengerToken(final OpalAvengerToken token) {
        super(token);
    }

    @Override
    public OpalAvengerToken copy() {
        return new OpalAvengerToken(this);
    }
}
