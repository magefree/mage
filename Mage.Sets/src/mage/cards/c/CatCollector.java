package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.CatToken3;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class CatCollector extends CardImpl {

    public CatCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you gain life for the first time during each of your turns, create a 1/1 white Cat creature token.
        this.addAbility(new CatCollectorTriggeredAbility());
    }

    private CatCollector(final CatCollector card) {
        super(card);
    }

    @Override
    public CatCollector copy() {
        return new CatCollector(this);
    }
}

class CatCollectorTriggeredAbility extends GainLifeFirstTimeTriggeredAbility {

    CatCollectorTriggeredAbility() {
        super(new CreateTokenEffect(new CatToken3()));
        setTriggerPhrase("Whenever you gain life for the first time during each of your turns, ");
    }

    private CatCollectorTriggeredAbility(final CatCollectorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CatCollectorTriggeredAbility copy() {
        return new CatCollectorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && game.isActivePlayer(event.getPlayerId());
    }

}
