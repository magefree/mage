package mage.cards.o;

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

    public OpalAvengerStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new OpalAvengerToken(), null, Duration.Custom));
        this.replaceRuleText = false;
        setTriggerPhrase("When you have 10 or less life, if {this} is an enchantment, ");
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
        if (game.getState().getPlayer(getControllerId()) != null) {
            return game.getState().getPlayer(getControllerId()).getLife() <= 10;
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

}

class OpalAvengerToken extends TokenImpl {

    public OpalAvengerToken() {
        super("Soldier", "3/5 Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(3);
        toughness = new MageInt(5);
    }

    private OpalAvengerToken(final OpalAvengerToken token) {
        super(token);
    }

    @Override
    public OpalAvengerToken copy() {
        return new OpalAvengerToken(this);
    }
}
